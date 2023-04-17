package com.gy.flume;

import java.io.File;
import java.io.IOException;

public class FileCreator {
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            String threadName = "Thread-Name-" + (i + 1);
            String fileName = "Flume-test-" + (i + 1) + ".log";
            FileRunner thread = new FileRunner(threadName, fileName);
            thread.start();
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}