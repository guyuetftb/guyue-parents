package com.gy.algorithm.basic.testing;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName Test2
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-05-09 23:01
 */
public class Test2 {

	public static void main(String[] args) {

		Queue<Integer> queue = new ArrayBlockingQueue<>(10);
		queue.add(0);
		queue.add(1);
		queue.add(2);
		queue.add(3);
		while(!queue.isEmpty()){
			Integer i = queue.remove();
			if(i != 0 && i % 3 == 0){
				System.out.println("Yes");
			}else{
				System.out.println("NO");
			}
		}
	}
}
