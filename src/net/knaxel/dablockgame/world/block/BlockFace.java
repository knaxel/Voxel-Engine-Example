package net.knaxel.dablockgame.world.block;


public class BlockFace {
 
    public BlockType type;
    public int side;
    
    public BlockFace(BlockType type) {
		super();
		this.type = type;
	}
    

	public boolean equals(final BlockFace face) { return face.type == this.type ; }
}