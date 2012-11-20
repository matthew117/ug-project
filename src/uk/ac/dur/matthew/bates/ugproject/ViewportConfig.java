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
	private boolean usingEnlargedViewport = false;

	private List<Viewport> viewports;

	public ViewportConfig()
	{
		viewports = new ArrayList<Viewport>();
	}

	public double[] getBgColor()
	{
		return bgColor;
	}

	public void setBgColor(double[] bgColor)
	{
		this.bgColor = bgColor;
	}

	public boolean isgAmbientLight()
	{
		return gAmbientLight;
	}

	public void setgAmbientLight(boolean gAmbientLight)
	{
		this.gAmbientLight = gAmbientLight;
	}

	public boolean isgPointLight()
	{
		return gPointLight;
	}

	public void setgPointLight(boolean gPointLight)
	{
		this.gPointLight = gPointLight;
	}

	public boolean isGridSnap()
	{
		return gridSnap;
	}

	public void setGridSnap(boolean gridSnap)
	{
		this.gridSnap = gridSnap;
	}

	public double getGridSnapSeparation()
	{
		return gridSnapSeparation;
	}

	public void setGridSnapSeparation(double gridSnapSeparation)
	{
		this.gridSnapSeparation = gridSnapSeparation;
	}

	public int getSelectedViewport()
	{
		return selectedViewport;
	}

	public void setSelectedViewport(int selectedViewport)
	{
		this.selectedViewport = selectedViewport;
	}

	public boolean isUsingEnlargedViewport()
	{
		return usingEnlargedViewport;
	}

	public void setUsingEnlargedViewport(boolean usingEnlargedViewport)
	{
		this.usingEnlargedViewport = usingEnlargedViewport;
	}

	public List<Viewport> getViewports()
	{
		return viewports;
	}

	public void setViewports(List<Viewport> viewports)
	{
		this.viewports = viewports;
	}

	public String toString()
	{
		String s = "<ViewportConfig ";
		s += "BGColor=\"" + bgColor[0] + " " + bgColor[1] + " " + bgColor[2] + " " + bgColor[3] + "\" ";
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