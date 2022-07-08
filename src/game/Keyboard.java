package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
	public Player p;
	Keyboard(Player p) {
		this.p = p;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		p.onkeydown(key);
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		p.onkeyup(key);
	}
}