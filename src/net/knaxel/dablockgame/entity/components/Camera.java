package net.knaxel.dablockgame.entity.components;

import net.knaxel.dablockgame.engine.utils.matrix.Matrix;
import net.knaxel.dablockgame.engine.utils.matrix.Matrix4;
import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.engine.utils.vector.Vector4;
import net.knaxel.dablockgame.entity.Entity;

public class Camera extends EntityComponent{
	
	
	private Vector3 position;
	private Vector3 rotation;
	private float fov, near, far, ratio;
	private Matrix4 projectionMatrix;
	
	//frustum
	private Vector4[] frustum_normals = new Vector4[6];
	
	public Camera(float ratio, float fov, float near, float far) {
		position = new Vector3(0);
		rotation = new Vector3(0);
		this.ratio = ratio;
		this.fov = fov;
		this.near = near;
		this.far = far;
		
		projectionMatrix = Matrix.createProjetionMatrix(ratio, fov, far, near);
	}
	public void followEntity(Entity e) {
		Location loc = (Location) e.getComponent(Location.class);
		position = loc.getPosition();
		rotation = loc.getDirection();
	}
	public Matrix4 getProjectionMatrix(){
		return projectionMatrix;
	}
	public Vector4[] getFrustum() {
		
		
		Matrix4 fmat =  new Matrix4();
		Matrix4.mul(Matrix.createViewMatrix(this), projectionMatrix, fmat);


		frustum_normals[0].set(fmat.m33 + fmat.m30, fmat.m23 + fmat.m20, fmat.m13 + fmat.m10, fmat.m03 + fmat.m00);//left
		frustum_normals[1].set(fmat.m33 - fmat.m30, fmat.m23 - fmat.m20, fmat.m13 - fmat.m10, fmat.m03 - fmat.m00);//right
		frustum_normals[2].set(fmat.m33 + fmat.m31, fmat.m23 + fmat.m21, fmat.m13 + fmat.m11, fmat.m03 + fmat.m01);//bottom
		frustum_normals[3].set(fmat.m33 - fmat.m31, fmat.m23 - fmat.m21, fmat.m13 - fmat.m11, fmat.m03 - fmat.m01);//top
		frustum_normals[4].set(fmat.m33 + fmat.m32, fmat.m23 + fmat.m22, fmat.m13 + fmat.m12, fmat.m03 + fmat.m02);//near
		frustum_normals[5].set(fmat.m33 - fmat.m32, fmat.m23 - fmat.m22, fmat.m13 - fmat.m12, fmat.m03 - fmat.m02);//far
		return frustum_normals;
		
	}
	public Matrix4 getViewMatrix() {
		return Matrix.createViewMatrix(this);
	}
	public void setPosition(Vector3 position) {
		this.position = position;
	}
	public Vector3 getPosition(){
		return position;
		
	}
	public float getPitch() {
		return rotation.y;
	}
	public float getYaw() {
		return rotation.x;
	}
	public float getRoll() {
		return rotation.z;
	}
	public void setPitch(float pitch) {
		rotation.y = pitch;
	}
	public void setYaw(float yaw) {
		rotation.x = yaw;
	}
	public void setRoll(float roll) {
		rotation.z = roll;
	}

}
