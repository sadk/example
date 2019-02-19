package thread;

import java.util.Random;
import java.util.concurrent.locks.Condition;
public class DataProducer implements Runnable{
	private Data data;
	public DataProducer(Data data) {
		this.data = data;
	}
	@Override
	public void run() {
		String importantInfo[] = {
				"aaaa",
				"bbbb",
				"cccc",
				"dddd"
		};
		
		Random random = new Random();
		for(int i=0;i<importantInfo.length;i++) {
			data.put(importantInfo[i]);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		data.put("Done!!!");
	}
}

