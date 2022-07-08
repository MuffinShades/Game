package game;

import java.awt.Color;

public class Block extends Object {
	public ObjectHandler hand = new ObjectHandler();
	
	Block(float x, float y, int tileSize, int id) {
		super(x, y, tileSize, id);
		
		//add block to block array
		hand.AddObject(this);
	}
	
	public void tick() {
		
	}
}
