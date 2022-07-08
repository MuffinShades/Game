package game;

import java.util.LinkedList;

public class Entity {
	public float x = 0, y = 0, vx = 0, vy = 0;
	public int id = 0;
	public boolean falling = true, ai = true, hostile = false, jumping = false, colliding = false, attacking = false;
	public double dmg = 0;
	public Point2d target;
	public int w = Main.pix;
	public int h = Main.pix;
	
	Entity(int id, float x, float y, boolean ai, boolean hostile) {
		this.x = x;
		this.y = y;
		this.ai = ai;
		this.hostile = hostile;
	}
	
	Entity(int id, float x, float y, boolean ai, boolean hostile, double dmg) {
		this.x = x;
		this.y = y;
		this.ai = ai;
		this.hostile = hostile;
		this.dmg = dmg;
	}
	
	public void tick() {
		//collision check
		LinkedList<Object> bl = ObjectHandler.blocks;
		
		boolean resetCollision = true;
		
		for (int i = 0; i < bl.size(); i++) {
			Block b = (Block) bl.get(i);
			if (Math.sqrt(Math.pow(Math.abs(b.x - this.x), 2) + Math.pow(Math.abs(b.y - this.y), 2)) < 200) {
				/*
				 * 
				 * Now start collision
				 * 
				 */
				
				if (b.collide((float) (this.x + (0.75 * b.w * Math.signum(this.vx))), this.y, this.w, this.h)) {
					this.jump();
				}
				
				if (b.collide(this.x + this.vx, this.y, this.w, this.h)) {
					this.vx = 0;
					this.colliding = true;
					resetCollision = false;
				}
				
				if (this.falling) {
					if (b.collide(this.x, this.y + this.vy, this.w, this.h)) {
						this.falling = false;
						this.y = b.y - this.h;
						this.vy = 0;
					}
				}
			} else {
				if (b.x > this.x) {
					break;
				} else {
					continue;
				}
			}
		}
		
		if (resetCollision) this.colliding = false;
		
		if (this.target != null && !this.colliding) {
			this.vx += 0.1 * Math.signum(this.x - this.target.x);
			
			if (this.attacking && this.hostile) {
				this.jump();
			}
		}
		
		if (this.falling) {
			this.vy += 0.1;
		}
		
		if (this.jumping && this.vy > 0) this.jumping = false;
		
		//change position
		
		this.x += this.vx;
		this.y += this.vy;
	}
	
	public void jump() {
		this.jumping = true;
		this.vy = -5;
	}
}
