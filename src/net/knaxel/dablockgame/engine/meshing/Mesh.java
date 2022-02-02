package net.knaxel.dablockgame.engine.meshing;

import net.knaxel.dablockgame.engine.utils.vector.Vector2;
import net.knaxel.dablockgame.engine.utils.vector.Vector3;

public class Mesh {

	private int vaoID;
	private int vertexCount;
	
	private Vector3 position;
	private float rotX, rotY, rotZ;
	private Vector3 scale;
	private Vector2 uv_size;
	private int textureID;

	public Mesh(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.position = new Vector3(0,0,0);
		this.rotX = 0.0f;
		this.rotY = 0.0f;
		this.rotZ = 0.0f;
		this.uv_size = new Vector2(1,1);
		this.scale = new Vector3(1,1,1);
		this.textureID = 0;
	}

	public Vector2 getUv_size() {
		return uv_size;
	}

	public void setUv_size(Vector2 uv_size) {
		this.uv_size = uv_size;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}
	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	public Vector3 getScale() {
		return scale;
	}

	public void setScale(float x, float y , float z) {
		this.scale.x = x;
		this.scale.y = y;
		this.scale.z = z;
	}

	public void setUVScale(int w, int h) {
		this.uv_size.x = w;
		this.uv_size.y = h;
		
	}

	public int getTextureID() {
		// TODO Auto-generated method stub
		return textureID;
	}

	public void setPosition(int x, int y, int z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		
	}


}