package uk.ac.dur.matthew.bates.ugproject.hpl2;

public class Primitive
{
	private int group = 0;
	private int id;
	private String name;
	private String tag = "";
	private float[] rotation = { 0, 0, 0 };
	private float[] scale = { 1, 1, 1 };
	private float[] worldPos = { 0, 0, 0 };

	public Primitive()
	{
		
	}
	
	public Primitive(Primitive obj)
	{
		this.group = obj.group;
		this.tag = obj.tag;
		this.rotation = obj.rotation.clone();
		this.scale = obj.scale.clone();
		this.worldPos = obj.worldPos.clone();
	}

	public int getGroup()
	{
		return group;
	}

	public void setGroup(int group)
	{
		this.group = group;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public float[] getRotation()
	{
		return rotation;
	}

	public void setRotation(float[] rotation)
	{
		this.rotation = rotation;
	}
	
	public void setRotation(float x, float y, float z)
	{
		setRotation(new float[]{x,y,z});
	}

	public float[] getScale()
	{
		return scale;
	}

	public void setScale(float[] scale)
	{
		this.scale = scale;
	}

	public float[] getWorldPos()
	{
		return worldPos;
	}

	public void setWorldPos(float[] worldPos)
	{
		this.worldPos = worldPos;
	}
	
	public void setWorldPos(float x, float y, float z)
	{
		setWorldPos(new float[]{x,y,z});
	}

}