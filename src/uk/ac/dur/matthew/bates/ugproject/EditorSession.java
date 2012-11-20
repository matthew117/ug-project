package uk.ac.dur.matthew.bates.ugproject;

import java.util.ArrayList;
import java.util.List;

public class EditorSession
{
	private Performance performance;
	private ViewportConfig viewportConfig;
	private List<Group> groups;
	
	public static EditorSession defaultEditorSession()
	{
		EditorSession defaultEditorSession = new EditorSession();
		
		defaultEditorSession.groups.add(new Group());
		return defaultEditorSession;
	}

	public EditorSession()
	{
		groups = new ArrayList<Group>();
	}

	public String toString()
	{
		String s = "<EditorSession>";
		s += "\n    " + performance;
		s += "\n    " + viewportConfig;
		s += "\n    <Groups>";
		for (Group g : groups)
		{
			s += "\n        " + g;
		}
		s += "\n    </Groups>";
		s += "\n</EditorSession>";
		return s;
	}
}
