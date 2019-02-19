package jdk8;

public class LambadaTest {

	public static void main(String[] args) {
		LambadaTest t = new LambadaTest();
		new Thread(() -> System.out.println("cccccccccccccc")).start();
	}

	public void test() {
		System.out.println("aaaa");
	}
	
	public static void test2() {
		System.out.println("bbbb");
	}
}

