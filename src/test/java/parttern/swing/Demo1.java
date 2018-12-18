package parttern.swing;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JButton;

import com.sun.jersey.core.header.reader.HttpHeaderReader.Event;

public class Demo1 {

	public static void main(String[] args) {
		Frame frame = new Frame();
		
		JButton button = new JButton("我该 干啥呢?");
		button.addActionListener((event) -> System.out.println(Event.values()));

		button.addActionListener((event) -> System.out.println("bbbbbbbbbb"));
		
		frame.add(BorderLayout.CENTER, button);
		frame.setSize(400,400);
		frame.setVisible(true);
	}

}


class WeatherData {
	
}
class DoshBar1 {
	
}
