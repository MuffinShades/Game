package game;

public class Game {
	public static int expect_FPS = 1400;
	public static int expect_TPS = 120;
	
	public static double getFPSDif() {
		if (Main.FPS > 0) {
			return Game.expect_FPS / Main.FPS;
		} else {
			return 1;
		}

	}
	
	public static boolean Collide(float x, float y, int w, int h, float x1, float y1, int w1, int h1) {
		return (x < x1 + w1 && x + w > x1 && y < y1 + h1 && y + h > y1);
	}
	
	public static double getTPSDif() {
		if (Main.TPS > 0) {
			return Game.expect_TPS / Main.TPS;
		} else {
			return 1;
		}
	}
	
	public static double getGlobalDif() {
		/*return (
				(Game.getTPSDif() + Game.getFPSDif())
		- 1);*/
		
		return 1;
	}
}
