package thread;

public class Data {
	private String message;
	
	private boolean empty = true;
	
	public synchronized String take() {
		while (empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		empty = true;
		
		notifyAll();
		return message;
	}
	
	public synchronized void put(String message) {
		while (!empty) {
			try {
				wait();
			} catch (Exception ex) {

			}
		}

		empty = false;

		this.message = message;
		notifyAll();
	}

}
