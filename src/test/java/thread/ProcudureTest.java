package thread;

public class ProcudureTest {
	public static void main(String[] args) {
		Info info = new Info(); //公共的数据对象
		Productor p = new Productor(info); //,产生一组数据
		Consumer c=new Consumer(info); // 获取一组数据（用于消费这组数据)
		
		new Thread(p).start();
		new Thread(c).start();
	}
}

class Info {
	public Info () {};
	private String title;
	private String content;
	
	private boolean flag; // true可以生产， false可以取数据

	public synchronized void set(String title,String content) {
		if(this.flag == false) {
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.title = title;
		try{
			Thread.sleep(200);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		this.content = content;
		
		super.notify();
		this.flag = false;
		
	}
	
	public synchronized void get(){
		if(this.flag) {
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try{
			Thread.sleep(100);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		System.out.println(this.title+" - "+ this.content);
		
		super.notify();
		this.flag = true;
	}
}

class Productor implements Runnable {
	Info info;
	public Productor(Info info) {
		this.info = info;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(200L);
			for (int i = 0; i < 20; i++) {
				if (i % 2 == 0) {
					Thread.sleep(200);
					this.info.set("张三", "我是中国人");

				} else {
					Thread.sleep(200);
					this.info.set("小泉", "我是日本人");
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

class Consumer implements Runnable {
	Info info ;
	public Consumer(Info info ) {
		this.info = info;
	}
	
	@Override
	public void run() {
		for(int i=0;i<30;i++) {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			info.get();
		}
		
	}
}