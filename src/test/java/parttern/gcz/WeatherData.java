package parttern.gcz;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class WeatherData implements Subject {
	private Templater tmp;
	List<Observer> listener = new ArrayList<>();
	@Override
	public void add(Observer observer) {
		listener.add(observer);
		
	}

	@Override
	public void remove(Observer obsesrver) {
		listener.remove(obsesrver);
		
	}

	@Override
	public void notifyObserver() {
		for(Observer e: listener) {
			e.update(tmp);
		}
	}

	public void templateChanged() {
		 notifyObserver();
	}
	
	public void setTemplater(Templater tmp) {
		this.tmp = tmp;
		templateChanged();
	}
	
}
