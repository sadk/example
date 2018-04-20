package parttern.gcz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SwinObserverExample {
	JFrame frame;
	public static void main(String[] args) {
		SwinObserverExample example = new SwinObserverExample();
		example.go();
	}
	
	public void go () {
		frame = new JFrame();
		JButton button = new JButton("should i do it ?");
		button.addActionListener(new AngleListener());
		button.addActionListener(new DeviListener());
		frame.getContentPane().add(BorderLayout.CENTER,button);
	}
}	
class AngleListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("don't do that!!!");
		
	}
}
class DeviListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("don't do that!!!");
		
	}
}

