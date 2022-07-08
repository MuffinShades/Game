package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.LinkedList;

public class ObjectHandler {
	//block array
	public static LinkedList<Object> blocks = new LinkedList<Object>();
	
	public static Object getObj(int i) {
		return blocks.get(i) != null ? blocks.get(i) : new Block(0,0,0,0);
	}
	
	public void tick() {
		/*for (int i = 0; i < blocks.size(); i++) {
			Object b = blocks.get(i);
			if (b != null) {
				//tick the block
				b.tick();
			}
		}*/
	}
	
	public void render(Graphics ctx) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i) == null) continue;
			if ((blocks.get(i).x - Main.offX) < 0 || (blocks.get(i).x - Main.offX) > Main.WIDTH || (blocks.get(i).y - Main.offY) < 0 || (blocks.get(i).y - Main.offY) > Main.HEIGHT || !blocks.get(i).visible) {
				if (blocks.get(i).x  - Main.offX > Main.WIDTH) {
					break;
				} else {
					continue;
				}
			} else {
				Object b = blocks.get(i);
				if (b != null) {
					//draw the block
					//ctx.setColor(b.color);
					//ctx.fillRect((int) (b.x - Main.offX), (int) (b.y - Main.offY), b.w, b.h);
					
					Graphics2D ctx2d = (Graphics2D) ctx;
					
					ctx2d.drawImage(b.img, (int) (b.x - Main.offX), (int) (b.y - Main.offY), b.w, b.h, null);
				}
			}
		}
	}
	
	public void AddObject(Object o) {
		this.blocks.add(o);
	}
	
	public LinkedList<Object> getObjects() {
		return blocks;
	}
	
	public static void SortObj() {
		Sort.mergeSort(blocks);
	}
	
	public static void RemovePendingBlocks() {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).rem) {
				blocks.remove(i);
			}
		}
	}
}
