package game;

import java.util.LinkedList;

public class ChunkHandler {
	public static LinkedList<Chunk> c = new LinkedList<>();
	
	public static void tick() {
		
	}
	
	public static void addChunk(Chunk chunk) {
		//chunk.applyVisibleLighting();
		c.add(chunk);
		
		//ObjectHandler.SortObj();
	}
}
