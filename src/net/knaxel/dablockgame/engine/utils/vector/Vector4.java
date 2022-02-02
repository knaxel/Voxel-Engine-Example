package net.knaxel.dablockgame.engine.utils.vector;

import java.io.Serializable;

public class Vector4 extends Vector implements Serializable{

	private static final long serialVersionUID = 1L;

	public float x, y, z, w;

	
	public Vector4() {
		super();
	}

	


	
	public Vector4(float x, float y, float z, float w) {
		set(x, y, z, w);
	}

	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	
	public void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	


	
	public float lengthSquared() {
		return x * x + y * y + z * z + w * w;
	}

	
	public Vector4 translate(float x, float y, float z, float w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}

	
	public static Vector4 add(Vector4 left, Vector4 right, Vector4 dest) {
		if (dest == null)
			return new Vector4(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
		else {
			dest.set(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
			return dest;
		}
	}

	
	public static Vector4 sub(Vector4 left, Vector4 right, Vector4 dest) {
		if (dest == null)
			return new Vector4(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
		else {
			dest.set(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
			return dest;
		}
	}


	
	public Vector negate() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
		return this;
	}

	
	public Vector4 negate(Vector4 dest) {
		if (dest == null)
			dest = new Vector4();
		dest.x = -x;
		dest.y = -y;
		dest.z = -z;
		dest.w = -w;
		return dest;
	}


	
	public Vector4 normalise(Vector4 dest) {
		float l = length();

		if (dest == null)
			dest = new Vector4(x / l, y / l, z / l, w / l);
		else
			dest.set(x / l, y / l, z / l, w / l);

		return dest;
	}

	
	public static float dot(Vector4 left, Vector4 right) {
		return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
	}

	
	public static float angle(Vector4 a, Vector4 b) {
		float dls = dot(a, b) / (a.length() * b.length());
		if (dls < -1f)
			dls = -1f;
		else if (dls > 1.0f)
			dls = 1.0f;
		return (float)Math.acos(dls);
	}

	


	
	public Vector scale(float scale) {
		x *= scale;
		y *= scale;
		z *= scale;
		w *= scale;
		return this;
	}

	

	public String toString() {
		return "Vector4f: " + x + " " + y + " " + z + " " + w;
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

	
	public void setZ(float z) {
		this.z = z;
	}


	
	public float getZ() {
		return z;
	}

	
	public void setW(float w) {
		this.w = w;
	}

	
	public float getW() {
		return w;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vector4 other = (Vector4)obj;
		
		if (x == other.x && y == other.y && z == other.z && w == other.w) return true;
		
		return false;
	}
}