package game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Object {
	public float x, y;
	public int w, h, id;
	public Color color;
	public float imgX = 0.0f, imgY = 0.0f;
	public static int[] id_max = {100,200,300,400,500};
	public String imgSrc = SpriteSheet.getSheetSrc("block_id:"+SpriteSheet.getIdRange(this.id,id_max));
	public BufferedImage img;
	public boolean rem = false;
	public boolean visible = true;
	
	//update for more id's
	public static BufferedImage[] images = new BufferedImage[256];
	
	Object(float x, float y, int tileSize, int id) {
		this.x = x;
		this.y = y;
		this.w = tileSize;
		this.h = tileSize;
		this.id = id;
		
		//get inf
		try {
			this.imgX = Float.parseFloat(infParser.GetValue(id, "imgX", "block_inf.inf"));
		} catch(Exception e) {
			this.imgX = 0.0f;
		}
		try {
			this.imgY = Float.parseFloat(infParser.GetValue(id, "imgY", "block_inf.inf"));
		} catch(Exception e) {
			this.imgY = 0.0f;
		}
		this.imgSrc = infParser.GetValue(id, "imgSrc", "block_inf.inf");

		if (Object.images[this.id] == null) {
			try {
				File p = new File("");
				//System.out.println(p.getAbsolutePath()+"\\bin\\game\\"+imgSrc);
				
				//get image
				this.img = ImageIO.read(new File(p.getAbsolutePath()+"\\bin\\game\\"+imgSrc));
			} catch (IOException e) {
				System.out.println(e);
			}
			
			this.img = this.img.getSubimage((int) (this.imgX * 8 * 16), (int) (this.imgY * 8 * 16), 8 * 16, 8 * 16);
			
			Object.images[this.id] = this.img;
		} else {
			this.img = Object.images[this.id];
		}
	}
	
	public boolean collide(float x, float y, int w, int h) {
		return this.x < x + w && this.x + this.w > x && this.y <  y + h && this.y + this.h > y;
	}
	
	public static boolean collide(float x, float y, int w, int h, float x1, float y1, int w1, int h1) {
		return x1 < x + w && x1 + w1 > x && y1 <  y + h && y1 + h1 > y;
	}
	
	public abstract void tick();
}
