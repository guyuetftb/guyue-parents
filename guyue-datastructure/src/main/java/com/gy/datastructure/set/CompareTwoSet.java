package com.gy.datastructure.set;

import com.gy.datastructure.util.FileOperation;
import java.util.ArrayList;

/**
 * @ClassName CompareTwoSet
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-12-22 16:43
 */
public class CompareTwoSet {

	public static double compate(Set<String> set, String fileName){
		long beginTime = System.nanoTime();
		ArrayList<String> words1 = new ArrayList<>();
		if (FileOperation.readFile(fileName, words1)) {
			System.out.println("Total words: " + words1.size());
			for (String word : words1) {
				set.add(word);
			}
			System.out.println("Total different words: " + set.size());
		}
		long endTime = System.nanoTime();
		double finalTime = (endTime - beginTime);
		return finalTime;
	}

	public static void main(String[] args) {

		BinarySearchTreeSet<String> bstSet = new BinarySearchTreeSet();
		double time = compate(bstSet,"pride-and-prejudice.txt");
		System.out.println(" BinarySearthTree Set time1 = " + time );

		LinkedListSet<String> listSet = new LinkedListSet();
		double time2 = compate(listSet,"pride-and-prejudice.txt");
		System.out.println(" LinkedList Set time2 = " + time2 );


	}

}
