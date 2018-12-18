package framework;

import java.util.concurrent.CyclicBarrier;

public class ConcurrentTest {
	public static void main(String[] args) {
		int num = 50;
		CyclicBarrier cb = new CyclicBarrier(num);
		
		for(int i=0;i<num;i++) {
			new Thread(()->{
				
			}) ;
		}
	}
}

