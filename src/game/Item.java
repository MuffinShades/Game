package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item extends Inf {
	public BufferedImage img;
	public int[] id_range = {100,200,300,400,500};
	public String imgSrc = SpriteSheet.getSheetSrc("item_id:"+SpriteSheet.getIdRange(id, id_range));
	public BufferedImage[] img_frames;
	public static BufferedImage[][] animations = new BufferedImage[999][];
	
	Item(int id) {
		this.id = id;
		
		this.readFile("inf\\item.inf", this.id);	
		
		initImg();
	}
	
	Item (int id, Inf d) {
		this.id = id;
		
		this.setInf(d);
	}
	
	public void initImg() {
		
		File p = new File(".");
		
		try {
			this.img = ImageIO.read(new File(p.getAbsolutePath()+"\\bin\\game\\"+this.imgSrc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!this.animated) {
			this.img = this.img.getSubimage((int) (this.imgX * 16 * 10), (int) (this.imgY * 16 * 10), 16 * 10, 16 * 10);
		} else {
			if (animations[this.id] != null) {
				this.img_frames = animations[this.id];
			} else {
				this.img_frames = new BufferedImage[this.frames];
				
				for (int i = 0; i < this.frames; i++) {
					this.img_frames[i] = this.img.getSubimage((int) ((i + this.imgX) * 16 * 10) , (int) (this.imgY * 16 * 10), 16 * 10, 16 * 10);
				}
				
				animations[this.id] = this.img_frames;
			}
		}
	}
}
