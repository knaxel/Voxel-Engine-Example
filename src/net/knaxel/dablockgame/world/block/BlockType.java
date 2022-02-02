package net.knaxel.dablockgame.world.block;

public enum BlockType {
	AIR(0),GRASS(1),STONE(2),DIRT(3);
	public int id;
	BlockType(int id){
		this.id = id;
	}
}
