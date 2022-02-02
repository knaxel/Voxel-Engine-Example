package net.knaxel.dablockgame.entity.components;

import net.knaxel.dablockgame.engine.utils.vector.Vector3;

public class Velocity extends EntityComponent{
	private Vector3 velocity;

	public Velocity() {
		velocity= new Vector3(0);
	}
	
	public Vector3 getVector() {
		return velocity;
	}

	public void set(float x, float y, float z) {
		this.velocity.set(x, y,z);;
	}
	
	
	
	
}
