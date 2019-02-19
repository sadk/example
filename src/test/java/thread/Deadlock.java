package thread;

import java.util.Timer;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

public class Deadlock {
	static class Friend {
		private final String name;

		public Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public synchronized void bow(Friend bower) {
			System.out.format("%s: %s" + "  has bowed to me!%n", this.name, bower.getName());
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
			System.out.format("%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
		}
	}

	public static void main(String[] args) {
		/*
		final Friend alphonse = new Friend("Alphonse");
		final Friend gaston = new Friend("Gaston");
		new Thread(() -> alphonse.bow(gaston)).start();
		new Thread(() -> gaston.bow(alphonse)).start();
		*/
		Timer timer  = new Timer("aa");
		timer.scheduleAtFixedRate(new java.util.TimerTask() {
			@Override
			public void run() {
				 System.out.println(this);
			}
		},1000L,1000L);
	}
}
