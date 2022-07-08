package game;

public class Biome {
	public static int amp = 8;
	public static int wll = 8;
	public static float frequency = 0.5f;
	public static String name = "";
	public static int perlinType;
	
	Biome(String name) {
		this.name = name;
	}
	
	public static void HandleValue(String name, String value) {
		switch(name) {
			case "amp":
				amp = Integer.parseInt(value);
			break;
			case "wll":
				wll = Integer.parseInt(value);
			break;
			case "terrainType":
				perlinType = Integer.parseInt(value);
			break;
		}
	}
	
	public static Biome parse(String data) {
		String[] s = data.split(",");
		if (s.length > 0) {
			Biome r = new Biome(s[0]);
		
			for (int i = 1; i < s.length; i++) {
				r.HandleValue(s[i].split(":")[0], s[i].split(":")[1]);
			}
		
			return r;
		} else {
			return new Biome("???");
		}
	}
}
