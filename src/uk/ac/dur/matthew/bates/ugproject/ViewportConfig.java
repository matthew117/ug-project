package uk.ac.dur.matthew.bates.ugproject;

import java.util.ArrayList;
import java.util.List;

public class ViewportConfig
{
	private double[] bgColor = { 0.2, 0.2, 0.2, 1 };
	private boolean gAmbientLight = false;
	private boolean gPointLight = false;
	private boolean gridSnap = true;
	private double gridSnapSeparation = 0.25;
	private int selectedViewport = 0;
	private boolean usingEnlargedViewport = true;
	
	private List<Viewport> viewports;
	
	public ViewportConfig()
	{
		viewports = new ArrayList<Viewport>();
		for (int i = 0; i < 4; i++) viewports.add(new Viewport());
	}
	
	public String toString()
	{
		String s = "<ViewportConfig ";
		s += "BGColor=\"" + bgColor[0] + " " + bgColor[1] + " " + bgColor[2] + " " +  bgColor[3] + "\" ";
		s += "GAmbientLight=\"" + gAmbientLight + "\" ";
		s += "GPointLight=\"" + gPointLight + "\" ";
		s += "GridSnap=\"" + gridSnap + "\" ";
		s += "GridSnapSeparation=\"" + gridSnapSeparation + "\" ";
		s += "SelectedViewport=\"" + selectedViewport + "\" ";
		s += "UsingEnlargedViewport=\"" + usingEnlargedViewport + "\">";
		for (Viewport vp : viewports)
		{
			s += "\n        " + vp;
		}
		s += "\n    </ViewportConfig>";
		return s;
	}
}