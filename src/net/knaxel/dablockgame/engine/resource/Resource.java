package net.knaxel.dablockgame.engine.resource;

import net.knaxel.dablockgame.world.block.BlockType;

public class Resource {
	
	public Resource() {
		
	}
	
	public void load() {
		
	}

	private static final int SOUTH = 0;
	private static final int NORTH = 1;
	private static final int EAST = 2;
	private static final int WEST = 3;
	private static final int UP = 4;
	private static final int DOWN = 5;

	public static final int getTextureID(BlockType type, int s) {

		switch (type) {
		case GRASS: {
			switch (s) {
			case UP:
				return 0;

			case DOWN:
				return 2;
			default:
				return 3;
			}
		}
		case DIRT: {
			return 2;
		}
		case STONE: {
			return 1;
		}
		default: {
			return 255;
		}

		}
	}

}
