package jdk8;

public class  AImpl implements A {
	public static void main(String[] args) {
		A t = new AImpl();
		t.foo();
		t.test2();
		
		System.out.println(MyAbsClazz.getInstance());
	}

}

interface A {
	default void foo() {
		System.out.println("aaaaaaaaaa");
	}
	
	default void test2() {
		System.out.println("ccccccccccccc");
	}
}

abstract class  MyAbsClazz {
	private MyAbsClazz () {
		
	}
	
	public static MyAbsClazz getInstance() {
		class Temp extends MyAbsClazz {}
		return new Temp();
	}
}
