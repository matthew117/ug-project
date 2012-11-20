package uk.ac.dur.matthew.bates.ugproject;

import java.util.ArrayList;
import java.util.List;

public class EditorSession
{
	private Performance performance;
	private ViewportConfig viewportConfig;
	private List<Group> groups;

	// GridPlane="2" Preset="0" RenderMode="1" ShowAxes="true" ShowGrid="true" UsingLTCam="true" />
	// <Viewport CameraPosition="181.102 3.745 10.3607" CameraTarget="1.101 3.745 10.3607"
	// CameraZoom="0" GridHeight="1.101" GridPlane="0" Preset="1" RenderMode="1" ShowAxes="true"
	// ShowGrid="true" UsingLTCam="true" />
	// <Viewport CameraPosition="1.101 183.995 10.3607" CameraTarget="1.101 3.995 10.3607"
	// CameraZoom="0" GridHeight="3.995" GridPlane="1" Preset="2" RenderMode="1" ShowAxes="true"
	// ShowGrid="true" UsingLTCam="true" />
	// <Viewport CameraPosition="-1.77312 8.57924 9.37368" CameraTarget="1.101 3.995 10.3607"
	// CameraZoom="2.20727" GridHeight="3.995" GridPlane="1" Preset="3" RenderMode="0"
	// ShowAxes="true" S

	public static EditorSession defaultEditorSession()
	{
		EditorSession defaultEditorSession = new EditorSession();

		defaultEditorSession.performance = new Performance();

		ViewportConfig viewportConfig = new ViewportConfig();
		List<Viewport> viewports = viewportConfig.getViewports();
		Viewport v1 = new Viewport();
		v1.setPreset(0);
		v1.setGridPlane(2);
		viewports.add(v1);
		Viewport v2 = new Viewport();
		v2.setPreset(1);
		v2.setGridPlane(0);
		viewports.add(v2);
		Viewport v3 = new Viewport();
		v3.setPreset(2);
		v3.setGridPlane(1);
		viewports.add(v3);
		Viewport v4 = new Viewport();
		v4.setPreset(3);
		v4.setGridPlane(1);
		viewports.add(v4);
		defaultEditorSession.viewportConfig = viewportConfig;

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
