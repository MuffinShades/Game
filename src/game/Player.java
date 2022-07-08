package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Player extends Character {
	public static String sprite_location = "";
	
	public int GW, GH;
	public boolean falling = false;
	public boolean canJump = false;
	public boolean ml = false, mr = false, col = false;
	public boolean infJump = true;
	public static int inventorySize = 5;
	public Item[] inventory = new Item[inventorySize];
	public static int selectedSlot = -1;
	public boolean has_space = true;
	public BufferedImage inventory_tile;
	public boolean img_ld = false;
	
	Player(float x, float y, int w, int h, int gw, int gh) {
		super(x, y, w, h);
		
		this.GW = gw;
		this.GH = gh;
		
		this.init_imgs();
	}
	
	public void render(Graphics ctx) {
		if (!img_ld) load_img();
		//draw player
		ctx.setColor(Color.BLACK);
		ctx.fillRect(this.GW / 2 - Character.w / 2, this.GH / 2 - Character.h / 2, Character.w, Character.h);
		
		//draw inventory
		for (int i = 0; i < this.inventory.length; i++) {
			// ctx2d = (Graphics2D) ctx;
			Item cell = this.inventory[i];
			
			Graphics2D ctx2d = (Graphics2D) ctx;
			
			if (i == this.selectedSlot) {
				/*ctx.setColor(Color.BLACK);
				ctx.fillRect((i*48+i*5) + 5, 5, 48, 48);*/
				ctx2d.drawImage(this.inventory_tile, (i*48+i*5) + 5, 5, 48, 48, null);
			} else {
				/*ctx.setColor(Color.BLACK);
				ctx.fillRect((i*48+i*5) + 5, 10, 48, 48);*/
				ctx2d.drawImage(this.inventory_tile, (i*48+i*5) + 5, 10, 48, 48, null);
			}
			
			if (cell != null) {
				//draw cell
				if (i == this.selectedSlot) {
					ctx2d.drawImage(cell.img, (i*48+i*5) + 5 + 5, 10, 38, 38, null);
				} else {
					ctx2d.drawImage(cell.img, (i*48+i*5) + 5 + 5, 10 + 5, 38, 38, null);
				}
			}
		}
	}
	
	public void tick() {
		//update offsets
		Main.offX = Character.x - this.GW / 2 + Character.w / 2;
		Main.offY = Character.y - this.GH / 2 + Character.h / 2;
		
		//update player position
		Character.x += Character.vx * Game.getGlobalDif();
		Character.y += Character.vy * Game.getGlobalDif();
		
		//gravity
		
		if (this.falling) {
			
		}
		
		LinkedList<Object> blocks = ObjectHandler.blocks;
		
		//valid blocks
		LinkedList<Object> vb = new LinkedList<Object>();
		
		for (int i = 0; i < blocks.size(); i++) {
			if (i > blocks.size() || blocks.get(i) == null || blocks.get(i).x > Character.x) break;
			Object b = ObjectHandler.getObj(i);
			if (Math.sqrt(Math.abs(Character.x - b.x) * Math.abs(Character.x - b.x) + Math.abs(Character.y - b.y) * Math.abs(Character.y - b.y)) < 100) {
				vb.add(b);
			}
		}
		
		boolean pf = true;
		boolean pj = true;
		
		for (int i = 0; i < vb.size(); i++) {
			Object b = vb.get(i);
			if (b.collide(Character.x+1, Character.y + Character.vy, Character.w-2, Character.h)) {
				this.falling = false;
				this.canJump = true;
				pf = false;
				this.y -= this.vy;
				this.y = b.y - Character.h;
				this.vy = 0;
			}
			
			if (b.collide(Character.x+1, Character.y + Character.vy + 2, Character.w-2, Character.h)) {
				pj = false;
			}
			
			if (b.collide(Character.x + Character.vx, Character.y - 1, Character.w, Character.h)) {
				if ((this.y + this.h) - b.y <= Main.pix / 2.1 && (this.y + this.h) - b.y > 0) {
					this.y = b.y - this.h;
				} else {
					this.x = this.x < b.x ? b.x - this.w : b.x + b.w;
					this.vx = 0;
					this.col = true;
				}
			} else {
				this.col = false;
			}
		}
		
		if (pf) {
			this.falling = true;
		}
		
		if (pj && !this.infJump) {
			this.canJump = false;
		}
		
		if (this.falling && this.vy < 5) {
			this.vy += 0.1;
		} else if (this.vy > 0) {
			this.vy -= 0.1;
		}
		
		if (this.mr) {
			if (this.vx < 5) {
				this.vx += 0.1;
			}
		}
		
		if (this.ml) {
			if (this.vx > -5) {
				this.vx -= 0.1;
			}
		}
		
		if (!this.mr && !this.ml && !this.col) {
			if (Math.round(this.vx) != 0) {
				this.vx += -Math.signum(this.vx) * 0.1f;
			} else {
				this.vx = 0.0f;
			}
		}
	}
	
	public void onkeydown(int e) {
		switch (e) {
			case KeyEvent.VK_D:
				this.mr = true;
			break;
			case KeyEvent.VK_A:
				this.ml = true;
			break;
			case KeyEvent.VK_SPACE:
				if (this.canJump) {
					Character.vy = -1f * Character.speed;
				}
			break;
			case KeyEvent.VK_S:
				Character.vy = Character.speed;
			break;
			case KeyEvent.VK_E:
				new GroundItem(1, x, y);
			break;
			case KeyEvent.VK_Q:
				((Lootable) this.inventory[0]).loot(this.x, this.y);
			break;
		}

		if (e >= 49 && e < this.inventory.length + 49) {
			if (this.selectedSlot == e - 49) {
				this.selectedSlot = -1;
			} else {
				this.selectedSlot = e - 49;
			}
		}
	}
	
	public void load_img() {
		File p = new File("");
		
		String pth = p.getAbsoluteFile()+"\\bin\\game\\"+SpriteSheet.getSheetSrc("inventory_tile");
		
		File inven_img_file = new File(pth);
		
		try {
			this.inventory_tile = ImageIO.read(inven_img_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.inventory_tile = this.inventory_tile.getSubimage(0,0,16*10,16*10);
		
		this.img_ld = true;
		
		this.inventory[0] = new Lootable(0, "id:1,ch:1/id:2,ch:50");
		this.inventory[1] = new Item(1);
	}
	
	public static void openInventory() {
		
	}
	
	public boolean giveItem(Item item) {
		for (int i = 0; i < this.inventory.length; i++) {
			if (this.inventory[i] == null) {
				this.inventory[i] = item;
				return true;
			}
		}
		has_space = false;
		return false;
	}
	
	public void onkeyup(int e) {
		switch (e) {
			case KeyEvent.VK_D:
				this.mr = false;
			break;
			case KeyEvent.VK_A:
				this.ml = false;
			break;
			case KeyEvent.VK_W:
				//Character.vy = 0;
			break;
			case KeyEvent.VK_S:
				Character.vy = 0;
			break;
		}
	}
	
	public void init_imgs() {
	}
}
