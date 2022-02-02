package net.knaxel.dablockgame.engine.utils.vector;

import java.io.Serializable;

public abstract class Vector implements Serializable {


	private static final long serialVersionUID = 1L;
	protected Vector() {
		super();
	}
 
	
	public final float length() {
		return (float) Math.sqrt(lengthSquared());
	}


	
	public abstract float lengthSquared();



	
	public abstract Vector negate();


	
	public final Vector normalise() {
		float len = length(); 
		if (len != 0.0f) {
			float l = 1.0f / len;
			return scale(l);
		} else
			throw new IllegalStateException("Zero length vector");
	}


	
	public abstract Vector scale(float scale);



}