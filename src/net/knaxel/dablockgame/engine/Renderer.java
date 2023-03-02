package net.knaxel.dablockgame.engine;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14C;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import net.knaxel.dablockgame.engine.meshing.Mesh;
import net.knaxel.dablockgame.engine.resource.Texture;
import net.knaxel.dablockgame.engine.shaders.StaticShader;
import net.knaxel.dablockgame.engine.utils.matrix.Matrix;
import net.knaxel.dablockgame.engine.utils.matrix.Matrix4;
import net.knaxel.dablockgame.entity.components.Camera;

public class Renderer {

	private StaticShader shader;
	
	public Renderer(StaticShader shader) {
		GL11.glLineWidth(2);
		this.shader = shader;
		
	}
	public void loadProjectionMatrix(Camera camera) {

		shader.start();
		shader.loadProjectionMatrix(camera.getProjectionMatrix());
		shader.stop();
	}
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0.0f, 0.0f, 1);
	}
	public void render(Mesh model, StaticShader shader, Texture t) {
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		Matrix4 transformationMatrix = Matrix.createTransformationMatrix(model.getPosition(),
				model.getRotX(), model.getRotY(), model.getRotZ(), model.getScale());
		shader.loadTransformationMatrix(transformationMatrix);

		
		
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); 
		GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT); 
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.id);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	public void renderWireFrame(Mesh model, StaticShader shader) {
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		Matrix4 transformationMatrix = Matrix.createTransformationMatrix(model.getPosition(),
				model.getRotX(), model.getRotY(), model.getRotZ(), model.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		
		shader.loadInt(1, "wireframe");
		GL13.glPolygonMode(GL13.GL_FRONT_AND_BACK, GL13.GL_LINE);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL13.glPolygonMode(GL13.GL_FRONT_AND_BACK, GL13.GL_FILL);
		shader.loadInt(0, "wireframe");
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	 
	}

}