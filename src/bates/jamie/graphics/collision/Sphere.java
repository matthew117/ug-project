package bates.jamie.graphics.collision;

import static bates.jamie.graphics.util.Vector.add;
import static bates.jamie.graphics.util.Vector.dot;
import static bates.jamie.graphics.util.Vector.multiply;
import static bates.jamie.graphics.util.Vector.normalize;
import static bates.jamie.graphics.util.Vector.subtract;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sphere extends Bound
{
	public static final float EPSILON = 0.0001f;
	
	public float r;
	
	public Sphere(float x, float y, float z, float r)
	{
		c = new float[] {x, y, z};
		this.r = r;
	}
	
	public Sphere(float[] c, float r)
	{
		this.c = c;
		this.r = r;
	}
	
	@Override
	public List<float[]> getPixelMap()
	{
		List<float[]> vertices = new ArrayList<float[]>();
		
		for(int e = 1; e <= 20; e++)
		{
			float radius = e * (r / 20);
			
			for(int a = 0; a <= 90; a ++)
			{	
				double theta = toRadians(a * (360.0 / 90));
				vertices.add(new float[] {(float) (c[0] + radius * cos(theta)), 0, (float) (c[2] + radius * sin(theta))});
			}
		}
		
		return vertices;
	}
	
	@Override
	public float getHeight() { return r * 2; }
	
	@Override
	public boolean testSphere(Sphere a)
	{
		float[] t = subtract(a.c, c);
		
		return dot(t, t) <= (a.r + r) * (a.r + r);
	}
	
	@Override
	public boolean testOBB(OBB b)
	{
		float[] p = b.closestPointToPoint(c);
		
		float[] v = subtract(p, c);
		
		return dot(v, v) <= r * r;
	}
	
	public static boolean testMovingSphereSphere(Sphere s0, Sphere s1, float[] v0, float[] v1)
	{
		float[] s = subtract(s1.c, s0.c);
		float[] v = subtract(v1, v0);
		float r = s1.r + s0.r;
		float c = dot(s, s) - r * r;

		if(c < 0.0f) return true;
		
		float a = dot(v, v);
		if(a < EPSILON) return false;
		float b = dot(v, s);
		if(b >= 0.0f) return false;
		float d = b * b - a * c;
		if(d < 0.0f) return false;

		return true;
	}

	@Override
	public float[] getFaceVector(float[] p) { return p; }
	
	@Override
	public float[] closestPointToPoint(float[] p)
	{
		float[]  t = subtract(p, c); //translation vector
		float[] _t = normalize(t); //unit vector
		return add(c, multiply(_t, r));	
	}
	
	@Override
	public float getMaximumExtent() { return r; }
	
	public float[] randomPointInside()
	{
		Random random = new Random();
		
		float[] p = {0, 0, 0};
		
		do
		{
			float x = (random.nextBoolean()) ? random.nextFloat() : -random.nextFloat();
			float y = (random.nextBoolean()) ? random.nextFloat() : -random.nextFloat();
			float z = (random.nextBoolean()) ? random.nextFloat() : -random.nextFloat();
	
			x *= r;
			y *= r;
			z *= r;
			
			p = new float[] {x, y, z};
		}
		while(dot(p, p) > r * r);
		
		return add(c, p);
	}
}
