package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CallableTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Thread t = new MyCall();
		
		new Thread(t,"黄牛1").start();
		new Thread(t,"黄牛2").start();
		new Thread(t,"黄牛3").start();
		new Thread(t,"黄牛4").start();
		new Thread(t,"黄牛5").start();
		
	}
}
class MyCall extends Thread {
	private int ticket = 59;
	
	@Override
	public void run() {
		
		
		for(int i=0;i<20;i++) {
			sale();
			//Lock lock = new ReentrantLock();
			
			//lock.lock();
			//synchronized (this) {

			//}
			
			//lock.unlock();
		}
	}
	       
	public synchronized void sale() { 
		if(ticket >0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"买票："+ticket--);
		}
	}
}