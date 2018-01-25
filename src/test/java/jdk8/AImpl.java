package jdk8;

public class AImpl implements A {
	public static void main(String[] args) {
		A t = new AImpl();
		t.foo();
	}
}

interface A {
	default void foo() {
		System.out.println("aaaaaaaaaa");
	}
}
