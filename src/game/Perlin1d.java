package game;

public class Perlin1d {
	private double m = 4294967296.0f;
	private int A = 1664525, c = 1;
	private double tam = 0;
	private double seed = 0;
	private double amp, wll, dep, h;
	private double a, b;
	private double rv = seed, trv = seed;
	private int perlinType = 0;
	
	//Initiate class
	
	Perlin1d(double seed, double density, double amp, double waveLength, double maxHeight, int perlinType) {
		this.seed = Math.max(0, Math.min(seed, m));
		this.amp = amp;
		this.wll = waveLength;
		this.dep = density;
		this.h = maxHeight;
		this.perlinType = perlinType;
		
		a = rand(0 / dep);
		b = rand(0 / dep);
		
		rv = seed; 
		trv = seed;
	}
	
	Perlin1d(double seed, double density, double amp, double waveLength, double maxHeight) {
		this.seed = Math.max(0, Math.min(seed, m));
		this.amp = amp;
		this.wll = waveLength;
		this.dep = density;
		this.h = maxHeight;
		this.perlinType = 0;
		
		a = rand(0 / dep);
		b = rand(0 / dep);
		
		rv = seed; 
		trv = seed;
	}
	
	private double rand(double x) {
		if (Math.abs(this.rv) > this.tam) {
			this.rv = this.trv;
		}
		
		for (int i = 0; i < Math.abs(x) - (Math.abs(x) > Math.abs(this.tam) ? Math.abs(this.tam) : 0); i++) {
			this.rv = (this.A * this.rv +this. c) % this.m;
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
	
	private double trig(double v) {
		switch (perlinType) {
			case 1:
				return Math.sin(v);
			case 2:
				return Math.tan(v);
			case 3:
				return TrigPlus.sec(v);
			case 4:
				return TrigPlus.csc(v);
			case 5:
				return TrigPlus.cot(v);
			default:
				return Math.cos(v);
		}
	}
	
	private double perp(double a, double b, double x) {
		double f = x * Math.PI;
		double t = (1 - trig(f)) * 0.5;
		return a * (1 - t) + b * t;
	}
	
	public double noise(double x) {
		if (x % wll == 0) {
			a = b;
			b = rand(x / dep);
			return (h / 8 + a * amp);
		} else {
			return (h / 8 + perp(a, b, (x % wll) / wll) * amp);
		}
	}
	
	public void setAmp(double amp) {
		this.amp = amp;
	}
	
	public void setWll(double wll) {
		this.wll = wll;
	}
	
	public void setPerlinType(int perlinType) {
		this.perlinType = perlinType;
	}
	
	public double layeredNoise(double x, int layers, double amp_c, double wll_c) {
		double n = 0;
		
		double aa = amp, w = wll;
		
		for (int i= 0; i < layers; i++) {
			if (x % w == 0) {
				a = b;
				b = rand(x / dep);
				return (h / 8 + a * amp);
			} else {
				n += (h / 8 + perp(a, b, (x % w) / w) * aa);
			}
			
			aa *= amp_c;
			w *= wll_c;
		}
		
		return n / layers;
	}
}
