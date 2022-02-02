package net.knaxel.dablockgame.engine.utils.vector;

public class Vector3i{
	
	public int x,y,z;

	public Vector3i(){
		x=0;y=0;z=0;
	}
	public Vector3i(int x, int y, int z){
		this.x=x;this.y=y;this.z=z;
	}

	

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + this.x;
		hash = 31 * hash + this.y;
		hash = 31 * hash + this.z;
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (this.getClass() != o.getClass())
			return false;
		Vector3i ob = ((Vector3i)o);
	      return (ob.x==x && ob.y==y && ob.z==z);
	}

	public float lengthSquared() {
		// TODO Auto-generated method stub
		return x*x+y*y+z*z;
	}

	public Vector3i negate() {
		x=-x;
		y=-y;
		z=-z;
		return this;
	}

	public Vector3i scale(float scale) {
		x*=scale;
		y*=scale;
		z*=scale;
		return this;
	}



}
