package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Inf {
	public boolean animated = false;
	public int id = 0;
	public int imgX = 0;
	public int imgY = 0;
	public int imgId = 0;
	public String[] recipe;
	public double dmg = 0;
	public boolean canUse = false;
	public boolean dealsDamage = false;
	public boolean canPlace = true;
	public boolean craftable = false;
	public boolean canBreak = true;
	public double health = 100;
	public float dropWidth = 32;
	public float dropHeight = 32;
	public int frames = 0;
	public int max_count = 0;
	public int frame = 0;
	public int count = 0;
	public static Inf[] importedInfs = new Inf[999];
	
	public void readFile(String path, int id) {
		File lo_path = new File(".");
		String local_path = lo_path.getAbsolutePath().substring(0, lo_path.getAbsolutePath().length() - 1);
		
		File inf_file = new File(local_path+"src\\game\\"+path);
		
		Scanner sc;
		
		try {
			sc = new Scanner(inf_file);
			
			boolean read = false;
			
			while (sc.hasNextLine()) {
				String ln = sc.nextLine();
				if (ln.startsWith("[") && read) {
					return;
				}
				
				if (ln.startsWith("["+id+"]")) {
					read = true;
					continue;
				}
				
				if (read && ln.indexOf("=") >= 0) {
					this.handleValue(ln.split("=")[0], ln.split("=")[1]);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		importedInfs[id] = this;
	}
	
	public void setInf(Inf d) {
		this.id = d.id;
		this.animated = d.animated;
		this.imgX = d.imgX;
		this.imgY = d.imgY;
		this.recipe = d.recipe;
		this.craftable = d.craftable;
		this.canBreak = d.canBreak;
		this.canPlace = d.canPlace;
		this.dealsDamage = d.dealsDamage;
		this.dmg = d.dmg;
		this.canUse = d.canUse;
		this.dropWidth = d.dropWidth;
		this.dropHeight = d.dropHeight;
		this.frames = d.frames;
		this.count = d.count;
		this.max_count = d.max_count;
	}
	
	public void handleValue(String name, String value) {
		switch (name) {
			case "id":
				this.id = Integer.parseInt(value);
			break;
			case "animated":
				this.animated = Boolean.parseBoolean(value);
			break;
			case "imgX":
				this.imgX = Integer.parseInt(value);
			break;
			case "imgY":
				this.imgY = Integer.parseInt(value);
			break;
			case "recipe":
				this.recipe = value.split(",");
			break;
			case "craftable":
				this.craftable = Boolean.parseBoolean(value);
			break;
			case "canBreak":
				this.canBreak = Boolean.parseBoolean(value);
			break;
			case "canPlace":
				this.canPlace = Boolean.parseBoolean(value);
			break;
			case "dealsDamage":
				this.dealsDamage = Boolean.parseBoolean(value);
			break;
			case "dmg":
				this.dmg = Double.parseDouble(value);
			break;
			case "canUse":
				this.canUse = Boolean.parseBoolean(value);
			break;
			case "dropWidth":
				this.dropWidth = Float.parseFloat(value);
			break;
			case "dropHeight":
				this.dropHeight = Float.parseFloat(value);
			break;
			case "frames":
				this.frames = Integer.parseInt(value);
			break;
			case "count":
				this.max_count = Integer.parseInt(value);
			break;
		}
	}
}
