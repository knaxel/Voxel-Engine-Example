package net.knaxel.dablockgame.depre;

class VoxelFace {
    
	
    public int type;
    public int side;
    
    public VoxelFace(int type) {
		super();
		this.type = type;
	}

	public boolean equals(final VoxelFace face) { return face.type == this.type; }
}