package uk.ac.dur.matthew.bates.ugproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

		StaticObject s = new StaticObject("static_objects/mansionbase/wall/default.dae");
		Random r = new Random();
		s.setWorldPos(new double[] { 0, 0, 0 });
		s.setRotation(new double[] { 0, 0, 0 });
		map.mapData.addStaticObject(s);

		for (int i = 0; i < 6; i++)
		{
		StaticObject t = new StaticObject("static_objects/mansionbase/wall/default.dae");
		t.setWorldPos(new double[] { map.mapData.getStaticObjects().get(i).getWorldPos()[0] + map.mapData.getStaticObjects().get(i).getMaxX() + Math.abs(t.getMinX()), 0, 0 });
		t.setRotation(new double[] { 0, 0, 0 });
		map.mapData.addStaticObject(t);
		}

		System.out.println(map);
		map.writeToFile("/Applications/Amnesia.app/Contents/Resources/custom_stories/Luigi_Mansion/file.map");
	}
}
