package uk.ac.dur.matthew.bates.ugproject.hpl2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import uk.ac.dur.matthew.bates.ugproject.hpl2.util.ColladaXMLParser;

public class StaticObject extends Primitive
{
	private boolean castShadows = true;
	private boolean collides = true;
	private int fileIndex;
	private String filePath;

	// cached vertex list
	private List<float[]> vertexList;

	public StaticObject()
	{
		
	}

	public StaticObject(StaticObject obj)
	{
		super((Primitive)obj);
		this.castShadows = obj.castShadows;
		this.collides = obj.collides;
		this.fileIndex = obj.fileIndex;
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
		s += "Group=\"" + getGroup() + "\" ";
		s += "ID=\"" + getId() + "\" ";
		s += "Name=\"" + (getName() != null ? getName() : "") + "\" ";
		s += "Rotation=\"" + Math.toRadians(getRotation()[0]) + " " + Math.toRadians(getRotation()[1]) + " " + Math
				.toRadians(getRotation()[2]) + "\" ";
		s += "Scale=\"" + getScale()[0] + " " + getScale()[1] + " " + getScale()[2] + "\" ";
		s += "Tag=\"" + (getTag() != null ? getTag() : "") + "\" ";
		s += "WorldPos=\"" + getWorldPos()[0] + " " + getWorldPos()[1] + " " + getWorldPos()[2] + "\" />";
		return s;
	}

}
