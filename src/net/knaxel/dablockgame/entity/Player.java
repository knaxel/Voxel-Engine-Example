package net.knaxel.dablockgame.entity;

import net.knaxel.dablockgame.engine.utils.vector.Vector2;
import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.entity.components.Location;
import net.knaxel.dablockgame.entity.components.Velocity;

public class Player extends Entity  {
	
	public Player() {
		super();
		this.addComponent(new Location());
		this.addComponent(new Velocity());
		
	}
	public Player(Vector3 v, Vector2 p) {
		this.addComponent(new Location());
		this.addComponent(new Velocity());
		setPosition(v);
	}

	public Vector3 getPosition() {
		return this.getComponent(Location.class).getPosition();
		
	}
	public Location getLocation() {
		return (Location)this.getComponent(Location.class);
		
	}
	public void setPosition(Vector3 v) {
		 this.getComponent(Location.class).setPosition(v.x,v.y,v.z);
	}
	
	public void setLocation(float x, float y, float z) {
		 this.getComponent(Location.class).setPosition(x,y,z);
	}

}
