package uk.ac.dur.matthew.bates.ugproject;

public class StaticObject
{
	private boolean castShadows = true;
	private boolean collides = true;
	private int fileIndex;
	private int group = 0;
	private int id;
	private String name;
	private String tag = "";
	private double[] rotation = { 0, 0, 0 };
	private double[] scale = { 1, 1, 1 };
	private double[] worldPos = { 0, 0, 0 };
	private String filePath;

	public StaticObject()
	{
		// TODO Auto-generated constructor stub
	}
	
	public StaticObject(String filePath)
	{
		this.filePath = filePath;
	}

	public double getMaxX()
	{
		return 0;
	}

	public double getMaxY()
	{
		return 0;
	}

	public double getMaxZ()
	{
		return 0;
	}

	public boolean isCastShadows()
	{
		return castShadows;
	}

	public void setCastShadows(boolean castShadows)
	{
		this.castShadows = castShadows;
	}

	public boolean isCollides()
	{
		return collides;
	}

	public void setCollides(boolean collides)
	{
		this.collides = collides;
	}

	public int getFileIndex()
	{
		return fileIndex;
	}

	public void setFileIndex(int fileIndex)
	{
		this.fileIndex = fileIndex;
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

	public double[] getRotation()
	{
		return rotation;
	}

	public void setRotation(double[] rotation)
	{
		this.rotation = rotation;
	}

	public double[] getScale()
	{
		return scale;
	}

	public void setScale(double[] scale)
	{
		this.scale = scale;
	}

	public double[] getWorldPos()
	{
		return worldPos;
	}

	public void setWorldPos(double[] worldPos)
	{
		this.worldPos = worldPos;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String toString()
	{
		String s = "<StaticObject ";
		s += "CastShadows=\"" + castShadows + "\" ";
		s += "Collides=\"" + collides + "\" ";
		s += "FileIndex=\"" + fileIndex + "\" ";
		s += "Group=\"" + group + "\" ";
		s += "ID=\"" + id + "\" ";
		s += "Name=\"" + (name != null ? name : "") + "\" ";
		s += "Rotation=\"" + rotation[0] + " " + rotation[1] + " " + rotation[2] + "\" ";
		s += "Scale=\"" + scale[0] + " " + scale[1] + " " + scale[2] + "\" ";
		s += "Tag=\"" + (tag != null ? tag : "") + "\" ";
		s += "WorldPos=\"" + worldPos[0] + " " + worldPos[1] + " " + worldPos[2] + "\" />";
		return s;
	}

}
