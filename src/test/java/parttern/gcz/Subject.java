package parttern.gcz;

public interface Subject {
	void add(Observer observer);
	void remove(Observer obsesrver);
	void notifyObserver();
}
