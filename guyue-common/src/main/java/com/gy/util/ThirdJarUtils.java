package com.gy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ThirdJarUtils {

	private final static Logger logger = Logger.getLogger(ThirdJarUtils.class);

	private final static HashMap<String, String> jarsHm = new HashMap<String, String>();
	private final static String CONFIG_FILE = "third.jar.properties";
	public final static String JAR_PATH;
	public final static String JAR_NAMES;

	static {
		InputStream in = ThirdJarUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
		Properties pros = new Properties();
		try {
			pros.load(in);
		} catch (IOException ex) {
			logger.error("PropertiesUtil load config.properties file error." + ex.getMessage());
			System.exit(0);
		}
		// set jar path.
		String path = pros.getProperty("third.jar.path");
		JAR_PATH = (null == path || path.length() <= 0) ? Constant.NULL : path;

		// set jars.
		String jars = pros.getProperty("third.jar.names");
		if (null != jars && jars.length() > 0) {
			JAR_NAMES = jars;
			String[] jarArr = jars.split(Constant.SEMICOLON);
			for (String s : jarArr) {
				jarsHm.put(s, JAR_PATH + s);
			}
		} else {
			JAR_NAMES = Constant.NULL;
		}
	}

	public static HashMap<String, String> getThirdJars() {
		return jarsHm;
	}

	public static void main(String[] args) {
		System.out.println(JAR_PATH);
		System.out.println(JAR_NAMES);
		for (String key : jarsHm.keySet()) {
			System.out.println("key = " + key + ", value = " + jarsHm.get(key));
		}
	}
}
