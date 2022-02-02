package net.knaxel.dablockgame.entity.components;

import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.world.World;

public class Location extends EntityComponent{
	
	private Vector3 position;
	private Vector3 rotation;
	private Vector3 direction;
	
	public Location() {
		position = new Vector3(0);
		rotation = new Vector3(0);
		direction = new Vector3(0);
	}
	public Vector3 getPosition() {
		return position;
	}
	public void setPosition(float x, float y, float z) {
		this.position.set(x, y, z);;
	}
	public Vector3 getRotation() {
		return rotation;	
	}
	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}
	public Vector3 getDirection() {
		return direction;
	}
	public void setRotation(float yaw, float pitch, float roll) {
		this.direction.set(yaw,pitch,roll);;
	}
	public void addYaw(float f) {
		this.direction.add(f, 0, 0);
		
	}
	public void addPitch(float f) {
		this.direction.add(0, f, 0);
		
	}
	public double getYaw() {
		return direction.x;
	}
	public double getPitch() {
		// TODO Auto-generated method stub
		return direction.y;
	}
	public void addPosition(Vector3 velocity) {
		position.x+=velocity.x;
		position.y+=velocity.y;
		position.z+=velocity.z;
		
	}
	
	
	
}
