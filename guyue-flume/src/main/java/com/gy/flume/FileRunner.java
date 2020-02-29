package com.gy.flume;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Logger;

import java.io.*;

public class FileRunner extends Thread {

    public static final Logger logger = Logger.getLogger(FileRunner.class);

    private static String path = "/Users/lipeng/workspace_guyue/guyue-parents/guyue-flume/data";
    private String fileName;
    private String threadName;

    public FileRunner(String threadName, String fileName) {
        super(threadName);
        this.fileName = fileName;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        try {
            // writeLogFile();
            writeTxtFile();
            System.out.println("----------- threadName=" + threadName + ", fileName=" + fileName + ", I am Over!. ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException
     */
    private void writeTxtFile() throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            // 文件路径
            File file = new File(path + "/" + fileName);
            // 将文件读入输入流
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            int rowNum = 0;
            int cycIndex = 0;
            ObjectMapper mapper = new ObjectMapper();
            while (true) {

                ObjectNode father = mapper.createObjectNode();
                father.put("timestamp", System.nanoTime());
                father.put("threadName", threadName);
                father.put("rowNum", rowNum);

                ArrayNode arrayNode = mapper.createArrayNode();
                arrayNode.add(mapper.createObjectNode().put("name", "Child-A"));
                arrayNode.add(mapper.createObjectNode().put("name", "Child-B"));
                arrayNode.add(mapper.createObjectNode().put("name", "Child-C"));
                father.putPOJO("children", arrayNode);


                bos.write((father.toString() + "\n").getBytes());
                rowNum++;
                cycIndex++;
                if (cycIndex != 0 && cycIndex % 10 == 0) {
                    bos.flush();
                    cycIndex = 0;
                    Thread.sleep(5000l);
                    System.out.println(" threadName = " + threadName + ", row-number = " + rowNum);
                }
                if (rowNum == 100) {
                    break;
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }

    private String getJsonLing() {

        return null;
    }

    private void writeLogFile() {
        // 先读取原有文件内容，然后进行写入操作
        try {
            int rowNum = 0;
            int cycIndex = 0;
            while (true) {
                String line = System.nanoTime() + "\t" + threadName + "\t" + rowNum;
                logger.info(line);
                rowNum++;
                cycIndex++;
                if (cycIndex != 0 && cycIndex % 20 == 0) {
                    cycIndex = 0;
                    Thread.sleep(3000l);
                    System.out.println(" threadName = " + threadName + ", row-number = " + rowNum);
                }
                if (rowNum == 230) {
                    break;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
