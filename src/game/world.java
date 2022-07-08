package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class world {
	public double seed = 0;
	
	private static LinkedList<String> biomes = new LinkedList<>();
	
	world(double seed) {
		this.seed = seed;
	}
	
	public final void Generate() {
		//load biomes in
		LoadBiomeFiles();
		
		//start to generate the world
		
		//create seeded random number generator
		SeedRandom sr = new SeedRandom(this.seed);
		
		//generate first 5 chuncks
		
		Perlin1d per = new Perlin1d(this.seed, 16, 8, 8, Main.HEIGHT, 0);
		
		String lastBiome = "";
		
		System.out.println("Generating Seed: " + seed);
		
		Log.print("Generating World with seed: " + String.format("%,.0f", seed));
		
		//generate world
		
		
		//temp world gen
		for (int i = 0; i < 20; i++) {
			new Block(i*Main.pix,Main.pix,Main.pix,0);
		}
		
		//logs
		Log.print("Generated a total of: "+ChunkHandler.c.size()+" chunks");
		Log.print("Cleaning up "+ObjectHandler.blocks.size()+" tiles...");
		
		//sort objects after creating world
		ObjectHandler.SortObj();
		
		//logs
		Log.print("Clean up complete!");
	}
	
	public static void LoadBiomeFiles() {
		//Load biomes
		
		File lo_path = new File(".");
		String local_path = lo_path.getAbsolutePath().substring(0, lo_path.getAbsolutePath().length() - 1);
		
		//get name of every biome
		File path_file = new File(local_path+"src\\game\\biomes\\");
		
		
		File[] files = path_file.listFiles();
		
		LinkedList<String> names = new LinkedList<>();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				names.add(files[i].getName());
			}
		}
		
		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i).substring(0, names.get(i).indexOf("."));
			System.out.println(name);
			String b = "";
			
			//get biome file
			File b_file = new File(local_path+"src\\game\\biomes\\"+names.get(i));
			System.out.println(local_path+"src\\game\\biomes\\"+names.get(i));
			Scanner sc;
			
			b+=name+",";
			
			try {
				sc = new Scanner(b_file);
				
				//read biome file
				while (sc.hasNextLine()) {
					String ln = sc.nextLine();
					
					if (!ln.startsWith("@") && !ln.startsWith("*") && ln.indexOf("-") >= 0) {
						//set the value
						//b.HandleValue(ln.substring(0, ln.indexOf("-")), ln.substring(ln.indexOf("-")+1));
						
						b += ln.substring(0, ln.indexOf("-"))+":"+ln.substring(ln.indexOf("-")+1)+",";
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			biomes.add(b);
		}
	}
}
