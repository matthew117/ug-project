package uk.ac.dur.matthew.bates.ugproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class StaticObject
{
	private boolean castShadows = true;
	private boolean collides = true;
	private int fileIndex;
	private int group = 0;
	private int id;
	private String name;
	private String tag = "";
	private float[] rotation = { 0, 0, 0 };
	private float[] scale = { 1, 1, 1 };
	private float[] worldPos = { 0, 0, 0 };
	private String filePath;

	private List<float[]> vertexList;

	public StaticObject()
	{
		// TODO Auto-generated constructor stub
	}
	
	public StaticObject(StaticObject obj)
	{
		this.castShadows = obj.castShadows;
		this.collides = obj.collides;
		this.fileIndex = obj.fileIndex;
		this.group = obj.group;
		this.tag = obj.tag;
		this.rotation = obj.rotation.clone();
		this.scale = obj.scale.clone();
		this.worldPos = obj.worldPos.clone();
		this.filePath = obj.filePath;
	}

	public StaticObject(String filePath)
	{
		this.filePath = filePath;
	}

	public float getMaxX()
	{
		if (vertexList == null)
		{
			vertexList = new ArrayList<float[]>();
			try
			{
				File file = new File("/Applications/Amnesia.app/Contents/Resources/" + filePath);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(file, new ColladaXMLParser(vertexList));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		float currentMax = 0;
		for (float[] xs : vertexList)
		{
			if (xs[0] > currentMax)
			{
				currentMax = xs[0];
			}
		}
		return currentMax;
	}
	
	public float getMinX()
	{
		if (vertexList == null)
		{
			vertexList = new ArrayList<float[]>();
			try
			{
				File file = new File("/Applications/Amnesia.app/Contents/Resources/" + filePath);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(file, new ColladaXMLParser(vertexList));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		float currentMin = Float.MAX_VALUE;
		for (float[] xs : vertexList)
		{
			if (xs[0] < currentMin)
			{
				currentMin = xs[0];
			}
		}
		return currentMin;
	}

	public float getMaxY()
	{
		if (vertexList == null)
		{
			vertexList = new ArrayList<float[]>();
			try
			{
				File file = new File("/Applications/Amnesia.app/Contents/Resources/" + filePath);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(file, new ColladaXMLParser(vertexList));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		float currentMax = 0;
		for (float[] xs : vertexList)
		{
			if (xs[1] > currentMax)
			{
				currentMax = xs[1];
			}
		}
		return currentMax;
	}
	
	public float getMinY()
	{
		if (vertexList == null)
		{
			vertexList = new ArrayList<float[]>();
			try
			{
				File file = new File("/Applications/Amnesia.app/Contents/Resources/" + filePath);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(file, new ColladaXMLParser(vertexList));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		float currentMin = Float.MAX_VALUE;
		for (float[] xs : vertexList)
		{
			if (xs[1] < currentMin)
			{
				currentMin = xs[1];
			}
		}
		return currentMin;
	}
	
	public float getMaxZ()
	{
		if (vertexList == null)
		{
			vertexList = new ArrayList<float[]>();
			try
			{
				File file = new File("/Applications/Amnesia.app/Contents/Resources/" + filePath);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(file, new ColladaXMLParser(vertexList));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		float currentMax = 0;
		for (float[] xs : vertexList)
		{
			if (xs[2] > currentMax)
			{
				currentMax = xs[2];
			}
		}
		return currentMax;
	}
	
	public float getMinZ()
	{
		if (vertexList == null)
		{
			vertexList = new ArrayList<float[]>();
			try
			{
				File file = new File("/Applications/Amnesia.app/Contents/Resources/" + filePath);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(file, new ColladaXMLParser(vertexList));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		float currentMin = Float.MAX_VALUE;
		for (float[] xs : vertexList)
		{
			if (xs[2] < currentMin)
			{
				currentMin = xs[2];
			}
		}
		return currentMin;
	}

	public boolean doesCastShadows()
	{
		return castShadows;
	}

	public void setCastShadows(boolean castShadows)
	{
		this.castShadows = castShadows;
	}

	public boolean doesCollides()
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

	public float[] getRotation()
	{
		return rotation;
	}

	public void setRotation(float[] rotation)
	{
		this.rotation = rotation;
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
		s += "Rotation=\"" + Math.toRadians(rotation[0]) + " " + Math.toRadians(rotation[1]) + " " + Math.toRadians(rotation[2]) + "\" ";
		s += "Scale=\"" + scale[0] + " " + scale[1] + " " + scale[2] + "\" ";
		s += "Tag=\"" + (tag != null ? tag : "") + "\" ";
		s += "WorldPos=\"" + worldPos[0] + " " + worldPos[1] + " " + worldPos[2] + "\" />";
		return s;
	}

}
