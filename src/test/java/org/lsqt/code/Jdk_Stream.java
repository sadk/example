package org.lsqt.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Jdk_Stream {
	static class User {
		public User(String name,int age){
			this.name = name;
			this.age = age;
		}
		public String name;
		public int age ;
		
	}
	
	static class Student {
		public Student(String name){
			this.name = name;
			 
		}
		public String name;
		public int age=15 ;
	}
	public static void main(String[] args) {
		User u1 = new User("张1", 23);
		User u2 = new User("张2", 43);
		User u3 = new User("张3", 33);
		
		List<User> list = Arrays.asList(u1, u2, u3);
		list.forEach(e -> {
			System.out.println(e.name);
		});
		
		long cnt = list.stream().filter(e-> e.age>30).map(e -> new Student(e.name)).count();
		System.out.println(cnt);
		
		Integer a=new Integer(1);
		Integer b=new Integer(1);
		System.out.println((a==b )+"   "+(a.equals(b)));
		
		List<Student> tempList = list.stream()
			.filter(e-> e.age>30)
			.map(e -> new Student(e.name))
			.collect(Collectors.toCollection(java.util.ArrayList<Student>::new));
		System.out.println(tempList);
		
		
		System.out.println(java.util.function.Predicate.isEqual(a).test(b));
		
		
		long t1 = System.nanoTime() ;
		IntStream.range(0, 1_000_000).filter(e -> e%2 == 0) ;
		long t2= System.nanoTime();
		IntStream.range(0, 1_000_000).parallel().filter(e-> e%2==0);
		long t3 = System.nanoTime();
		
		System.out.println(t2-t1);
		System.out.println(t3-t2);
		
		
		//Collections.emptyList().add("One");
		//int i=Integer.parseInt("hello");
		//System.console().readLine();
		
		List<String> lt = new ArrayList<>();
		lt.add("A");;
		lt.addAll(new ArrayList<>());
		
		
		 System.out.println(java.time.LocalDate.now().minusDays(1));
		 System.out.println(java.time.LocalTime.now());
	}

}
