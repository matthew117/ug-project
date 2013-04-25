package uk.ac.dur.matthew.bates.ugproject.hpl2;

public class ObjectFactory
{
	private String file;
	private float xPosition;
	private float yPosition;
	private float zPosition;
	private float xRotation;
	private float yRotation;
	private float zRotation;

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
	
	public ObjectFactory rotation(float xr, float yr, float zr)
	{
		this.xRotation = xr;
		this.yRotation = yr;
		this.zRotation = zr;
		return this;
	}
	
	public StaticObject build()
	{
		if (file.endsWith(".ent"))
		{
			Entity e = new Entity(file);
			e.setRotation(new float[] { xRotation, yRotation, zRotation });
			e.setWorldPos(new float[] { xPosition, yPosition, zPosition });
			return e;
		}
		if (file.endsWith(".dae"))
		{
			StaticObject e = new StaticObject(file);
			e.setRotation(new float[] { xRotation, yRotation, zRotation });
			e.setWorldPos(new float[] { xPosition, yPosition, zPosition });
			return e;
		}
		return null;
	}
}
