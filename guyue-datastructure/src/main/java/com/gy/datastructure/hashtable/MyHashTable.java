package com.gy.datastructure.hashtable;

import java.util.TreeMap;

/**
 * @ClassName MyHashTable
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-14 17:33
 */
public class MyHashTable<K, V> {

	private final static int upperTol = 10;
	private final static int lowerTol = 2;
	private final static int capacity = 7;

	private TreeMap<K, V> hashTable[];
	private int M;
	private int size;


	public MyHashTable(int M) {
		hashTable = new TreeMap[M];
		for (int index = 0; index < M; index++) {
			hashTable[index] = new TreeMap<K, V>();
		}
		this.M = M;
		this.size = 0;
	}

	public MyHashTable() {
		this(capacity);
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}

	public void add(K key, V value) {
		TreeMap<K, V> map = hashTable[hash(key)];
		if (map.containsKey(key)) {
			map.put(key, value);
		} else {
			map.put(key, value);
			size++;
			// M 是数组空间的数是
			// size 是元素的个数
			if (size >= M * upperTol) {
				resize(2 * M);
			}
		}
	}

	public V remove(K key) {
		TreeMap<K, V> map = hashTable[hash(key)];
		V ret = null;
		if (map.containsKey(key)) {
			ret = map.remove(key);
			size--;
			// size 是元素的数量
			// lowerTol * M 是地址空间 * 最小容积量
			// M / 2 >= lowerTol 表示 地址空间不能小于初始容量量7.
			if (size < lowerTol * M && M / 2 >= lowerTol) {
				resize(M / 2);
			}
		}
		return ret;
	}

	public V get(K key) {
		TreeMap<K, V> map = hashTable[hash(key)];
		V ret = null;
		if (map.containsKey(key)) {
			ret = map.get(key);
		}
		return ret;
	}

	public void set(K key, V value) {
		TreeMap<K, V> map = hashTable[hash(key)];
		if (map.containsKey(key)) {
			map.put(key, value);
		}
		throw new IllegalArgumentException(key + "don't exist!");
	}

	public boolean contains(K key) {
		TreeMap<K, V> map = hashTable[hash(key)];
		return map.containsKey(key);
	}

	private void resize(int newSize) {
		TreeMap<K, V> newHashTable[] = new TreeMap[newSize];
		for (int index = 0; index < newSize; index++) {
			newHashTable[index] = new TreeMap<>();
		}

		int oldM = M;
		this.M = newSize;
		for (int index = 0; index < oldM; index++) {
			TreeMap<K, V> oldMap = hashTable[index];
			for (K key : oldMap.keySet()) {
				newHashTable[hash(key)].put(key, oldMap.get(key));
			}
		}

		this.hashTable = newHashTable;
	}
}
