package org.lsqt.jdk;

public class Jdk1 {
	public static void main(String[] args) {
		new Thread(() ->System.out.println("111111")).start();;
	}
}
