package org.lsqt.jdk;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.printf("===%d %s \n",324,"234哈哈");
		
		FutureTask<Integer> ft = new FutureTask<>(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				 System.out.println("==========call()!!!!");
				return 435;
			}
		}) ;
		
		new Thread(ft,"xxxx").start();
		System.out.println(ft.get());
	}
}
