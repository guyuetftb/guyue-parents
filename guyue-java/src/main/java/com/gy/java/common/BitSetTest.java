package com.gy.java.common;

import java.util.*;
/**
 *
 */
public class BitSetTest {
	public static void main(String[] args) {
		BitSet sieve = new BitSet(1024);
		int size = sieve.size();
		System.out.println("size = "  + size);
		for (int i = 2; i < size; i++){
			sieve.set(i);
		}
		System.out.println("sieve.size() = " + sieve.size());
		int finalBit = (int) Math.sqrt(sieve.size());
		System.out.println("finalBit = " + finalBit);
		
		for (int i = 2; i < finalBit; i++){
			System.out.println(sieve.get(i));
			if (sieve.get(i)){
				for (int j = 2 * i; j < size; j += i){
					sieve.clear(j);
				}
			}
		}

		int counter = 0;
		for (int i = 1; i < size; i++) {
			if (sieve.get(i)) {
				System.out.printf("%5d", i);
				if (++counter % 15 == 0)
					System.out.println();
			}
		}
	}
}
