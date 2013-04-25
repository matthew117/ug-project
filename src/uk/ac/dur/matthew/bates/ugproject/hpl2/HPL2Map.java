package uk.ac.dur.matthew.bates.ugproject.hpl2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
