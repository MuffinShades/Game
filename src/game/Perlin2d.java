package game;

public class Perlin2d {
	public int nodes = 256;
	public double seed = 0;
	public Point2d[][] grid = new Point2d[nodes][nodes];
	
	Perlin2d(double seed) {
		this.seed = seed;
		
		this.populate();
	}
	
	public double random() {
		return Math.random();
	}
	
	public double smooth(double x) {
		return 6*Math.pow(x, 5)-15*Math.pow(x, 4)+10*Math.pow(x, 3);
	}
	
	public double lerp(double x, double a, double b) {
		return a + smooth(x) * (b - a);
	}
	
	public Point2d ruv() {
		float theta = (float) (this.random() * 2 * Math.PI);
		
		return new Point2d(Math.cos(theta), Math.sin(theta));
	}
	
	public void populate() {
		for (int i = 0; i < this.nodes; i++) {
			Point2d[] row = new Point2d[this.nodes];
			
			for (int j = 0; j < this.nodes; j++) {
				row[j] = ruv();
			}
			
			this.grid[i] = row;
		}
	}
	
	public double dot(double x, double y, double vx, double vy) {
		Point2d gv;
		
		
		/*if (this.grid[(int) vx][(int) vy] != null) {
			gv = this.grid[(int) vx][(int) vy];
		} else {
			gv = this.ruv();
			this.grid[(int) vx][(int) vy] = gv;
		}*/
		
		int vxi = (int) (vx % 255);
		int vyi = (int) (vy % 255);
		
		gv = this.grid[vxi][vyi];
		
		Point2d dv = new Point2d(x - vx, y - vy);
		return dv.x * gv.x + dv.y * gv.y;
	}
	
	public float noise(double x, double y) {
		double xf = Math.floor(x);
		double yf = Math.floor(y);
		
		double tl = this.dot(x, y, xf, yf);
		double tr = this.dot(x, y, xf + 1, yf);
		double bl = this.dot(x, y, xf, yf + 1);
		double br = this.dot(x, y, xf + 1, yf + 1);
		
		double xt = this.lerp(x - xf, tl, tr);
		double xb = this.lerp(x - xf, bl, br);
		double v = this.lerp(y - yf, xt, xb);
		
		return (float) v;
	}
	
	public double layeredNoise(double x, double y, double freq, double amp, double freq_c, double amp_c, int layers, double offX, double offY) {
		double v = 0;
		double a = amp;
		double f = freq;
		
		for (int i = 0; i < layers; i++) {
			v += Math.abs(this.noise((x+offX) * a * f, (y+offY) * a * f)+1)*0.5f;
			a *= amp_c;
			f *= freq_c;
		}
		
		return v / layers;
	}
	
	public double layeredNoise(double x, double y, double freq, double amp, double freq_c, double amp_c, int layers) {
		double v = 0;
		double a = amp;
		double f = freq;
		
		for (int i = 0; i < layers; i++) {
			v += Math.abs(this.noise(x * a * f, y *a * f)+1)*0.5f;
			a *= amp_c;
			f *= freq_c;
		}
		
		return v / layers;
	}
}
