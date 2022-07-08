package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Character {
	
	public static float x = 0, y = 0, vx = 0, vy = 0, speed = 3.5f;
	public static int w = 0, h = 0;
	
	public Character(float x, float y, int w, int h) {
		Character.x = x;
		Character.y = y;
		Character.w = w;
		Character.h = h;
	}
	
	public void onkeydown(char e) {
		
	}
	
	public void onkeyup(char e) {
		
	}
	
	public abstract void tick();
	public abstract void render(Graphics ctx);
}
