package uk.ac.dur.matthew.bates.ugproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapData
{
	// constants
	private static final int DEFAULT_GROUP = 0;

	// static fields
	private static int nextID = 0;

	// fields
	private Map<Integer, String> fileIndexStaticObjects;
	private Map<Integer, String> fileIndexEntities;
	private Map<Integer, String> fileIndexDecals;
	
	private List<StaticObject> staticObjects;
	private List<Primitive> primitives;
	private List<Decal> decals;
	private List<Entity> entities;

	private boolean fogActive = false;
	private double[] fogColor = { 1, 1, 1, 1 };
	private boolean fogCulling = true;
	private int fogEnd = 20;
	private int fogFallOffExp = 1;
	private int fogStart = 0;
	private int globalDecalMaxTris = 300;
	private String name;
	private boolean skyBoxActive = false;
	private double[] skyBoxColor = { 1, 1, 1, 1 };
	private String skyBoxTexture;

	public MapData()
	{
		// TODO Auto-generated constructor stub
		fileIndexStaticObjects = new HashMap<Integer, String>();
		fileIndexEntities = new HashMap<Integer, String>();
		fileIndexDecals = new HashMap<Integer, String>();
		
		staticObjects = new ArrayList<StaticObject>();
		primitives = new ArrayList<Primitive>();
		decals = new ArrayList<Decal>();
		entities = new ArrayList<Entity>();
	}

	public static StaticObject createStaticObject(String filePath)
	{
		return null;
	}

	public static StaticObject createStaticObject(String filePath, int group)
	{
		return null;
	}

	public String toString()
	{
		String s = "<MapData ";
		s += "FogActive=\"" + fogActive + "\" ";
		s += "FogColor=\"" + fogColor[0] + " " + fogColor[1] + " " + fogColor[2] + " " + fogColor[3] + "\" ";
		s += "FogCulling=\"" + fogCulling + "\" ";
		s += "FogEnd=\"" + fogEnd + "\" ";
		s += "FogFalloffExp=\"" + fogFallOffExp + "\" ";
		s += "FogStart=\"" + fogStart + "\" ";
		s += "GlobalDecalMaxTris=\"" + globalDecalMaxTris + "\" ";
		s += "Name=\"" + (name != null ? name : "") + "\" ";
		s += "SkyBoxActive=\"" + skyBoxActive + "\" ";
		s += "SkyBoxColor=\"" + skyBoxColor[0] + " " + skyBoxColor[1] + " " + skyBoxColor[2] + " " + skyBoxColor[3] + "\" ";
		s += "SkyBoxTexture=\"" + (skyBoxTexture != null ? skyBoxTexture : "") + "\">";
		s += "\n    <MapContents>";
		if (fileIndexStaticObjects.size() == 0)
		{
			s += "\n        <FileIndex_StaticObjects NumOfFiles=\"" + fileIndexStaticObjects.size() + "\" />";
		}
		else
		{
			s += "\n        <FileIndex_StaticObjects NumOfFiles=\"" + fileIndexStaticObjects.size() + "\">";
			for (Integer fileID : fileIndexStaticObjects.keySet())
			{
				s += "\n            <File Id=\"" + fileID + "\" Path=\"";
				s += fileIndexStaticObjects.get(fileID) + "\" />";
			}
			s += "\n        </FileIndex_StaticObjects>";
		}
		if (fileIndexEntities.size() == 0)
		{
			s += "\n        <FileIndex_Entities NumOfFiles=\"" + fileIndexEntities.size() + "\" />";
		}
		else
		{
			s += "\n        <FileIndex_Entities NumOfFiles=\"" + fileIndexEntities.size() + "\">";
			for (Integer fileID : fileIndexEntities.keySet())
			{
				s += "\n            <File Id=\"" + fileID + "\" Path=\"";
				s += fileIndexEntities.get(fileID) + "\" />";
			}
			s += "\n        </FileIndex_Entities>";
		}
		if (fileIndexDecals.size() == 0)
		{
			s += "\n        <FileIndex_Decals NumOfFiles=\"" + fileIndexDecals.size() + "\" />";
		}
		else
		{
			s += "\n        <FileIndex_Decals NumOfFiles=\"" + fileIndexDecals.size() + "\">";
			for (Integer fileID : fileIndexDecals.keySet())
			{
				s += "\n            <File Id=\"" + fileID + "\" Path=\"";
				s += fileIndexDecals.get(fileID) + "\" />";
			}
			s += "\n        </FileIndex_Decals>";
		}
		if (staticObjects.size() == 0)
		{	
			s += "\n        <StaticObjects />";	
		}
		else
		{
			s += "\n        <StaticObjects>";
			for (StaticObject sObj : staticObjects)
			{
				s += "\n            " + sObj;
			}
			s += "\n        <StaticObjects />";	
		}
		if (primitives.size() == 0)
		{	
			s += "\n        <Primitives />";	
		}
		else
		{
			s += "\n        <Primitives>";
			s += "\n        <Primitives />";	
		}
		if (decals.size() == 0)
		{	
			s += "\n        <Decals />";	
		}
		else
		{
			s += "\n        <Decals>";
			s += "\n        <Decals />";	
		}
		if (entities.size() == 0)
		{	
			s += "\n        <Entities />";	
		}
		else
		{
			s += "\n        <Entities>";
			s += "\n        <Entities />";	
		}
		s += "\n    </MapContents>";
		s += "\n</MapData>";
		return s;
	}

	public static void main(String[] args)
	{
		MapData md = new MapData();
		md.staticObjects.add(new StaticObject());
		System.out.println(md);
	}

}
