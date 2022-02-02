package net.knaxel.dablockgame.engine.utils.vector;

public class Vector4i extends Vector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x,y,z,w;             

	public Vector4i(){
		x=0;y=0;z=0;w=0;
	}
	public Vector4i(int x, int y, int z,int w){
		this.x=x;this.y=y;this.z=z;this.w=w;
	}

	

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + this.x;
		hash = 31 * hash + this.y;              
		hash = 31 * hash + this.z;
		hash = 31 * hash + this.w;
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
		Vector4i ob = ((Vector4i)o);
	      return (ob.x==x && ob.y==y && ob.z==z&& ob.w==w);
	}

	public float lengthSquared() {
		// TODO Auto-generated method stub
		return x*x+y*y+z*z+w*w;
	}

	public Vector4i negate() {
		x=-x;
		y=-y;
		z=-z;
		w=-w;
		return this;
	}

	public Vector4i scale(float scale) {
		x*=scale;
		y*=scale;
		z*=scale;
		w*=scale;
		return this;
	}

}
