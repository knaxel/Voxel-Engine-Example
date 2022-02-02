package net.knaxel.dablockgame.engine.utils.vector;

import java.io.Serializable;

public class Vector3 extends Vector implements Serializable{


	private static final long serialVersionUID = 1L;

	public float x, y, z;

	


	public Vector3() {
		set(0,0,0);
	}

	
	public Vector3(float x, float y, float z) {
		set(x, y, z);
	}

	public Vector3(Vector3 v) {
		this(v.x, v.y, v.z);
	}
	
	public Vector3(float i) {
		set(i, i, i);
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

	

	
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	
	public Vector3 translate(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	
	public static Vector3 add(Vector3 left, Vector3 right, Vector3 dest) {
		if (dest == null)
			return new Vector3(left.x + right.x, left.y + right.y, left.z + right.z);
		else {
			dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
			return dest;
		}
	}

	
	public static Vector3 sub(Vector3 left, Vector3 right, Vector3 dest) {
		if (dest == null)
			return new Vector3(left.x - right.x, left.y - right.y, left.z - right.z);
		else {
			dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
			return dest;
		}
	}

	
	public static Vector3 cross(
			Vector3 left,
			Vector3 right,
			Vector3 dest)
	{

		if (dest == null)
			dest = new Vector3();

		dest.set(
				left.y * right.z - left.z * right.y,
				right.x * left.z - right.z * left.x,
				left.x * right.y - left.y * right.x
				);

		return dest;
	}



	
	public Vector negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	
	public Vector3 negate(Vector3 dest) {
		if (dest == null)
			dest = new Vector3();
		dest.x = -x;
		dest.y = -y;
		dest.z = -z;
		return dest;
	}


	
	public Vector3 normalise(Vector3 dest) {
		float l = length();

		if (dest == null)
			dest = new Vector3(x / l, y / l, z / l);
		else
			dest.set(x / l, y / l, z / l);

		return dest;
	}

	
	public static float dot(Vector3 left, Vector3 right) {
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}

	
	public static float angle(Vector3 a, Vector3 b) {
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

		return this;

	}

	


	
	public String toString() {
		StringBuilder sb = new StringBuilder(64);

		sb.append("Vector3f[");
		sb.append(x);
		sb.append(", ");
		sb.append(y);
		sb.append(", ");
		sb.append(z);
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

	
	public void setZ(float z) {
		this.z = z;
	}

	
	public float getZ() {
		return z;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vector3 other = (Vector3)obj;
		
		if (x == other.x && y == other.y && z == other.z) return true;
		
		return false;
	}




	public void add(float f, float g, float h) {
		this.x+=f;
		this.y+=g;
		this.z+=h;
	}




	public void add(Vector3 v) {
		this.x+=v.x;
		this.y+=v.y;
		this.z+=v.z;
		
	}




	public static Vector3 getVectorFromRotation(Vector3 direction) {
		float pitchRadian = (float) (direction.y * (Math.PI / 180)); // X rotation
		float yawRadian = (float) (direction.x * (Math.PI / 180)); // Y rotation
		float lx = (float) ( Math.sin(yawRadian) * Math.cos(pitchRadian));
		float ly = (float) -(Math.sin(pitchRadian));
		float lz = (float) -( Math.cos(yawRadian) * Math.cos(pitchRadian));
		return new Vector3(lx,ly,lz);
	}
}

