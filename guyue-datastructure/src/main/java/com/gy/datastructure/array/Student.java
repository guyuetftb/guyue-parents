package com.gy.datastructure.array;

import java.util.ArrayList;

/**
 * @ClassName Student
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-04 19:06
 */
public class Student {

	private String name;
	private Double score;

	public Student(String name, Double score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public String toString() {
		return "Student{" +
			"name='" + name + '\'' +
			", score=" + score +
			'}';
	}

	public static void main(String[] args) {

		DSArray<Student> students = new DSArray<>();
		Student li = new Student("li",100d);
		Student wang = new Student("wang",99d);
		Student zhao = new Student("zhao",88d);
		students.addLast(li);
		students.addLast(wang);
		students.addLast(zhao);
		System.out.println(students);

		ArrayList a;
	}
}
