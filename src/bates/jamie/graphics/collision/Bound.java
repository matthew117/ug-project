package bates.jamie.graphics.collision;

import java.util.List;

public abstract class Bound
{
	public float[] c;
	
	public void setPosition(float x, float y, float z) { c = new float[] {x, y, z}; }
	
	public void setPosition(float[] c) { this.c = c; }
	
	public float[] getPosition() { return c; }
	
	public abstract List<float[]> getPixelMap();
	
	public abstract float[] getFaceVector(float[] p);
	
	public abstract float[] closestPointToPoint(float[] p);
	
	public abstract boolean testSphere(Sphere s);
	
	public abstract boolean testOBB(OBB b);
	
	public abstract float getMaximumExtent();
	
	public abstract float getHeight();
	
	public abstract float[] randomPointInside();
	
	public boolean testBound(Bound b)
	{
		if(b instanceof Sphere) return testSphere((Sphere) b);
		else if(b instanceof OBB) return testOBB((OBB) b);
		
		return false;	
	}
}
