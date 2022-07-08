package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * 
 * This class contains all sprite sheet data and affixing functions
 * 
 * */

public class SpriteSheet {
	public static int w = 16;
	public static int h = 16;
	
	public static int scale = 10;
	
	public static int affixPosistion(int v) {
		return v * w * scale;
	}
	
	public static int scale(int v) {
		return v * scale;
	}
	
	public static String getSheetSrc(String data_value) {
		File p = new File(".");
		File f = new File(p.getAbsolutePath()+"\\src\\game\\Settings\\sheet_set.inf");
		
		Scanner sc;
		
		try {
			sc = new Scanner(f);
			
			while (sc.hasNextLine()) {
				String ln = sc.nextLine();
				
				if (ln.indexOf("=") >= 0 && ln.split("=").length > 1) {
					if (ln.split("=")[0].equalsIgnoreCase(data_value)) {
						return ln.split("=")[1];
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getIdRange(int id, int[] range) {
		for (int i = 0; i < range.length; i++) {
			if (range[i] > id) {
				if (i > 0) {
					return (range[i-1]+1)+"-"+range[i];
				} else {
					return "0-"+range[i];
				}
			}
		}
		return Integer.toString(id);
	}
}
