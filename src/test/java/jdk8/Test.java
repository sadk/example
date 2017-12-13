package jdk8;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
		
        JButton button1 = new JButton("点我!");
        JButton button2 = new JButton("也点我!");
		
        frame.getContentPane().add(button1);
        frame.getContentPane().add(button2);

        button1.addActionListener(e -> { System.out.println("这里是Lambda实现方式"); });
        
        //使用方法引用方式
        button2.addActionListener(Test::doSomething);
	}

    public static void doSomething(ActionEvent e) {
		System.out.println(e.getActionCommand());
        System.out.println("这里是方法引用实现方式");
        
    }
}
