package uk.ac.dur.matthew.bates.ugproject.hpl2;

import bates.jamie.graphics.util.Matrix;

public class ObjectFactory
{
	private String file;
	private float xPosition;
	private float yPosition;
	private float zPosition;
	private float xRotation;
	private float yRotation;
	private float zRotation;
	private float xScale = 1.0f;
	private float yScale = 1.0f;
	private float zScale = 1.0f;

	public ObjectFactory(String file)
	{
		this.file = file;
	}

	public ObjectFactory file(String file)
	{
		this.file = file;
		return this;
	}

	public ObjectFactory position(float x, float y, float z)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		return this;
	}
	
	public ObjectFactory rotate(float x, float y, float z, float rx, float ry, float rz)
	{
		float[] xyz = Matrix.multiply(new float[]{x,y,z}, Matrix.getRotationMatrix(rx, ry, rz));
		
		this.xPosition = xPosition + xyz[0];
		this.yPosition = yPosition + xyz[1];
		this.zPosition = zPosition + xyz[2];
		
		this.xRotation = xRotation + rx;
		this.yRotation = yRotation + ry;
		this.zRotation = zRotation + rz;
		
		return this;
	}
	
	public ObjectFactory offset(int x, int y, int z)
	{
		this.xPosition = xPosition + x;
		this.yPosition = yPosition + y;
		this.zPosition = zPosition + z;
		
		return this;
	}

	public ObjectFactory rotation(float xr, float yr, float zr)
	{
		this.xRotation = xr;
		this.yRotation = yr;
		this.zRotation = zr;
		return this;
	}
	
	public ObjectFactory addRotation(float xr, float yr, float zr)
	{
		this.xRotation = xRotation + xr;
		this.yRotation = yRotation + yr;
		this.zRotation = zRotation + zr;
		return this;
	}

	public ObjectFactory scale(float xs, float ys, float zs)
	{
		this.xScale = xs;
		this.yScale = ys;
		this.zScale = zs;
		return this;
	}

	public StaticObject build()
	{
		if (file.endsWith(".ent"))
		{
			Entity e = new Entity(file);
			e.setRotation(new float[] { xRotation, yRotation, zRotation });
			e.setWorldPos(new float[] { xPosition, yPosition, zPosition });
			e.setScale(new float[] { xScale, yScale, zScale });
			return e;
		}
		if (file.endsWith(".dae"))
		{
			StaticObject e = new StaticObject(file);
			e.setRotation(new float[] { xRotation, yRotation, zRotation });
			e.setWorldPos(new float[] { xPosition, yPosition, zPosition });
			e.setScale(new float[] { xScale, yScale, zScale });
			return e;
		}
		return null;
	}
}
