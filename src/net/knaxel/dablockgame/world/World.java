package net.knaxel.dablockgame.world;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import net.knaxel.dablockgame.engine.utils.noise.ImprovedNoise;
import net.knaxel.dablockgame.engine.utils.vector.Vector2i;
import net.knaxel.dablockgame.engine.utils.vector.Vector3i;
import net.knaxel.dablockgame.world.block.Block;
import net.knaxel.dablockgame.world.block.BlockFace;
import net.knaxel.dablockgame.world.block.BlockType;

public class World {
	
	
	private ConcurrentHashMap<Vector3i, Chunk> chunks;
	
	public World() {
		
		
		chunks = new ConcurrentHashMap<>();
	}
	public Block getBlockAt(int x, int y, int z)  {

		Vector3i chunkCoord = new Vector3i((int)(x/Chunk.CHUNK_SIZE), (int)(y/Chunk.CHUNK_SIZE), (int)(z/Chunk.CHUNK_SIZE));
		if(! chunks.containsKey(chunkCoord)) {
          	return null;
		}

		Chunk chunk = chunks.get(chunkCoord);

             
		int cx = (int) (x%Chunk.CHUNK_SIZE);
		int cy = (int) (y%Chunk.CHUNK_SIZE);
		int cz = (int) (z%Chunk.CHUNK_SIZE);

		return chunk.getBlockAt(cx,cy,cz);
		
	}
	public boolean isBlockAt(int x, int y, int z) {

		Vector3i chunkCoord = new Vector3i((int)(x/Chunk.CHUNK_SIZE), (int)(y/Chunk.CHUNK_SIZE), (int)(z/Chunk.CHUNK_SIZE));
		if(! chunks.containsKey(chunkCoord)) {
			return false;
		}

		Chunk chunk = chunks.get(chunkCoord);
		
		
		int cx = (int) (x%Chunk.CHUNK_SIZE);
		int cy = (int) (y%Chunk.CHUNK_SIZE);
		int cz = (int) (z%Chunk.CHUNK_SIZE);
		

 		if(!chunk.isBlockAt(cx, cy, cz)) {
			return false;
		}
		BlockType type = chunk.getBlockAt(cx,cy,cz).type;
		return type != BlockType.AIR;
	}

	public boolean breakBlockAt( int x,  int y, int z) {

		if(!isBlockAt(x,y,z)) {
			return false;
		}

		Vector3i chunkCoord = new Vector3i((x/Chunk.CHUNK_SIZE), (y/Chunk.CHUNK_SIZE), (z/Chunk.CHUNK_SIZE));
		if(! chunks.containsKey(chunkCoord)) {
			return false;
		}
		Chunk chunk = chunks.get(chunkCoord);
		int cx = (int) (x%Chunk.CHUNK_SIZE);
		int cy = (int) (y%Chunk.CHUNK_SIZE);
		int cz = (int) (z%Chunk.CHUNK_SIZE);
		chunk.breakBlockAt(cx,cy,cz);
		return true;
	}
	public BlockFace f(int x, int y, int z, int side) {
		
		if(!isBlockAt(x,y,z)) {
			return null;
		}
		BlockType type = getBlockAt(x,y,z).type;
		
		BlockFace voxelFace = new BlockFace(type);
		
		voxelFace.side = side;

		return voxelFace;
	}
	public void generateChunk(Vector3i vec) {
		int x = vec.x; 
		int y = vec.y;
		int z = vec.z;
		
		Block[][][] blocks = new Block[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
		Block v;
		for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {

			for (int j =Chunk.CHUNK_SIZE-1; j >=0 ; j--) {

				for (int k = 0; k < Chunk.CHUNK_SIZE; k++) {
					v = new Block();
					v.type = BlockType.STONE; 
					blocks[i][j][k] = v;
				    

					  double scale = 6;
					if(ImprovedNoise.noise(((double)(i+Chunk.CHUNK_SIZE*x))/scale, ((double)(j+Chunk.CHUNK_SIZE*y))/scale,((double)(k+Chunk.CHUNK_SIZE*z))/scale) > 0) {

							v.type = BlockType.AIR;
							
					}
					if(j<Chunk.CHUNK_SIZE-1 && v.type!=BlockType.AIR && blocks[i][j+1][k].type == BlockType.AIR) {

						v.type = BlockType.GRASS;
					}
					blocks[i][j][k] = v;
				}
			}
		}
		
		Chunk chunk = new Chunk();
		chunk.setBlocks(blocks);
		chunks.put(new Vector3i(x,y,z), chunk);
	}
	public Chunk getChunk(int x,int y, int z) {
		
		return chunks.get(new Vector3i(x,y,z));
	}
	public boolean chunkLoaded(Vector3i v) {
		return chunks.containsKey(v);
	}
}
