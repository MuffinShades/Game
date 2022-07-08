package game;

public class SeedRandom {
	private static double m = 4294967296.0f;
	private static int A = 1664525, c = 1;
	private static double tam = 0;
	private static double seed = 0;
	private static double rv = seed, trv = seed;
	
	SeedRandom(double seed) {
		this.seed = seed;
		this.rv = seed;
		this.trv = seed;
	}
	
	public static double rand(double x, double seed) {
		rv = seed;
		trv = seed;
		
		if (Math.abs(rv) > tam) {
			rv = trv;
		}
		
		for (int i = 0; i < Math.abs(x) - (Math.abs(x) > Math.abs(tam) ? Math.abs(tam) : 0); i++) {
			rv = (A * rv + c) % m;
		}
		
		if (x == 0) {
			rv = (A * rv + c) % m;
		}
		
		if (x > tam) {
			trv = rv;
			tam = x;
		}
		
		return rv / m;
	}
	
	public static double rand(double x) {
		
		if (Math.abs(rv) > tam) {
			rv = trv;
		}
		
		for (int i = 0; i < Math.abs(x) - (Math.abs(x) > Math.abs(tam) ? Math.abs(tam) : 0); i++) {
			rv = (A * rv + c) % m;
		}
		
		if (x == 0) {
			rv = (A * rv + c) % m;
		}
		
		if (x > tam) {
			trv = rv;
			tam = x;
		}
		
		return rv / m;
	}
}
