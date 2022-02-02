package net.knaxel.dablockgame.engine.utils.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;

public class Vector2 extends Vector implements Serializable {

	private static final long serialVersionUID = 1L;

	public float x, y;

	
	public Vector2() {
		super();
	}

	
	
	public Vector2(float x, float y) {
		set(x, y);
	}

	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	


	
	public float lengthSquared() {
		return x * x + y * y;
	}

	
	public Vector2 translate(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	
	public Vector negate() {
		x = -x;
		y = -y;
		return this;
	}

	
	public Vector2 negate(Vector2 dest) {
		if (dest == null)
			dest = new Vector2();
		dest.x = -x;
		dest.y = -y;
		return dest;
	}


	
	public Vector2 normalise(Vector2 dest) {
		float l = length();

		if (dest == null)
			dest = new Vector2(x / l, y / l);
		else
			dest.set(x / l, y / l);

		return dest;
	}

	
	public static float dot(Vector2 left, Vector2 right) {
		return left.x * right.x + left.y * right.y;
	}



	
	public static float angle(Vector2 a, Vector2 b) {
		float dls = dot(a, b) / (a.length() * b.length());
		if (dls < -1f)
			dls = -1f;
		else if (dls > 1.0f)
			dls = 1.0f;
		return (float)Math.acos(dls);
	}

	
	public static Vector2 add(Vector2 left, Vector2 right, Vector2 dest) {
		if (dest == null)
			return new Vector2(left.x + right.x, left.y + right.y);
		else {
			dest.set(left.x + right.x, left.y + right.y);
			return dest;
		}
	}

	
	public static Vector2 sub(Vector2 left, Vector2 right, Vector2 dest) {
		if (dest == null)
			return new Vector2(left.x - right.x, left.y - right.y);
		else {
			dest.set(left.x - right.x, left.y - right.y);
			return dest;
		}
	}

	
	public Vector store(FloatBuffer buf) {
		buf.put(x);
		buf.put(y);
		return this;
	}

	
	public Vector load(FloatBuffer buf) {
		x = buf.get();
		y = buf.get();
		return this;
	}

	
	public Vector scale(float scale) {

		x *= scale;
		y *= scale;

		return this;
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder(64);

		sb.append("Vector2f[");
		sb.append(x);
		sb.append(", ");
		sb.append(y);
		sb.append(']');
		return sb.toString();
	}

	
	public final float getX() {
		return x;
	}

	
	public final float getY() {
		return y;
	}

	
	public final void setX(float x) {
		this.x = x;
	}

	
	public final void setY(float y) {
		this.y = y;
	}	
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vector2 other = (Vector2)obj;
		
		if (x == other.x && y == other.y) return true;
		
		return false;
	}
	
}