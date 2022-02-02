package net.knaxel.dablockgame.engine.systems;

import java.util.HashMap;
import java.util.NoSuchElementException;

import net.knaxel.dablockgame.engine.Engine;
import net.knaxel.dablockgame.engine.Loader;
import net.knaxel.dablockgame.engine.Renderer;
import net.knaxel.dablockgame.engine.meshing.Mesh;
import net.knaxel.dablockgame.engine.meshing.TerrainMesh;
import net.knaxel.dablockgame.engine.resource.Texture;
import net.knaxel.dablockgame.engine.shaders.StaticShader;
import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.engine.utils.vector.Vector3i;
import net.knaxel.dablockgame.entity.components.Camera;

public class RenderSystem extends EngineSystem{
	private Loader loader;
	private StaticShader shader;
	private Renderer renderer;
	private HashMap<Vector3i, Mesh> meshes = new HashMap<>();
	private Camera current_camera;
	
	private MeshingSystem meshSystem;
	
	private boolean wireframe = false;
	

	public RenderSystem(Engine e) {
		super(e);
		shader = new StaticShader();
		renderer = new Renderer(shader);
		loader = new Loader();
		current_camera=e.getCamera();
		renderer.loadProjectionMatrix(current_camera);

		Texture.TERRAIN.id = loader.loadTexture("terrain");
		meshSystem = engine.getSystem(MeshingSystem.class);
		
	}
	@Override
	public void tick(double dt) {
		int i = 0;
		while(meshSystem.hasMesh() && i < 5) {
			TerrainMesh meshData =  meshSystem.pop(); 
			if(meshData.attrib1.length == 0) {
				return;
			}
			Mesh mesh = loader.loadTerrainMesh(meshData);
			mesh.setPosition(meshData.chunk_loc.x * 16, meshData.chunk_loc.y * 16, meshData.chunk_loc.z * 16);

			meshes.put(meshData.chunk_loc , mesh);
			System.out.println("chunk mesh recieved :  # meshes - " + meshes.size() + ", (" + meshData.chunk_loc.x * 16+ ","+ meshData.chunk_loc.y * 16+ ","+ meshData.chunk_loc.z * 16 + ")");
			i++;
		}
	}
	
	public void render() {

		renderer.prepare();
		shader.start();
		shader.loadViewMatrix(current_camera);
		shader.sunColor(new Vector3(1, 1, .8f));
		shader.sunPosition(engine.getPlayer().getPosition());
		
		if(wireframe) {
			for (Mesh mesh : meshes.values()) {
	
				
				renderer.renderWireFrame(mesh, shader);
	
			}
			shader.stop();
		}else{

			for (Mesh mesh : meshes.values()) {
	
				
				renderer.render(mesh, shader, Texture.TERRAIN);
	
			}
			shader.stop();
		}
		
	}
	public void toggleWireframe() {
		wireframe = wireframe? false : true;
	}

}
