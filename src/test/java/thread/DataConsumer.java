package thread;

import java.util.Random;

public class DataConsumer implements Runnable {
	private Data data;

	public DataConsumer(Data data) {
		this.data = data;
	}

	@Override
	public void run() {
		Random random = new Random();
		for (String message = data.take(); !message.equals("DONE"); message = data.take()) {
			System.out.format("MESSAGE RECEIVED: %s%n", message);
		}
		try {
			Thread.sleep(random.nextInt(1000));
		} catch (InterruptedException e) {
			
		}
	}

	public static void main(String[] args) {
		Data drop = new Data();
		(new Thread(new DataProducer(drop))).start();
		(new Thread(new DataConsumer(drop))).start();
	}
}
