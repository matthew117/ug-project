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
		for (int i = 0; i < 50; i++)
		{
			StaticObject s = new StaticObject("static_objects/castlebase/wall/default_square_brick.dae");
			Random r = new Random();
			s.setWorldPos(new double[] { r.nextInt(10), r.nextInt(10), r.nextInt(10) });
			s.setRotation(new double[] { 0, 180, 0 });
			map.mapData.addStaticObject(s);
		}
		for (int i = 0; i < 50; i++)
		{
			StaticObject s = new StaticObject("static_objects/mansionbase/wall/door_way.dae");
			Random r = new Random();
			s.setWorldPos(new double[] { r.nextInt(10), r.nextInt(10), r.nextInt(10) });
			s.setRotation(new double[] { 0, 180, 0 });
			map.mapData.addStaticObject(s);
		}
		System.out.println(map);
		map.writeToFile("/Applications/Amnesia.app/Contents/Resources/custom_stories/Luigi_Mansion/file.map");
	}
}
