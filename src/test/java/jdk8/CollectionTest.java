package jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionTest {
	private String testText = "";
	public static void main(String[] args) {
		List<Person> list = Arrays.asList(new Person("三"),new Person("五"),new Person("八"));
		list.forEach((p)->p.setFirstName("张"));
		
		list.forEach((n)->System.out.println(n));

		List<Person> data = list.stream().filter(p -> p.getLastName().startsWith("五")).map((n) -> new Person("磊"))
				.collect(Collectors.toList());
		
		System.out.println(data);
		
		long t0 = System.nanoTime();
		 int a[]=IntStream.range(0, 1_000_000).filter(p -> p % 2==0).toArray();
		long t1 = System.nanoTime();
		
		int b[]=IntStream.range(0, 1_000_000).parallel().filter(p -> p % 2==0).toArray();
		long t2 = System.nanoTime();
		
		 System.out.printf("serial: %.2fs, parallel %.2fs%n", (t1 - t0) * 1e-9, (t2 - t1) * 1e-9);
		 
		 Collections.emptyList().add("111");
	}
	
	public static class Person {
		private String firstName;
		private String lastName;

		public String toString() {
			return firstName + "  " + lastName;
		}
		public Person(String lastName) {
			this.lastName = lastName;
		}
		
		
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}
}

