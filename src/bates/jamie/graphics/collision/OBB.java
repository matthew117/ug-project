package bates.jamie.graphics.collision;

import static java.lang.Math.*;
import static bates.jamie.graphics.util.Matrix.*;
import static bates.jamie.graphics.util.Vector.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OBB extends Bound
{	
	public static final float EPSILON = 0.00001f;
	
	public float[][] rotation = new float[3][3];
	
	public float[] extents;
	
	public boolean[] validFaces = new boolean[6];
	
	public OBB(float c0, float c1, float c2,
			   float u0, float u1, float u2,
			   float e0, float e1, float e2)
	{
		setPosition(c0, c1, c2);
		setRotation(u0, u1, u2);
		extents = new float[] {e0, e1, e2};	
		Arrays.fill(validFaces, true);
	}
	
	public OBB(float x, float y, float z, float rx, float ry, float rz, float halfWidth, float halfHeight, float halfDepth, boolean[] validFaces)
	{
		setPosition(x, y, z);
		setRotation(rx, ry, rz);
		extents = new float[] {halfWidth, halfHeight, halfDepth};	
		this.validFaces = validFaces;
	}
	
	public OBB(float[] c, float[] u, float[] e, boolean[] v)
	{
		setPosition(c);
		setRotation(u[0], u[1], u[2]);
		this.extents = e;
		validFaces = v;
	}
	
	public boolean isValidCollision(float[] face)
	{
		float[][] faces = getAxisVectors(1);
		
		for(int i = 0; i < faces.length; i++)
			if(Arrays.equals(face, faces[i])) return validFaces[i];
		
		return false;
	}
	
	public void setRotation(float x, float y, float z) { rotation = getRotationMatrix(x, y, z); }
	
	public float getHeight() { return extents[1] * 2; }
	
	@Override
	public float[] getFaceVector(float[] p)
	{	
		float[] closest = closestPointToPoint(p);
		
		float[][] normals = getAxisVectors(1);
		
		float[] q = subtract(closest, c);
		
		float xScale = dot(q, rotation[0]) / extents[0];
		float yScale = dot(q, rotation[1]) / extents[1];
		float zScale = dot(q, rotation[2]) / extents[2];
		
		if(abs(yScale) >= abs(xScale) && abs(yScale) >= abs(zScale))
		{
			if(yScale < 0 && p[1] < c[1] - extents[1]) return normals[2];
			else if(yScale > 0) return normals[3];
		}
		if(abs(xScale) > abs(zScale))
		{
			if(xScale < 0) return normals[0];
			else return normals[1];
		}
		if(abs(xScale) == abs(zScale))
		{
			if(extents[0] < extents[2])
			{
				if(xScale < 0) return normals[0];
				else return normals[1];
			}
			else if(extents[0] == extents[2])
			{
				if(dot(q, rotation[0]) < dot(q, rotation[2]))
				{
					if(xScale < 0) return normals[0];
					else return normals[1];
				}
				else
				{
					if(zScale < 0) return normals[4];
					else return normals[5];
				}
			}
			else
			{
				if(zScale < 0) return normals[4];
				else return normals[5];
			}
		}
		else
		{
			if(zScale < 0) return normals[4];
			else return normals[5];
		}		
	}
	
	public float getPenetration(OBB b)
	{
		float p = Integer.MAX_VALUE;
		
		OBB a = this;
		
		float ra, rb, r;
		float[][] R = new float[3][3];
		float[][] AbsR = new float[3][3];
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
			{
				R[j][i] = dot(b.rotation[i], a.rotation[j]);
				AbsR[j][i] = abs(R[j][i]) + EPSILON;
			}
		
		float[] t = subtract(a.c, b.c);
		t = new float[] {dot(t, b.rotation[0]), dot(t, b.rotation[1]), dot(t, b.rotation[2])};		
		
		for(int i = 0; i < 3; i++)
		{
			ra = b.extents[i];
			rb = a.extents[0] * AbsR[i][0] + a.extents[1] * AbsR[i][1] + a.extents[2] * AbsR[i][2];
			r  = abs(t[i]);

			if(p > abs(rb - (r - ra))) p = abs(rb - (r - ra));
		}

		for(int i = 0; i < 3; i++)
		{
			ra = b.extents[0] * AbsR[0][i] + b.extents[1] * AbsR[1][i] + b.extents[2] * AbsR[2][i];
			rb = a.extents[i];
			r  = abs(t[0] * R[0][i] + t[1] * R[1][i] + t[2] * R[2][i]);

			if(p > abs(rb - (r - ra))) p = abs(rb - (r - ra));
		}
		
		return p;
	}
	
	public float getPenetration(Sphere s)
	{
		float[]  q = closestPointOnPerimeter(s.c);
		float[] _q = subtract(q, s.c);
		
		double ra = sqrt(_q[0] * _q[0] + _q[2] * _q[2]);
		double rb = s.r;
		
		return (float) abs(rb - ra);
	}
	
	@Override
	public float getMaximumExtent()
	{
		return (float) sqrt(dot(extents, extents));
	}
	
	/**
	 * An even index indicates that the vertex is on front of the OBB.
	 * Conversely, an odd index indicates that the vertex is on the back.
	 * The first four indices store the bottom vertices while the last,
	 * while the last four store the top vertices.
	 * If the modolo 4 of the index is 0 or 1, the vertex is on the left,
	 * else if the modolo 4 is 2 or 3, the vertex is on the right.
	 */
	public float[][] getVertices()
	{ 	
		float[][] vertices = new float[8][3];
		
		float[] eu0 = multiply(rotation[0], extents[0]);
		float[] eu1 = multiply(rotation[1], extents[1]);
		float[] eu2 = multiply(rotation[2], extents[2]);
		
		vertices[0] = subtract(subtract(subtract(c, eu0), eu1), eu2); //right bottom front 
		vertices[1] =      add(subtract(subtract(c, eu0), eu1), eu2); //left  bottom front
		vertices[2] = subtract(subtract(     add(c, eu0), eu1), eu2); //right bottom back
		vertices[3] =      add(subtract(     add(c, eu0), eu1), eu2); //left  bottom back
		vertices[4] = subtract(     add(subtract(c, eu0), eu1), eu2); //right top    front
		vertices[5] =      add(     add(subtract(c, eu0), eu1), eu2); //left  top    front
		vertices[6] = subtract(     add(     add(c, eu0), eu1), eu2); //right top    back
		vertices[7] =      add(     add(     add(c, eu0), eu1), eu2); //left  top    back
		
		return vertices;
	}
	
	@Override
	public List<float[]> getPixelMap()
	{ 
		List<float[]> vertices = new ArrayList<float[]>();
		
		for(int i = 1; i <= 100; i++)
			for(int j = 1; j <= 100; j++)
			{
				float[] x = multiply(rotation[0], extents[0] * ((float) i / 100));
				float[] z = multiply(rotation[2], extents[2] * ((float) j / 100));
				
				vertices.add(add(add(c, x), z));
				vertices.add(add(subtract(c, x), z));
				vertices.add(subtract(add(c, x), z));
				vertices.add(subtract(subtract(c, x), z));
			}
		
		return vertices;
	}
	
	@Override
	public boolean testOBB(OBB b)
	{
		OBB a = this;
		
		float ra, rb;
		float[][] R = new float[3][3];
		float[][] AbsR = new float[3][3];
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
			{
				R[j][i] = dot(b.rotation[i], a.rotation[j]);
				AbsR[j][i] = abs(R[j][i]) + EPSILON;
			}
		
		float[] t = subtract(a.c, b.c);
		t = new float[] {dot(t, b.rotation[0]), dot(t, b.rotation[1]), dot(t, b.rotation[2])};		
		
		for(int i = 0; i < 3; i++)
		{
			ra = b.extents[i];
			rb = a.extents[0] * AbsR[i][0] + a.extents[1] * AbsR[i][1] + a.extents[2] * AbsR[i][2];
			if(abs(t[i]) > ra + rb) return false;
		}
		
		for(int i = 0; i < 3; i++)
		{
			ra = b.extents[0] * AbsR[0][i] + b.extents[1] * AbsR[1][i] + b.extents[2] * AbsR[2][i];
			rb = a.extents[i];
			if(abs(t[0] * R[0][i] + t[1] * R[1][i] + t[2] * R[2][i]) > ra + rb) return false;
		}
		
		ra = b.extents[1] * AbsR[2][0] + b.extents[2] * AbsR[1][0];
		rb = a.extents[1] * AbsR[0][2] + a.extents[2] * AbsR[0][1];
		if(abs(t[2] * R[1][0] - t[1] * R[2][0]) > ra + rb) return false;
		
		ra = b.extents[1] * AbsR[2][1] + b.extents[2] * AbsR[1][1];
		rb = a.extents[0] * AbsR[0][2] + a.extents[2] * AbsR[0][0];
		if(abs(t[2] * R[1][1] - t[1] * R[2][1]) > ra + rb) return false;
		
		ra = b.extents[1] * AbsR[2][2] + b.extents[2] * AbsR[1][2];
		rb = a.extents[0] * AbsR[0][1] + a.extents[1] * AbsR[0][0];
		if(abs(t[2] * R[1][2] - t[1] * R[2][2]) > ra + rb) return false;
		
		ra = b.extents[0] * AbsR[2][0] + b.extents[2] * AbsR[0][0];
		rb = a.extents[1] * AbsR[1][2] + a.extents[2] * AbsR[1][1];
		if(abs(t[0] * R[2][0] - t[2] * R[0][0]) > ra + rb) return false;
		
		ra = b.extents[0] * AbsR[2][1] + b.extents[2] * AbsR[0][1];
		rb = a.extents[0] * AbsR[1][2] + a.extents[2] * AbsR[1][0];
		if(abs(t[0] * R[2][1] - t[2] * R[0][1]) > ra + rb) return false;
		
		ra = b.extents[0] * AbsR[2][2] + b.extents[2] * AbsR[0][2];
		rb = a.extents[0] * AbsR[1][1] + a.extents[1] * AbsR[1][0];
		if(abs(t[0] * R[2][2] - t[2] * R[0][2]) > ra + rb) return false;
		
		ra = b.extents[0] * AbsR[1][0] + b.extents[1] * AbsR[0][0];
		rb = a.extents[1] * AbsR[2][2] + a.extents[2] * AbsR[2][1];
		if(abs(t[1] * R[0][0] - t[0] * R[1][0]) > ra + rb) return false;
		
		ra = b.extents[0] * AbsR[1][1] + b.extents[1] * AbsR[0][1];
		rb = a.extents[0] * AbsR[2][2] + a.extents[2] * AbsR[2][0];
		if(abs(t[1] * R[0][1] - t[0] * R[1][1]) > ra + rb) return false;
		
		ra = b.extents[0] * AbsR[1][2] + b.extents[1] * AbsR[0][2];
		rb = a.extents[0] * AbsR[2][1] + a.extents[1] * AbsR[2][0];
		if(abs(t[1] * R[0][2] - t[0] * R[1][2]) > ra + rb) return false;
		
		return true;
	}
	
	@Override
	public boolean testSphere(Sphere s)
	{
		float[] p = closestPointToPoint(s.c);
		
		float[] v = subtract(p, s.c);
		
		return dot(v, v) <= s.r * s.r;
	}

	public boolean testRay(float[] p0, float[] p1)
	{
		float[] v = subtract(p0, c);
		
		p0 = new float[] {dot(v, rotation[0]), dot(v, rotation[1]), dot(v, rotation[2])};
		
		float[] min = subtract(c, extents);
		float[] max = add(c, extents);
		
		float[] d = subtract(p1, p0);
		float[] m = subtract(subtract(add(p0, p1), min), max);
		
		float adx = abs(d[0]);
		if(abs(m[0]) > extents[0] + adx) return false;
		float ady = abs(d[1]);
		if(abs(m[1]) > extents[1] + ady) return false;
		float adz = abs(d[2]);
		if(abs(m[2]) > extents[2] + adz) return false;
		
		adx += EPSILON; ady += EPSILON; adz += EPSILON;
		
		if(abs(m[1] * d[2] - m[2] * d[1]) > extents[1] * adz + extents[2] * ady) return false;
		if(abs(m[2] * d[0] - m[0] * d[2]) > extents[0] * adz + extents[2] * adx) return false;
		if(abs(m[0] * d[1] - m[1] * d[0]) > extents[0] * ady + extents[1] * adx) return false;
		
		return true;	
	}

	@Override
	public float[] closestPointToPoint(float[] p)
	{	
		float[] d = subtract(p, c);
		float[] q = c;
		
		for(int i = 0; i < 3; i++)
		{
			float dist = dot(d, rotation[i]);
			
			if(dist >  extents[i]) dist =  extents[i];
			if(dist < -extents[i]) dist = -extents[i];
			
			q = add(q, multiply(rotation[i], dist));
		}
		
		return q;
	}
	
	public float[] closestPointOnPerimeter(float[] p)
	{
		float[] d = subtract(p, c);
		
		float[] q = c;
		
		float[] normal = getFaceVector(p);
		
		int n = getFaceIndex(normal);

		for(int i = 0; i < 3; i++)
		{
			float dist = dot(d, rotation[i]);
			
			if(dist >  extents[i] || (i * 2) + 1 == n) dist =  extents[i];
			if(dist < -extents[i] || (i * 2)     == n) dist = -extents[i];
			
			q = add(q, multiply(rotation[i], dist));
		}
		
		return q;
	}
	
	public int getFaceIndex(float[] normal)
	{
		float[][] normals = getAxisVectors(1);
		
		for(int i = 0; i < 6; i++)
			if(Arrays.equals(normals[i], normal)) return i;
		
		return -1;
	}
	
	public float[][] getAxisVectors(float scale)
	{
		return new float[][]
			{
				subtract(c, multiply(rotation[0],  scale)), //0 front
					 add(c, multiply(rotation[0],  scale)), //1 back
				subtract(c, multiply(rotation[1],  scale)), //2 down
					 add(c, multiply(rotation[1],  scale)), //3 up	 
				subtract(c, multiply(rotation[2],  scale)), //4 right 
					 add(c, multiply(rotation[2],  scale)), //5 left	
			};
	}
	
	public float[] closestPointToOBB(OBB b)
	{
		float[] p = closestPointToPoint(b.getPosition());
		
		for(int i = 0; i < 10; i++)
		{
			p = b.closestPointToPoint(p);
			p = closestPointToPoint(p);
		}
		
		return p;
	}
	
	public float[] getUpVector(float scale) { return add(c, multiply(rotation[1], scale)); }
	
	public float[] getDownVector(float scale) { return subtract(c, multiply(rotation[1], scale)); }
	
	public float[] randomPointInside()
	{
		Random random = new Random();

		float x = (random.nextBoolean()) ? random.nextFloat() : -random.nextFloat();
		float y = (random.nextBoolean()) ? random.nextFloat() : -random.nextFloat();
		float z = (random.nextBoolean()) ? random.nextFloat() : -random.nextFloat();
	
		float[] _x = multiply(rotation[0], extents[0] * x);
		float[] _y = multiply(rotation[1], extents[1] * y);
		float[] _z = multiply(rotation[2], extents[2] * z);
		
		return add(add(add(c, _x), _y), _z);
	}
}
