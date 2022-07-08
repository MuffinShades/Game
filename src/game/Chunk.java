package game;

public class Chunk {
	public float x;
	public float y;
	public final static int CHUNCK_SIZE_X = 16, CHUNCK_SIZE_Y = 16;
	public int chunckId = 0;
	public static int freeC_id;
	public Block[][] tiles = new Block[CHUNCK_SIZE_X][CHUNCK_SIZE_Y];
	public boolean visible = true;
	
	Chunk(float x, float y) {
		this.x = x;
		this.y = y;
		
		this.chunckId = freeC_id;
		freeC_id++;
		
		ChunkHandler.addChunk(this);
	}
	
	public void addTile(float x, float y, int id) {
		if ((int) Math.floor(x) >= CHUNCK_SIZE_X || (int) Math.floor(y) >= CHUNCK_SIZE_Y) return;
		
		Block b = new Block((float) (x * Main.pix + this.x), (float) (y * Main.pix + this.y), Main.pix, id);
		
		b.visible = true;
		
		this.tiles[(int) Math.floor(x)][(int) Math.floor(y)] = b;
	}
	
	public void setVisible(boolean v) {
		this.visible = v;
		
		for (int i = 0; i < this.tiles.length; i++) {
			for (int j = 0; j < this.tiles[i].length; j++) {
				if (this.tiles[i][j] != null) {
					this.tiles[i][j].visible = true;
				}
			}
		}
		
		if (v) {
			//
		}
	}
	
	public void applyVisibleLighting() {
		for (int i = 0; i < this.tiles.length; i++) {
			for (int j = 0; j < this.tiles[i].length; j++) {
				if (this.tiles[i][j] != null) {
					if (j > 0 && this.tiles[i][j-1] == null) {
						//this.tiles[i][j].visible = true;
					} else if (j > 0) {
						//this.tiles[i][j].visible = false;
					}
				}
			}
		}
	}
	
	public static boolean chunkAt(float x, float y) {
		
		//detect chunks
		for (int i = 0; i < ChunkHandler.c.size(); i++) {
			if (ChunkHandler.c.get(i).x == x && ChunkHandler.c.get(i).y == y) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Chunk getChunkAt(float x, float y) {
		
		//detect chunks
		for (int i = 0; i < ChunkHandler.c.size(); i++) {
			if (ChunkHandler.c.get(i).x == x && ChunkHandler.c.get(i).y == y) {
				return ChunkHandler.c.get(i);
			}
		}
		
		return null;
	}
	
	public void apply2dNoise(float t, Perlin2d cvn, int i, int j, int l, double n, float offY) {
		for (int k = 1; k <  Chunk.CHUNCK_SIZE_Y; k++) {
			
			//calculate perlin offsets for 2d noise and block chuncks
			float o = Math.round(Math.round(n*Main.pix)/Main.pix);
			
			//create 2d noise value
			
			double val = cvn.layeredNoise(j + i*Chunk.CHUNCK_SIZE_X, o + k, 0.01, 1.2, 1.85, 0.85, 7, 200, 200);
			
			
			if (val < t) {
				if ((float) o + k - offY >= 0) {
					this.addTile(j,  (float) o + k - offY, 0);
				}
			}
			
			//increase cave generation rate
			if (t > 0.45f) {
				//t -= 0.005f;
			}
		}
	}
	
	public void fill(int i, int j, double n, float offY) {
		if (n >= 0) {
			for (int k = 1; k <  Chunk.CHUNCK_SIZE_Y; k++) {
				
				//calculate perlin offsets for block chuncks
				float o = Math.round(Math.round(n*Main.pix)/Main.pix);
				
				//add the tile to the chunk
				if ((float) o + k - offY >= 0) {
					this.addTile(j, (float) o + k - offY, 0);
				}
			}
		}
	}
}