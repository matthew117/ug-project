package uk.ac.dur.matthew.bates.ugproject.hpl2;

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

		defaultEditorSession.performance = new Performance();

		ViewportConfig viewportConfig = new ViewportConfig();
		List<Viewport> viewports = viewportConfig.getViewports();

		viewportConfig.setSelectedViewport(3);

		Viewport v1 = new Viewport();
		v1.setPreset(0);
		v1.setGridPlane(2);
		v1.setRenderMode(0);
		v1.setCameraPosition(new double[] { 0, 0, 180 });

		viewports.add(v1);

		Viewport v2 = new Viewport();
		v2.setPreset(1);
		v2.setGridPlane(0);
		v2.setRenderMode(0);
		v2.setCameraPosition(new double[] { 180, 0, 0 });
		viewports.add(v2);

		Viewport v3 = new Viewport();
		v3.setPreset(2);
		v3.setGridPlane(1);
		v3.setRenderMode(0);
		v3.setCameraPosition(new double[] { 0, 180, 0 });
		viewports.add(v3);

		Viewport v4 = new Viewport();
		v4.setPreset(3);
		v4.setGridPlane(1);
		v4.setRenderMode(0);
		v4.setCameraPosition(new double[] { 5, 2, 5 });
		v4.setCameraTarget(new double[] { 0, 0, 10 });
		viewports.add(v4);

		defaultEditorSession.viewportConfig = viewportConfig;

		defaultEditorSession.groups.add(new Group());
		return defaultEditorSession;
	}

	public static EditorSession lookatEditorSession(double[] cameraPos, double[] lookat)
	{
		// could implement a view fustrum based method that takes a bounding box for the scene
		// and calculates the editor view from that
		EditorSession defaultEditorSession = new EditorSession();

		defaultEditorSession.performance = new Performance();

		ViewportConfig viewportConfig = new ViewportConfig();
		List<Viewport> viewports = viewportConfig.getViewports();

		viewportConfig.setSelectedViewport(3);
		viewportConfig.setUsingEnlargedViewport(true);

		Viewport v1 = new Viewport();
		v1.setPreset(0);
		v1.setGridPlane(2);
		v1.setRenderMode(0);
		v1.setCameraPosition(new double[] { 0, 0, 180 });

		viewports.add(v1);

		Viewport v2 = new Viewport();
		v2.setPreset(1);
		v2.setGridPlane(0);
		v2.setRenderMode(0);
		v2.setCameraPosition(new double[] { 180, 0, 0 });
		viewports.add(v2);

		Viewport v3 = new Viewport();
		v3.setPreset(2);
		v3.setGridPlane(1);
		v3.setRenderMode(0);
		v3.setCameraPosition(new double[] { 0, 180, 0 });
		viewports.add(v3);

		Viewport v4 = new Viewport();
		v4.setPreset(3);
		v4.setGridPlane(1);
		v4.setRenderMode(0);
		v4.setCameraPosition(cameraPos);
		v4.setCameraTarget(lookat);
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
		s += "\n" + performance;
		s += "\n" + viewportConfig;
		s += "\n<Groups>";
		for (Group g : groups)
		{
			s += "\n" + g;
		}
		s += "\n</Groups>";
		s += "\n</EditorSession>";
		return s;
	}
}
