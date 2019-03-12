package thread;

import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
	public static void main(String[] args) {
		ThreadLocalRandom ram = ThreadLocalRandom.current();
		int c = ThreadLocalRandom.current().nextInt(4, 77);
		System.out.println(c);
		
		new Thread(()->System.out.println(ThreadLocalRandom.current().nextInt(4, 77))).start();
		new Thread(()->System.out.println(ThreadLocalRandom.current().nextInt(4, 77))).start();
		new Thread(()->System.out.println(ThreadLocalRandom.current().nextInt(4, 77))).start();
		new Thread(()->System.out.println(ThreadLocalRandom.current().nextInt(4, 77))).start();
		new Thread(()->System.out.println(ThreadLocalRandom.current().nextInt(4, 77))).start();
		new Thread(()->System.out.println(ThreadLocalRandom.current().nextInt(4, 77))).start();
		new Thread(()->System.out.println(ThreadLocalRandom.current().nextInt(4, 77))).start();
		new Thread(()->System.out.println(ThreadLocalRandom.current().nextInt(4, 77))).start();
		
		String t;
		
	}
}
