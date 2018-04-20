package parttern.gcz;

import java.awt.event.ActionEvent;

public class MainTest {

	public static void main(String[] args) {
		WeatherData data = new WeatherData();
		Observer mb1 = new Mb1Observer(data);

		data.setTemplater(new Templater(2343, 33, 22));
		 
	}

}
