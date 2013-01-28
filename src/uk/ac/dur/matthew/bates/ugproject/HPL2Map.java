package uk.ac.dur.matthew.bates.ugproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HPL2Map
{
	// file path constants
	public static final String MANSION_DOOR = "entities/door/mansion/mansion.ent";
	public static final String MANSION_WALL = "static_objects/mansionbase/wall/default.dae";

	private EditorSession editorSession;
	private MapData mapData;

	public HPL2Map()
	{
		editorSession = EditorSession.defaultEditorSession();
		mapData = new MapData();
	}

	public String toString()
	{
		String s = "<Level>";
		s += "\n" + editorSession;
		s += "\n" + mapData;
		s += "\n</Level>";
		return s;
	}

	public void writeToFile(String filePath)
	{
		try
		{
			File file = new File(filePath);
			File cache = new File(filePath + "_cache");

			if (file.exists())
			{
				file.delete();
			}

			if (cache.exists())
			{
				cache.delete();
			}

			file.createNewFile();

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(this.toString());
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		HPL2Map map = new HPL2Map();

		StaticObject s = new StaticObject("static_objects/mansionbase/wall/default.dae");
		StaticObject t = new StaticObject(s);

		TessellatedWall corridor = new TessellatedWall(s);
		corridor.setWorldPos(new float[] { 0, 0, 0 });
		corridor.setWidth(5);
		List<StaticObject> objs = corridor.calculateObjects();

		t.setRotation(new float[] { 0, 180, 0 });
		TessellatedWall corridor1 = new TessellatedWall(t);
		corridor1.setWorldPos(new float[] { 0, 0, 4 });
		corridor1.setWidth(5);
		List<StaticObject> objs1 = corridor1.calculateObjects();

		for (StaticObject o : objs)
		{
			// map.mapData.addStaticObject(o);
		}

		for (StaticObject o : objs1)
		{
			// ¯map.mapData.addStaticObject(o);
		}

		float width = Math.abs(s.getMaxX() - s.getMinX());

		Plane plane = new Plane(
				"/Applications/Amnesia.app/Contents/Resources/static_objects/mansionbase/floor/mansionbase_floor_boards.mat");
		plane.setStartCorner(new float[] { 0, 0, 0 });
		plane.setEndCorner(new float[] { 16, 0, 16 });
		plane.setWorldPos(new float[] { 0, 0, 0 });
		map.mapData.addPrimitive(plane);

		Lamp l = new Lamp();
		l.setFilePath("entities/lamp/candlestick01/candlestick01.ent");
		// map.mapData.addEntity(l);

		Door d = new Door();
		d.setFilePath(MANSION_DOOR);
		// map.mapData.addEntity(d);

		// SimpleRoom sr = new SimpleRoom(new StaticObject(MANSION_WALL), new float[] { 0, 0, 0 },
		// 5,
		// 3);
		// List<StaticObject> room = sr.calculateObjects();
		// for (StaticObject so : room)
		// {
		// map.mapData.addStaticObject(so);
		// }

		split(new float[] { 0, 0, 0 }, 4, 4);

		for (StaticObject so : rooms)
		{
			map.mapData.addStaticObject(so);
		}

		System.out.println(map);
		
		map.writeToFile("/Applications/Amnesia.app/Contents/Resources/custom_stories/Luigi_Mansion/file.map");
		
//		try
//		{
//			SAXParserFactory factory = SAXParserFactory.newInstance();
//			SAXParser saxParser = factory.newSAXParser();
//			saxParser.parse(new File("/Applications/Amnesia.app/Contents/Resources/custom_stories/Luigi_Mansion/file.map"), new PrettyPrinter());
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
	}

	static List<StaticObject> rooms = new ArrayList<StaticObject>();

	public static void split(float[] position, int width, int height)
	{
		if (width < 1) return;
		if (height < 1) return;
		if (position[0] < 0) return;
		if (position[0] > 4*width) return;
		rooms.addAll((new SimpleRoom(new StaticObject(MANSION_WALL), position, width, height))
				.calculateObjects());
		// split horizontally left
		if ((new Random()).nextBoolean())
		{
			split(new float[] { position[0], position[1], position[2] }, width / 2, height);
		}
		// split horizontally right
		if ((new Random()).nextBoolean())
		{
			split(new float[] { position[0] + width / 2, position[1], position[2] }, width / 2,
					height);
		}
		// split vertically down
		if ((new Random()).nextBoolean())
		{
			split(new float[] { position[0], position[1], position[2] + height / 2 }, width,
					height / 2);
		}
		// split vertically up
		if ((new Random()).nextBoolean())
		{
			split(new float[] { position[0], position[1], position[2] - height / 2 }, width,
					height / 2);
		}
	}
}
