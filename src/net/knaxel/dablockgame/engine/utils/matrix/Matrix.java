package net.knaxel.dablockgame.engine.utils.matrix;

import java.io.Serializable;
import java.nio.FloatBuffer;

import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.entity.components.Camera;

public abstract class Matrix implements Serializable {
	public static final Matrix4 createTransformationMatrix(Vector3 translation, float rx, float ry,
			float rz, Vector3 scale) {
		Matrix4 matrix = new Matrix4();
		matrix.setIdentity();
		Matrix4.translate(translation, matrix, matrix);
		Matrix4.rotate((float) Math.toRadians(rx), new Vector3(1,0,0), matrix, matrix);
		Matrix4.rotate((float) Math.toRadians(ry), new Vector3(0,1,0), matrix, matrix);
		Matrix4.rotate((float) Math.toRadians(rz), new Vector3(0,0,1), matrix, matrix);
		Matrix4.scale(scale, matrix, matrix);
		return matrix;
	}
public static final Matrix4 createProjetionMatrix(float ratio, float fov, float farplane, float nearplane) {

	float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * ratio);
	float x_scale = y_scale / ratio;
	float frustum_length = farplane - nearplane;

	Matrix4 projectionMatrix = new Matrix4();
	projectionMatrix.m00 = x_scale;
	projectionMatrix.m11 = y_scale;
	projectionMatrix.m22 = -((farplane + nearplane) / frustum_length);
	projectionMatrix.m23 = -1;
	projectionMatrix.m32 = -((2 * nearplane * farplane) / frustum_length);
	projectionMatrix.m33 = 0;
	return projectionMatrix;
}
	public static final Matrix4 createViewMatrix(Camera camera) {
		Matrix4 viewMatrix = new Matrix4();
		viewMatrix.setIdentity();
		Matrix4.rotate((float) (camera.getPitch() * (Math.PI / 180)), new Vector3(1, 0, 0), viewMatrix,
				viewMatrix);
		Matrix4.rotate((float) (camera.getYaw() * (Math.PI / 180)), new Vector3(0, 1, 0), viewMatrix, viewMatrix);
		Vector3 cameraPos = camera.getPosition();
		Vector3 negativeCameraPos = new Vector3(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	protected Matrix() {
		super();
	}

	
	public abstract Matrix setIdentity();


	
	public abstract Matrix invert();


	
	public abstract Matrix load(FloatBuffer buf);


	
	public abstract Matrix loadTranspose(FloatBuffer buf);


	
	public abstract Matrix negate();


	
	public abstract Matrix store(FloatBuffer buf);


	
	public abstract Matrix storeTranspose(FloatBuffer buf);


	
	public abstract Matrix transpose();


	
	public abstract Matrix setZero();


	
	public abstract float determinant();


}