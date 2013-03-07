package uk.ac.dur.matthew.bates.ugproject.hpl2.util;

import java.util.ArrayList;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.hpl2.StaticObject;

public class TessellatedWall
{
	private StaticObject tileObject;
	private float[] worldPos = { 0, 0, 0 };
	private float width;
	private float height;

	public TessellatedWall(StaticObject tileObject)
	{
		this.tileObject = tileObject;
	}

	public List<StaticObject> calculateObjects()
	{
		List<StaticObject> list = new ArrayList<StaticObject>();
		float tileWidth = Math.abs((tileObject.getMinX() - tileObject.getMaxX()));
		for (int i = 0; i < width; i++)
		{
			StaticObject obj = new StaticObject(tileObject);
			obj.setWorldPos(calculatedAngularEndPoint(worldPos, tileObject.getRotation()[1],
					(tileWidth * i) + (tileWidth/2)));
			list.add(obj);
		}
		return list;
	}

	private float[] calculatedAngularEndPoint(float[] origin, float angle, float distance)
	{
		return new float[] { origin[0] + (float) (distance * Math.cos(Math.toRadians(angle))),
				origin[1], origin[2] + (float) (distance * Math.sin(Math.toRadians(angle))) };
	}

	public StaticObject getTileObject()
	{
		return tileObject;
	}

	public void setTileObject(StaticObject tileObject)
	{
		this.tileObject = tileObject;
	}

	public float[] getWorldPos()
	{
		return worldPos;
	}

	public void setWorldPos(float[] worldPos)
	{
		this.worldPos = worldPos;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

}
