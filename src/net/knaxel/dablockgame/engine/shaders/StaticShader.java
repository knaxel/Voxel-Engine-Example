package net.knaxel.dablockgame.engine.shaders;


import net.knaxel.dablockgame.engine.utils.matrix.Matrix;
import net.knaxel.dablockgame.engine.utils.matrix.Matrix4;
import net.knaxel.dablockgame.engine.utils.vector.Vector2;
import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.entity.components.Camera;

public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/net/knaxel/dablockgame/engine/shaders/VertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/net/knaxel/dablockgame/engine/shaders/FragmentShader.glsl";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	private int location_sunColor;
	private int location_sunPosition;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "attrib1");
		super.bindAttribute(1, "attrib2");
	}
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		
		location_sunColor = super.getUniformLocation("sunColor");
		location_sunPosition = super.getUniformLocation("sunPosition");
		
	}
	
	public void sunPosition(Vector3 pos) {
		super.loadVector(location_sunPosition, pos);
	}
	public void sunColor(Vector3 color) {
		super.loadVector(location_sunColor, color);
		
	}
	public void loadInt(int i, String str ) {
		super.loadInt(super.getUniformLocation(str), i);
	}
	public void loadTransformationMatrix(Matrix4 matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4 viewMatrix = Matrix.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4 projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}

	public void loadUVScale(Vector2 uv_size) {
		super.loadVec2(super.getUniformLocation("uv_scale"), uv_size);
		
	}
	

}