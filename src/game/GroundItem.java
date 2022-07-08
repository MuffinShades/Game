package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class GroundItem extends Item {
	
	public double x;
	public double y;
	public float vx = 0;
	public float vy = 0;
	public float w = this.dropWidth;
	public float h = this.dropHeight;
	public float size = 1;
	public boolean falling = true;
	public int id = 0;
	public boolean colliding = false;
	
	//number of ticks before you can pick up the item
	public int spawn_tick = 1000;
	
	//handler
	public static GroundHandler hand = new GroundHandler();

	GroundItem(int id, float x, float y) {
		super(id);
		
		this.id = id;
		
		this.x = x;
		this.y = y;
		this.w *= size;
		this.h *= size;
		
		//add to object array
		hand.add(this);
	}
	
	GroundItem(int id, float x, float y, float size) {
		super(id);
		
		this.id = id;
		
		this.x = x;
		this.y = y;
		this.size = size;
		
		File p = new File(".");
		
		try {
			this.img = ImageIO.read(new File(p.getAbsolutePath()+"\\bin\\game\\"+imgSrc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.img = this.img.getSubimage((int) (this.imgX * 16 * 10), (int) (this.imgY * 16 * 10), 16 * 10, 16 * 10);
		
		//add to object array
		hand.add(this);
	}
	
	public void tick() {
		if (!this.colliding || this.vx != 0) {
		LinkedList<Object> blocks = Main.hnd.getObjects();
		boolean cf = false;
		for (int i = 0; i < blocks.size(); i++) {
			Block b = (Block) blocks.get(i);
			if (Math.sqrt(Math.abs(b.x - this.x) + Math.abs(b.y - this.y)) < 200) {
				boolean br = false;
				if (b.collide((int) this.x, (int) (this.y + this.vy), (int) this.w, (int) this.h)) {
					this.falling = false;
					this.y = b.y - this.h;
					this.vy = 0;
					br = true;
					this.colliding = true;
					
					//slow down velocity
					if (Math.abs(Math.floor(this.vx * 50) / 50) > 0) {
						this.vx += -0.05f * Math.signum(this.vx);
					} else {
						this.vx = 0;
					}
				}
				if (b.collide((int) (this.x + this.vx), (int) (this.y + (-1f * Math.signum(this.vy))), (int) this.w, (int) this.h)) {
					this.vx = 0;
					if (this.x < b.x) {
						this.x = b.x - this.w;
					} else {
						this.x = b.x + b.w;
					}
					br = true;
				}
				
				if (br) break;
			} else {
				continue;
			}
		}
		
		if (cf) this.falling = true;
		
		if (this.falling && this.vy < 2) {
			this.vy += 0.1;
		}
		
		if (this.spawn_tick <= 0 && Main.p.has_space && Object.collide((float) this.x, (float) this.y, (int) this.w, (int) this.h, Main.p.x, Main.p.y, Main.p.w, Main.p.h)) {
			boolean result = Main.p.giveItem(new Item(this.id));
			
			if (result) {
				hand.remove(this);
			}
		} else if (this.spawn_tick > 0) {
			this.spawn_tick--;
		}
		
		//slow down velocity
		if (Math.abs(Math.floor(this.vx * 20) / 20) > 0) {
			this.vx += -0.02f * Math.signum(this.vx);
		} else {
			this.vx = 0;
		}
		}
		
		this.x += vx * Game.getGlobalDif();
		this.y += vy * Game.getGlobalDif();
	}
	
	public void setVx(float value) {
		this.vx = value;
	}
	
	public void setVy(float value) {
		this.vy = value;
	}
	
	public void render(Graphics ctx) {
		Graphics2D ctx2d = (Graphics2D) ctx;
		
		if (!this.animated) {
			ctx2d.drawImage(img, (int) (this.x - Main.offX), (int) (this.y - Main.offY), (int) (this.w), (int) (this.h), null);
		} else {
			//BufferedImage e = this.img.getSubimage((int) ((this.frame + this.imgX) * 16 * 10) , (int) (this.imgY * 16 * 10), 16 * 10, 16 * 10);
			
			ctx2d.drawImage(this.img_frames[this.frame], (int) (this.x - Main.offX), (int) (this.y - Main.offY), (int) (this.w), (int) (this.h), null);
		}
		
		if (this.animated) {
			this.count ++;
			
			if (this.count >= this.max_count) {
				this.frame ++;
				this.count = 0;
			}
			
			if (this.frame >= this.frames) {
				this.frame = 0;
			}
		}
	}
	
}
