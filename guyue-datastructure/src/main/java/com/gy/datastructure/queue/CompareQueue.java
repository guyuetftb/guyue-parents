package com.gy.datastructure.queue;

import java.util.Random;

/**
 * @ClassName CompareQueue
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-09 21:30
 */
public class CompareQueue {

	public static double testQueue(Queue<Integer> queue, int opCount){
		long startTime = System.nanoTime();
		Random random = new Random();
		for(int index = 0;index < opCount;index++){
			queue.enqueue(random.nextInt(Integer.MAX_VALUE));
		}

		for(int index = 0;index < opCount;index++){
			queue.dequeue();
		}

		long endTime = System.nanoTime();
		return (endTime - startTime) / 1000000000.0;
	}

	public static void main(String[] args) {

		int opCount = 100000;
		ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
		double arraySecond = testQueue(arrayQueue,opCount);
		System.out.println(" array queue time = " + arraySecond);

		LoopQueue<Integer> loopQueue = new LoopQueue<>();
		double loopSecond = testQueue(loopQueue,opCount);
		System.out.println(" loop queue time = " + loopSecond);

	}
}
