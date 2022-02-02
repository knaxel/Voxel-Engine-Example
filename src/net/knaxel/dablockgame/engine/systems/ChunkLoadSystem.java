package net.knaxel.dablockgame.engine.systems;

import net.knaxel.dablockgame.engine.Engine;
import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.engine.utils.vector.Vector3i;
import net.knaxel.dablockgame.engine.utils.vector.Vector4i;
import net.knaxel.dablockgame.entity.components.Location;
import net.knaxel.dablockgame.world.Chunk;
import net.knaxel.dablockgame.world.World;

public class ChunkLoadSystem extends EngineSystem{

	private World world; 
	private Location loc;
	private MeshingSystem meshes;
	
	
	public ChunkLoadSystem(Engine e) {
		super(e);

		meshes = engine.getSystem(MeshingSystem.class);
		world = e.getWorld();
		loc = e.getPlayer().getComponent(Location.class);
	}
	
	public void bleh(double dt) {

		Vector3 pos = new Vector3(loc.getPosition()).translate(0f, -128f, 0f);

		for(int x=0;x<5; x++) {
			for(int y=0;y<5; y++) {
				for(int z=0;z<5; z++) {
		
					int cx = (int)(pos.x/Chunk.CHUNK_SIZE);
					int cy = (int)(pos.y/Chunk.CHUNK_SIZE);
					int cz = (int)(pos.z/Chunk.CHUNK_SIZE);
					int i = cx + x;
					int j=  cy + y;
					int k = cz + z;
					int l = (int) -(Math.sqrt( (y)*(y)+ (z)*(z)+ (x)*(x) )); 
		
					if(!world.chunkLoaded(new Vector3i(i,j,k)) ){
						world.generateChunk(new Vector3i(i,j,k));
						
						meshes.addJob(new Vector4i(i,j,k,l));
		
					}
				}
			}
		}


		System.out.println("chunk data loaded");
		
	}
}
