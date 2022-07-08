package game;

public class BlockType {
	public String name = "";
	public int id = 0;
	public boolean canPlace = true;
	public boolean canCollide = true;
	public int imgX = 0;
	public int imgY = 0;
	
	public void setImgX(int v) {
		this.imgX = v;
	}
	
	public void setImgY(int v) {
		this.imgY = v;
	}
	
	public void setName(String v) {
		this.name = v;
	}
	
	public void setId(int v) {
		this.id = v;
	}
	
	public void setCanPlace(boolean v) {
		this.canPlace = v;
	}
	
	public void setCanCollide(boolean v) {
		this.canCollide = v;
	}
}
