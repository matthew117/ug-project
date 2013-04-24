package uk.ac.dur.matthew.bates.ugproject.hpl2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PathConfig;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PrettyPrinter;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.SimpleRoom;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.TessellatedWall;

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

	public EditorSession getEditorSession()
	{
		return editorSession;
	}

	public void setEditorSession(EditorSession editorSession)
	{
		this.editorSession = editorSession;
	}

	public MapData getMapData()
	{
		return mapData;
	}

	public void setMapData(MapData mapData)
	{
		this.mapData = mapData;
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
			// map.mapData.addStaticObject(o);
		}

		float width = Math.abs(s.getMaxX() - s.getMinX());

		Plane plane = new Plane(
				"/Applications/Amnesia.app/Contents/Resources/static_objects/mansionbase/floor/mansionbase_floor_boards.mat");
		plane.setStartCorner(new float[] { 0, 0, 0 });
		plane.setEndCorner(new float[] { 16, 0, 16 });
		plane.setWorldPos(new float[] { 0, 0, 0 });
		map.mapData.addPrimitive(plane);

		Lamp l = new Lamp(PathConfig.CANDLESTICK_WALL);
		 map.mapData.addEntity(l);

		Door d = new Door(MANSION_DOOR);
		d.setFilePath(MANSION_DOOR);
		 map.mapData.addEntity(d);

		 SimpleRoom sr = new SimpleRoom(new StaticObject(MANSION_WALL), new float[] { 0, 0, 0 },
		 5,
		 3);
		 List<StaticObject> room = sr.calculateObjects();
		 for (StaticObject so : room)
		 {
		 map.mapData.addStaticObject(so);
		 }

		for (StaticObject so : rooms)
		{
			map.mapData.addStaticObject(so);
		}

		map.writeToFile("/Applications/Amnesia.app/Contents/Resources/custom_stories/Luigi_Mansion/file.map");

		try
		{
			StringBuffer sb = new StringBuffer();
			Scanner sc = new Scanner(new File("/Applications/Amnesia.app/Contents/Resources/custom_stories/Luigi_Mansion/file.map"));
			
			while(sc.hasNextLine())
			{
				sb.append(sc.nextLine());
			}
			
			PrettyPrinter.printDocument(sb.toString());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		// ============================
		// Start LevelEditor from Java
		// ============================
		
//		try
//		{
//			System.out.println(">>> open -a /Applications/Amnesia.app/Contents/Resources/LevelEditor.app");
//			Runtime runtime = Runtime.getRuntime();
//			Process process = runtime.exec("open -a /Applications/Amnesia.app/Contents/Resources/LevelEditor.app");
//			process.waitFor();
//			Scanner sc = new Scanner(new BufferedInputStream(process.getInputStream()));
//			while (sc.hasNextLine())
//			{
//				System.out.println("<<< " + sc.nextLine());
//			}
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
		
		
	}

	static List<StaticObject> rooms = new ArrayList<StaticObject>();
}
