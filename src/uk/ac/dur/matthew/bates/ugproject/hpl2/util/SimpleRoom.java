package uk.ac.dur.matthew.bates.ugproject.hpl2.util;

import java.util.ArrayList;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.hpl2.StaticObject;

public class SimpleRoom
{
	public StaticObject wallObj;
	public float[] worldPos = { 0, 0, 0 };
	public int width;
	public int length;
	
	public SimpleRoom(StaticObject obj, float[] worldPos, int width, int length)
	{
		this.wallObj = obj;
		this.worldPos = worldPos;
		this.width = width;
		this.length = length;
	}
	
	public List<StaticObject> calculateObjects()
	{
		List<StaticObject> list = new ArrayList<StaticObject>();
		float tileWidth = Math.abs((wallObj.getMinX() - wallObj.getMaxX()));
		
		TessellatedWall north = new TessellatedWall(wallObj);
		north.setWidth(width);
		north.setWorldPos(worldPos.clone());
		list.addAll(north.calculateObjects());
		
		wallObj.setRotation(new float[] { 0, 180, 0 });
		TessellatedWall south = new TessellatedWall(wallObj);
		south.setWorldPos(new float[] { worldPos[0] + tileWidth*width, worldPos[1], worldPos[2] + tileWidth*length });
		south.setWidth(width);
		list.addAll(south.calculateObjects());
		
		wallObj.setRotation(new float[] { 0, 90, 0 });
		TessellatedWall west = new TessellatedWall(wallObj);
		west.setWorldPos(new float[] { worldPos[0], worldPos[1], worldPos[2] });
		west.setWidth(length);
		list.addAll(west.calculateObjects());
		
		wallObj.setRotation(new float[] { 0, 270, 0 });
		TessellatedWall east = new TessellatedWall(wallObj);
		east.setWorldPos(new float[] { worldPos[0] + tileWidth*width, worldPos[1], worldPos[2] + tileWidth*length });
		east.setWidth(length);
		list.addAll(east.calculateObjects());
		
		return list;
	}

}
