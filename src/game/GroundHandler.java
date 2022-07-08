package game;

import java.awt.Graphics;
import java.util.LinkedList;

public class GroundHandler {
	public static LinkedList<GroundItem> g_obj = new LinkedList<GroundItem>();
	public static boolean h = true;
	
	public static void tick() {
		for (int i = 0; i < g_obj.size(); i++) {
			GroundItem obj = g_obj.get(i);
			obj.tick();
		}
	}
	
	public static void render(Graphics ctx) {
		for (int i = 0; i < g_obj.size(); i++) {
			GroundItem obj = g_obj.get(i);
			if ((obj.x - Main.offX) < 0 || (obj.x - Main.offX) > Main.WIDTH || (obj.y - Main.offY) < 0 || (obj.y - Main.offY) > Main.HEIGHT) {
				continue;
			} else {
				g_obj.get(i).render(ctx);
			}
		}
	}
	
	public void add(GroundItem g) {
		g_obj.add(g);
	}
	
	public void remove(GroundItem g) {
		g_obj.remove(g);
	}
	
	public static LinkedList<GroundItem> getItems() {
		return g_obj;
	}
}
