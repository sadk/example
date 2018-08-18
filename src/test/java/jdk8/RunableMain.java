package jdk8;

import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

public class RunableMain {
	public static void main(String[] args) {
		Runnable run = ()-> System.out.println("111");
		 

		Thread t = new Thread(run);
		t.start();
		
		TreeSet<String> tree = new TreeSet<>((o1, o2) -> Integer.compare(o1.length(), o2.length()));
		tree.add("afdsafsa我");
		tree.add("xvds");
		System.out.println(tree);
		
		RunableMain r = new RunableMain();
		r.testMsg(e ->{System.out.println(1111);});
		/*
		IMsg2 m = (1L,2L)-> {
			System.out.println("111");
		};
		*/
		BinaryOperator<Long> bo = (Long x, Long y) -> {
			return x + y;
		};

		IMsg2 mm = (x, y) -> {

		};

		IMsg2 m3 = new IMsg2() {
			@Override
			public void add(Long a, Long b) {

			}
		};
		
		IMsg2 m4 = (x,y) ->{System.out.println(x+y);};
		
		m4.add(23L, 423L);
		
		Consumer<?> c=(T)->{};
	}
	
	interface IMsg {
		void testM(String e);
		
	}
	@FunctionalInterface
	interface IMsg2 {
		void add(Long a,Long b);
	}
	
	void testMsg (IMsg e) {
		System.out.println(2222);
	}
	
	
}
