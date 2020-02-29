package com.gy.java;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class SerializabeTest {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub 1
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("shit.obj"));
		Customer customer = new Customer("Gefforey", 23);
		out.writeObject("Hello,gefforey");
		out.writeObject(new Date());
		out.writeObject(customer);
		out.writeInt(520);
		out.close();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("shit.obj"));
		System.out.println("obj-1 String =" + in.readObject());
		System.out.println("obj-2 Date=" + in.readObject());
		System.out.println("obj-3 Customer=" + in.readObject());
		System.out.println("obj-4 Int=" + in.readInt());
		in.close();
	}
}

class Customer implements Serializable {
	public String name;
	public int age;

	public Customer(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String toString() {
		return "name=" + name + ",\tage=" + age + ".";
	}
}
