package com.gy.avro.login;

public class AvroUserManager {
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            String threadName = "Thread-Avro-Name-" + (i + 1);
            String fileName = "Flume-test-" + (i + 1) + ".log";
            AvroUserRunner thread = new AvroUserRunner(threadName, fileName);
            thread.start();
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
