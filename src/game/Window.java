package game;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Window {
	public JFrame win;
	
	Window(int w, int h, String title, Main m) {
		win = new JFrame(title);
		
		win.setPreferredSize(new Dimension(w, h));
		win.setMaximumSize(new Dimension(w, h));
		win.setMinimumSize(new Dimension(w, h));
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocationRelativeTo(null);
		
		win.setVisible(true);
		//win.add(m);
		
		//m.start();
	}
}
