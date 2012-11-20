package uk.ac.dur.matthew.bates.ugproject;

public class Viewport
{
	private double[] cameraPosition = { 1, 4, 190 };
	private double[] cameraTarget = { 1, 4, 10 };
	private double cameraZoom = 0;
	private double gridHeight = 10;
	private int gridPlane = 0;
	private int preset = 0;
	private int renderMode = 1;
	private boolean showAxes = true;
	private boolean showGrid = true;
	private boolean usingLTCam = true;

	public Viewport()
	{

	}

	public double[] getCameraPosition()
	{
		return cameraPosition;
	}

	public void setCameraPosition(double[] cameraPosition)
	{
		this.cameraPosition = cameraPosition;
	}

	public double[] getCameraTarget()
	{
		return cameraTarget;
	}

	public void setCameraTarget(double[] cameraTarget)
	{
		this.cameraTarget = cameraTarget;
	}

	public double getCameraZoom()
	{
		return cameraZoom;
	}

	public void setCameraZoom(double cameraZoom)
	{
		this.cameraZoom = cameraZoom;
	}

	public double getGridHeight()
	{
		return gridHeight;
	}

	public void setGridHeight(double gridHeight)
	{
		this.gridHeight = gridHeight;
	}

	public int getGridPlane()
	{
		return gridPlane;
	}

	public void setGridPlane(int gridPlane)
	{
		this.gridPlane = gridPlane;
	}

	public int getPreset()
	{
		return preset;
	}

	public void setPreset(int preset)
	{
		this.preset = preset;
	}

	public int getRenderMode()
	{
		return renderMode;
	}

	public void setRenderMode(int renderMode)
	{
		this.renderMode = renderMode;
	}

	public boolean isShowAxes()
	{
		return showAxes;
	}

	public void setShowAxes(boolean showAxes)
	{
		this.showAxes = showAxes;
	}

	public boolean isShowGrid()
	{
		return showGrid;
	}

	public void setShowGrid(boolean showGrid)
	{
		this.showGrid = showGrid;
	}

	public boolean isUsingLTCam()
	{
		return usingLTCam;
	}

	public void setUsingLTCam(boolean usingLTCam)
	{
		this.usingLTCam = usingLTCam;
	}

	public String toString()
	{
		String s = "<Viewport ";
		s += "CameraPosition=\"" + cameraPosition[0] + " " + cameraPosition[1] + " " + cameraPosition[2] + "\" ";
		s += "CameraTarget=\"" + cameraTarget[0] + " " + cameraTarget[1] + " " + cameraTarget[2] + "\" ";
		s += "CameraZoom=\"" + cameraZoom + "\" ";
		s += "GridHeight=\"" + gridHeight + "\" ";
		s += "GridPlane=\"" + gridPlane + "\" ";
		s += "Preset=\"" + preset + "\" ";
		s += "RenderMode=\"" + renderMode + "\" ";
		s += "ShowAxes=\"" + showAxes + "\"";
		s += "ShowGrid=\"" + showGrid + "\" ";
		s += "UsingLTCam=\"" + usingLTCam + "\" />";
		return s;
	}
}