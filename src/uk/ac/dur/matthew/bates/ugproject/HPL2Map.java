package uk.ac.dur.matthew.bates.ugproject;

public class HPL2Map
{
	private EditorSession editorSession;
	private MapData mapData;
	
	public HPL2Map()
	{
		editorSession = new EditorSession();
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
	
	public static void main(String[] args)
	{
		HPL2Map map = new HPL2Map();
		System.out.println(map);
	}
}
