package net.knaxel.dablockgame.world;

import net.knaxel.dablockgame.world.block.Block;
import net.knaxel.dablockgame.world.block.BlockFace;
import net.knaxel.dablockgame.world.block.BlockType;



public class Chunk {
	public static final int CHUNK_SIZE = 16;
	private Block[][][] blocks ;
	
	public boolean isBlockAt(int x, int y, int z) {
		return x>=0&&y>=0&&z>=0&&x<CHUNK_SIZE&&y<CHUNK_SIZE&&z<CHUNK_SIZE&&blocks[x][y][z].type != BlockType.AIR;
	}
	

	public void setBlocks(Block[][][] blocks) {
		this.blocks = blocks;
	}
	public Block getBlockAt(int x, int y, int z) {
		return blocks[x][y][z];
	}
	public void breakBlockAt(int x, int y, int z) {
		blocks[x][y][z].type = BlockType.AIR;
	}	


}
