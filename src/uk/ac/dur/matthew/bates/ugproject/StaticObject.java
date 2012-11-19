package uk.ac.dur.matthew.bates.ugproject;

public class StaticObject
{
	private boolean castShadows;
	private boolean collides;
	private int fileIndex;
	private int group;
	private int id;
	private String name;
	private String tag;
	private double[] rotation = { 0, 0, 0 };
	private double[] scale = { 1, 1, 1 };
	private double[] worldPos = { 0, 0, 0 };

	public StaticObject()
	{
		// TODO Auto-generated constructor stub
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
