package uk.ac.dur.matthew.bates.ugproject.model;

public class Line3D
{
	public static float distance3D(float px, float py, float pz, float qx, float qy, float qz)
	{
		return (float) Math.sqrt(s(qx-px) + s(qy-py) + s(qz-pz));
	}
	
	private static float s(float x)
	{
		return x * x;
	}
}
