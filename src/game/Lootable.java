package game;

public class Lootable extends Item {
	public String loot_table;
	
	Lootable(int id, String loot_table) {
		super(id);
		
		this.loot_table = loot_table;
	}
	
	public void loot(float x, float y) {
		String[] spl = this.loot_table.split("/");
		
		for (int i = 0; i < spl.length; i++) {
			String r = spl[i];
			double chance = Double.parseDouble(r.split(",")[1].split(":")[1]);
			int id = Integer.parseInt(r.split(",")[0].split(":")[1]);
			
			if (Math.random() * 100 <= chance) {
				GroundItem g = new GroundItem(id, x, y);
				g.setVx((float) (Math.random() * 6 - 3) + 1);
				g.setVy((float) -Math.abs(Math.random() * 2) - 2);
			}
		}
	}
}
