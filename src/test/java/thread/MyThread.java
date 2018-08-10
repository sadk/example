package thread;

public class MyThread {
	
	public static void main(String[] args) {
		 
		
		Thread t1= new MyAThread( );
		Thread t2= new MyAThread( );
		Thread t3= new MyAThread( );
		
		
		t1.start();
		t2.start();
		t3.start();
		
	}

	
	
}
class MyAThread extends Thread{
	private volatile int ticket = 20;
	@Override
	public void run() {
		for(int i=0;i<200;i++) {
			if(ticket>0) {
				System.out.println("买票 ==> "+ ticket-- );
			}
		}
	}
 
}
