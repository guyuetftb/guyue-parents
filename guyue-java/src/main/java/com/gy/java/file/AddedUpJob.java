package com.gy.java.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AddedUpJob implements Runnable {


	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + new Date());
		try {

			try {
				BufferedInputStream buf = new BufferedInputStream(new FileInputStream(new File("")));
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ming.txt")));
				String data = null;
				while ((data = br.readLine()) != null) {
					System.out.println(data);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("xxxxxxxxxxx");
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("after 5 minute ,end up the job.");
	}


	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
		AddedUpJob job = new AddedUpJob();
		executor.scheduleAtFixedRate(job, 1, 5, TimeUnit.SECONDS);
	}
}
