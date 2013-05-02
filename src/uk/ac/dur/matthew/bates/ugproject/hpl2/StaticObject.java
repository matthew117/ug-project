package uk.ac.dur.matthew.bates.ugproject.hpl2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import bates.jamie.graphics.collision.OBB;
import bates.jamie.graphics.util.Matrix;

import uk.ac.dur.matthew.bates.ugproject.hpl2.util.ColladaXMLParser;
import uk.ac.dur.matthew.bates.ugproject.util.ListUtils;

public class StaticObject extends Primitive
{
	private boolean castShadows = true;
	private boolean collides = true;
	private int fileIndex;
	private String filePath;

	// cached vertex list
	protected List<float[]> vertexList;

	public StaticObject()
	{

	}

	public StaticObject(StaticObject obj)
	{
		super((Primitive) obj);
		this.castShadows = obj.castShadows;
		this.collides = obj.collides;
		this.fileIndex = obj.fileIndex;
		this.filePath = obj.filePath;
	}

	public StaticObject(String filePath)
	{
		this.filePath = filePath;
	}

	public float[] getBounds()
	{
		return new float[] { getWidth(), getHeight(), getDepth() };
	}

	public float[] getHalfBounds()
	{
		return new float[] { getWidth() / 0.2f, getHeight() / 2.0f, getDepth() / 2.0f };
	}

	public OBB getOBB()
	{
		float[] bound = getHalfBounds();
		return new OBB(getX(), getY(), getZ(), getRotation()[0], getRotation()[1], getRotation()[2], bound[0],
				bound[1], bound[2]);
	}

	public float getMidX()
	{
		return getX() + (getMinX() + getMaxX()) / 2.0f;
	}

	public float getMidY()
	{
		return getY() + (getMinY() + getMaxY()) / 2.0f;
	}

	public float getMidZ()
	{
		return getZ() + (getMinZ() + getMaxZ()) / 2.0f;
	}

	public float getX()
	{
		return getWorldPos()[0];
	}

	public float getY()
	{
		return getWorldPos()[1];
	}

	public float getZ()
	{
		return getWorldPos()[2];
	}

	public float getMaxX()
	{
		loadVertexList(filePath);
		float currentMax = 0;
		for (float[] xs : vertexList)
		{
			if (xs[0] > currentMax)
			{
				currentMax = xs[0];
			}
		}

		float[] xyz = Matrix.multiply(new float[] { currentMax, 0, 0 },
				Matrix.getRotationMatrix(getRotation()[0], getRotation()[1], getRotation()[2]));

		return currentMax;
	}

	protected void loadVertexList(String filePath)
	{
		if (vertexList == null)
		{
			vertexList = new ArrayList<float[]>();
			try
			{
				File file = new File(filePath);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(file, new ColladaXMLParser(vertexList));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public float getMinX()
	{
		loadVertexList(filePath);
		float currentMin = Float.MAX_VALUE;
		for (float[] xs : vertexList)
		{
			if (xs[0] < currentMin)
			{
				currentMin = xs[0];
			}
		}
		float[] xyz = Matrix.multiply(new float[] { currentMin, 0, 0 },
				Matrix.getRotationMatrix(getRotation()[0], getRotation()[1], getRotation()[2]));

		return currentMin;
	}

	public float getMaxY()
	{
		loadVertexList(filePath);
		float currentMax = 0;
		for (float[] xs : vertexList)
		{
			if (xs[1] > currentMax)
			{
				currentMax = xs[1];
			}
		}
		float[] xyz = Matrix.multiply(new float[] { 0, currentMax, 0 },
				Matrix.getRotationMatrix(getRotation()[0], getRotation()[1], getRotation()[2]));

		return currentMax;
	}

	public float getMinY()
	{
		loadVertexList(filePath);
		float currentMin = Float.MAX_VALUE;
		for (float[] xs : vertexList)
		{
			if (xs[1] < currentMin)
			{
				currentMin = xs[1];
			}
		}
		float[] xyz = Matrix.multiply(new float[] { 0, currentMin, 0 },
				Matrix.getRotationMatrix(getRotation()[0], getRotation()[1], getRotation()[2]));

		return currentMin;
	}

	public float getMaxZ()
	{
		loadVertexList(filePath);
		float currentMax = 0;
		for (float[] xs : vertexList)
		{
			if (xs[2] > currentMax)
			{
				currentMax = xs[2];
			}
		}
		float[] xyz = Matrix.multiply(new float[] { 0, 0, currentMax },
				Matrix.getRotationMatrix(getRotation()[0], getRotation()[1], getRotation()[2]));

		return currentMax;
	}

	public float getMinZ()
	{
		loadVertexList(filePath);
		float currentMin = Float.MAX_VALUE;
		for (float[] xs : vertexList)
		{
			if (xs[2] < currentMin)
			{
				currentMin = xs[2];
			}
		}
		float[] xyz = Matrix.multiply(new float[] { 0, 0, currentMin },
				Matrix.getRotationMatrix(getRotation()[0], getRotation()[1], getRotation()[2]));

		return currentMin;
	}

	public float[] getCentroid()
	{
		loadVertexList(filePath);

		List<Float> xs = new ArrayList<Float>();
		for (float[] v : vertexList)
		{
			xs.add(v[0]);
		}
		
		List<Float> ys = new ArrayList<Float>();
		for (float[] v : vertexList)
		{
			ys.add(v[1]);
		}
		
		List<Float> zs = new ArrayList<Float>();
		for (float[] v : vertexList)
		{
			zs.add(v[2]);
		}

		return new float[]{ListUtils.average(xs),ListUtils.average(ys),ListUtils.average(zs)};
	}

	public float getWidth()
	{
		return getMaxX() - getMinX();
	}

	public float getHeight()
	{
		return getMaxY() - getMinY();
	}

	public float getDepth()
	{
		return getMaxZ() - getMinZ();
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
		s += "Rotation=\"" + Math.toRadians(getRotation()[0]) + " " + Math.toRadians(getRotation()[1]) + " "
				+ Math.toRadians(getRotation()[2]) + "\" ";
		s += "Scale=\"" + getScale()[0] + " " + getScale()[1] + " " + getScale()[2] + "\" ";
		s += "Tag=\"" + (getTag() != null ? getTag() : "") + "\" ";
		s += "WorldPos=\"" + getWorldPos()[0] + " " + getWorldPos()[1] + " " + getWorldPos()[2] + "\" />";
		return s;
	}

}
