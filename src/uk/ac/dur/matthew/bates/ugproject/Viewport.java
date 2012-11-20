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