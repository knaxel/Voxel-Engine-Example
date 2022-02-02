package net.knaxel.dablockgame.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import net.knaxel.dablockgame.engine.meshing.Mesh;
import net.knaxel.dablockgame.engine.meshing.TerrainMesh;

public class Loader {

	
	private HashMap<Integer, List<Integer>> vaos = new HashMap<>();
	
	private List<Integer> textures = new ArrayList<>();


	public Loader() {
	}

	public int loadTexture(String filename) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + filename + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		textures.add(texture.getTextureID());
		return texture.getTextureID();
	}

	public Mesh loadTerrainMesh(TerrainMesh meshData) {
		
		
		int vaoID = createVAO();
		bindIndicesBuffer(vaoID, meshData.indices);
		storeDataInIntAttrib(vaoID, 0, meshData.attrib1);
		storeDataInIntAttrib(vaoID, 1, meshData.attrib2);

		unbindVAO();
		return new Mesh(vaoID, meshData.indices.length);
		
	}
	public Mesh loadToMesh(float[] vertices, int[] indices, float[] uv, float[] normals, float[] textureInfo) {
		int vaoID = createVAO();
		bindIndicesBuffer(vaoID, indices);
		storeDataInAttributeList(vaoID, 0, 3, vertices);
		storeDataInAttributeList(vaoID, 1, 2, uv);
		storeDataInAttributeList(vaoID, 2, 3, normals);
		storeDataInAttributeList(vaoID, 3, 3, textureInfo);
		unbindVAO();
		return new Mesh(vaoID, indices.length);
	} 
	
	public void unloadVAO(int vao) {
		if(vaos.containsKey(vao)) {
			for(int vbo : vaos.get(vao)) {
				GL15.glDeleteBuffers(vbo);
			}
			GL30.glDeleteVertexArrays(vao);
			vaos.remove(vaos);
		}
	}
	public void cleanUp() {
		for (int vao : vaos.keySet()) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (Collection<Integer> vbos : vaos.values()) {
			for(int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
			}
	}

	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.put(vaoID, new ArrayList<Integer>());
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private void storeDataInAttributeList(int vao, int attributeNumber, int size, float[] data) {
		int vboID = GL15.glGenBuffers();
		vaos.get(vao).add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	private void storeDataInIntAttrib(int vao, int attributeNumber, int[] data) {
		int vboID = GL15.glGenBuffers();
		vaos.get(vao).add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL30.glVertexAttribIPointer(attributeNumber, 1, GL11.GL_INT, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	private void bindIndicesBuffer(int vao,int[] indices) {
		int vboID = GL15.glGenBuffers();
		vaos.get(vao).add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}