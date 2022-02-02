package net.knaxel.dablockgame.engine.utils.vector;

public class Vector2i {
	public int x;
	public int y;

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + this.x;
		hash = 31 * hash + this.y;
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		Vector2i that = (Vector2i) other;
		return (this.x == that.x) && (this.y == that.y);
	}

}