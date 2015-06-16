import java.awt.BorderLayout;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Main{
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("GameJam");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(new Panel(), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
