package com.gy.util;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.ShutdownHookManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

/**
 * Run a Hadoop job jar.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class RunJarUtil {

    /**
     * Pattern that matches any string
     */
    public static final Pattern MATCH_ANY = Pattern.compile(".*");
    private static Logger log = Logger.getLogger(RunJarUtil.class);

    /**
     * Priority of the RunJar shutdown hook.
     */
    public static final int SHUTDOWN_HOOK_PRIORITY = 10;

    /**
     * Unpack a jar file into a directory.
     * <p/>
     * This version unpacks all files inside the jar regardless of filename.
     */
    public static void unJar(File jarFile, File toDir) throws IOException {
        unJar(jarFile, toDir, MATCH_ANY);
    }

    /**
     * Unpack matching files from a jar. Entries inside the jar that do not
     * match the given pattern will be skipped.
     *
     * @param jarFile     the .jar file to unpack
     * @param toDir       the destination directory into which to unpack the jar
     * @param unpackRegex the pattern to match jar entries against
     */
    public static void unJar(File jarFile, File toDir, Pattern unpackRegex) throws IOException {
        JarFile jar = new JarFile(jarFile);
        try {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && unpackRegex.matcher(entry.getName()).matches()) {
                    InputStream in = jar.getInputStream(entry);
                    try {
                        File file = new File(toDir, entry.getName());
                        ensureDirectory(file.getParentFile());
                        OutputStream out = new FileOutputStream(file);
                        try {
                            IOUtils.copyBytes(in, out, 8192);
                        } finally {
                            out.close();
                        }
                    } finally {
                        in.close();
                    }
                }
            }
        } finally {
            jar.close();
        }
    }

    /**
     * Ensure the existence of a given directory.
     *
     * @throws IOException if it cannot be created and does not already exist
     */
    private static void ensureDirectory(File dir) throws IOException {
        if (!dir.mkdirs() && !dir.isDirectory()) {
            throw new IOException("Mkdirs failed to create " + dir.toString());
        }
    }

    /**
     * Run a Hadoop job jar. If the main class is not in the jar's manifest,
     * then it must be provided on the command line.
     */
    public static int runJob(String[] args) throws Throwable {
        String usage = "RunJar jarFile [mainClass] args...";

        if (args.length < 1) {
            System.err.println(usage);
            return -1;
        }

        // jar file.
        int firstArg = 0;
        String fileName = args[firstArg++];
        File file = new File(fileName);
        String mainClassName = null;

        JarFile jarFile;
        try {
            jarFile = new JarFile(fileName);
        } catch (IOException io) {
            throw new IOException("Error opening job jar: " + fileName, io);
        }

        Manifest manifest = jarFile.getManifest();
        if (manifest != null) {
            mainClassName = manifest.getMainAttributes().getValue("Main-Class");
        }
        jarFile.close();

        if (mainClassName == null) {
            if (args.length < 2) {
                System.err.println(usage);
                return -1;
            }
            mainClassName = args[firstArg++];
        }
        mainClassName = mainClassName.replaceAll("/", ".");

        File tmpDir = new File(new Configuration().get("hadoop.tmp.dir"));
        ensureDirectory(tmpDir);

        final File workDir;
        try {
            workDir = File.createTempFile("hadoop-unjar", "", tmpDir);
        } catch (IOException ioe) {
            // If user has insufficient perms to write to tmpDir, default
            // "Permission denied" message doesn't specify a filename.
            System.err.println("Error creating temp dir in hadoop.tmp.dir " + tmpDir + " due to " + ioe.getMessage());
            return -1;
        }

        log.info("work dir " + workDir);

        if (!workDir.delete()) {
            log.error("Delete failed for " + workDir);
            System.err.println("Delete failed for " + workDir);
            return -1;
        }
        ensureDirectory(workDir);

        ShutdownHookManager.get().addShutdownHook(new Runnable() {
            @Override
            public void run() {
                try {

                    FileUtil.fullyDelete(workDir);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SHUTDOWN_HOOK_PRIORITY);

        unJar(file, workDir);

        ArrayList<URL> classPath = new ArrayList<URL>();
        classPath.add(new File(workDir + "/").toURI().toURL());
        classPath.add(file.toURI().toURL());
        classPath.add(new File(workDir, "classes/").toURI().toURL());
        File[] libs = new File(workDir, "lib").listFiles();
        if (libs != null) {
            for (int i = 0; i < libs.length; i++) {
                classPath.add(libs[i].toURI().toURL());
            }
        }

        ClassLoader loader = new URLClassLoader(classPath.toArray(new URL[0]));

        Thread.currentThread().setContextClassLoader(loader);
        Class<?> mainClass = Class.forName(mainClassName, true, loader);
        Method main = mainClass.getMethod("executeTask", Array.newInstance(String.class, 0).getClass());
        String[] newArgs = Arrays.asList(args).subList(firstArg, args.length).toArray(new String[0]);
        int res = -1;
        try {
            log.info("begin to do work of " + mainClass.getName());
            Object obj = main.invoke(mainClass.newInstance(), new Object[]{newArgs});
            log.info("work of " + mainClass.getName() + " over: " + obj);
            if (obj != null) {
                res = Integer.parseInt(obj.toString());
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            log.warn("invock");
            throw e.getTargetException();
        } catch (Exception ee) {
            log.error("invoke error:", ee);
            throw ee;
        } finally {
            log.info("delete work dir " + workDir);
            try {
                boolean delflag = FileUtil.fullyDelete(workDir);
                log.info("delete work dir " + workDir + " result :" + delflag);
            } catch (Exception e) {
                log.error("delete work dir " + workDir, e);
            }
        }
        return res;
    }

}
