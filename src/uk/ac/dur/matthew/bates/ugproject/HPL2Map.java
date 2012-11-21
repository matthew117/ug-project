package uk.ac.dur.matthew.bates.ugproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HPL2Map
{
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
			map.mapData.addStaticObject(o);
		}

		for (StaticObject o : objs1)
		{
			map.mapData.addStaticObject(o);
		}

		System.out.println(map);
		map.writeToFile("/Applications/Amnesia.app/Contents/Resources/custom_stories/Luigi_Mansion/file.map");
	}
}
