package net.knaxel.dablockgame.engine.utils.matrix;

import java.io.Serializable;
import java.nio.FloatBuffer;

import net.knaxel.dablockgame.engine.utils.vector.Vector2;

public class Matrix2 extends Matrix implements Serializable {

	private static final long serialVersionUID = 1L;

	public float m00, m01, m10, m11;

	
	public Matrix2() {
		setIdentity();
	}

	
	public Matrix2(Matrix2 src) {
		load(src);
	}

	
	public Matrix2 load(Matrix2 src) {
		return load(src, this);
	}

	
	public static Matrix2 load(Matrix2 src, Matrix2 dest) {
		if (dest == null)
			dest = new Matrix2();

		dest.m00 = src.m00;
		dest.m01 = src.m01;
		dest.m10 = src.m10;
		dest.m11 = src.m11;

		return dest;
	}

	
	public Matrix load(FloatBuffer buf) {

		m00 = buf.get();
		m01 = buf.get();
		m10 = buf.get();
		m11 = buf.get();

		return this;
	}

	
	public Matrix loadTranspose(FloatBuffer buf) {

		m00 = buf.get();
		m10 = buf.get();
		m01 = buf.get();
		m11 = buf.get();

		return this;
	}

	
	public Matrix store(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m01);
		buf.put(m10);
		buf.put(m11);
		return this;
	}

	
	public Matrix storeTranspose(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m10);
		buf.put(m01);
		buf.put(m11);
		return this;
	}



	
	public static Matrix2 add(Matrix2 left, Matrix2 right, Matrix2 dest) {
		if (dest == null)
			dest = new Matrix2();

		dest.m00 = left.m00 + right.m00;
		dest.m01 = left.m01 + right.m01;
		dest.m10 = left.m10 + right.m10;
		dest.m11 = left.m11 + right.m11;

		return dest;
	}

	
	public static Matrix2 sub(Matrix2 left, Matrix2 right, Matrix2 dest) {
		if (dest == null)
			dest = new Matrix2();

		dest.m00 = left.m00 - right.m00;
		dest.m01 = left.m01 - right.m01;
		dest.m10 = left.m10 - right.m10;
		dest.m11 = left.m11 - right.m11;

		return dest;
	}

	
	public static Matrix2 mul(Matrix2 left, Matrix2 right, Matrix2 dest) {
		if (dest == null)
			dest = new Matrix2();

		float m00 = left.m00 * right.m00 + left.m10 * right.m01;
		float m01 = left.m01 * right.m00 + left.m11 * right.m01;
		float m10 = left.m00 * right.m10 + left.m10 * right.m11;
		float m11 = left.m01 * right.m10 + left.m11 * right.m11;

		dest.m00 = m00;
		dest.m01 = m01;
		dest.m10 = m10;
		dest.m11 = m11;

		return dest;
	}

	
	public static Vector2 transform(Matrix2 left, Vector2 right, Vector2 dest) {
		if (dest == null)
			dest = new Vector2();

		float x = left.m00 * right.x + left.m10 * right.y;
		float y = left.m01 * right.x + left.m11 * right.y;

		dest.x = x;
		dest.y = y;

		return dest;
	}

	
	public Matrix transpose() {
		return transpose(this);
	}

	
	public Matrix2 transpose(Matrix2 dest) {
		return transpose(this, dest);
	}

	
	public static Matrix2 transpose(Matrix2 src, Matrix2 dest) {
		if (dest == null)
			dest = new Matrix2();

		float m01 = src.m10;
		float m10 = src.m01;

		dest.m01 = m01;
		dest.m10 = m10;

		return dest;
	}

	
	public Matrix invert() {
		return invert(this, this);
	}

	
	public static Matrix2 invert(Matrix2 src, Matrix2 dest) {
		

		float determinant = src.determinant();
		if (determinant != 0) {
			if (dest == null)
				dest = new Matrix2();
			float determinant_inv = 1f/determinant;
			float t00 =  src.m11*determinant_inv;
			float t01 = -src.m01*determinant_inv;
			float t11 =  src.m00*determinant_inv;
			float t10 = -src.m10*determinant_inv;

			dest.m00 = t00;
			dest.m01 = t01;
			dest.m10 = t10;
			dest.m11 = t11;
			return dest;
		} else
			return null;
	}

	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(m00).append(' ').append(m10).append(' ').append('\n');
		buf.append(m01).append(' ').append(m11).append(' ').append('\n');
		return buf.toString();
	}

	
	public Matrix negate() {
		return negate(this);
	}

	
	public Matrix2 negate(Matrix2 dest) {
		return negate(this, dest);
	}

	
	public static Matrix2 negate(Matrix2 src, Matrix2 dest) {
		if (dest == null)
			dest = new Matrix2();

		dest.m00 = -src.m00;
		dest.m01 = -src.m01;
		dest.m10 = -src.m10;
		dest.m11 = -src.m11;

		return dest;
	}

	
	public Matrix setIdentity() {
		return setIdentity(this);
	}

	
	public static Matrix2 setIdentity(Matrix2 src) {
		src.m00 = 1.0f;
		src.m01 = 0.0f;
		src.m10 = 0.0f;
		src.m11 = 1.0f;
		return src;
	}

	
	public Matrix setZero() {
		return setZero(this);
	}

	public static Matrix2 setZero(Matrix2 src) {
		src.m00 = 0.0f;
		src.m01 = 0.0f;
		src.m10 = 0.0f;
		src.m11 = 0.0f;
		return src;
	}

	
	public float determinant() {
		return m00 * m11 - m01*m10;
	}
}