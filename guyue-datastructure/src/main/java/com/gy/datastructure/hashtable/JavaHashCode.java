package com.gy.datastructure.hashtable;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @ClassName JavaHashCode
 * @Description TOOD
 * @Author lipeng
 * @Date 2020-02-12 09:36
 */
public class JavaHashCode {

	public static void main(String[] args) {

		// Java 中基本数据类型的 hashCode 值是需要转换包装类的
		int a = 42;
		System.out.println(((Integer)a).hashCode());

		// Java 的 HashCode 值是可以为负数的
		// 因为 Java 的 hashCode 函数返回是一个 Int型，
		// Int 型是有符号数, 自然 hashCode 就会有负数的情况
		// Java 只是把数据映射到一个数值上，这个值在 hash 表中该数据操作
		// 如何取值 Java 是不做规定的。
		int b = -42;
		System.out.println(((Integer)b).hashCode());

		double c = 3.1415926;
		System.out.println(((Double)c).hashCode());

		String d = "imooc";
		System.out.println(d.hashCode());

		System.out.println(Integer.MAX_VALUE + 1);
		System.out.println();

		Student student = new Student(3, 2, "Bobo", "Liu");
		System.out.println(student.hashCode());

		HashSet<Student> set = new HashSet<>();
		set.add(student);

		HashMap<Student, Integer> scores = new HashMap<>();
		scores.put(student, 100);

		Student student2 = new Student(3, 2, "Bobo", "Liu");
		System.out.println(student2.hashCode());
	}

}
