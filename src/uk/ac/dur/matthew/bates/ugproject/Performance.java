package uk.ac.dur.matthew.bates.ugproject;

public class Performance
{
	private double camClipPlanes[] = { 0.05, 1000 };
	private boolean lightsActive = true;
	private boolean psActive = true;
	private boolean showFog = true;
	private boolean showSkybox = true;
	private boolean worldReflection = true;

	public String toString()
	{
		String s = "<Performance ";
		s += "CamClipPlanes=\"" + camClipPlanes[0] + " " + camClipPlanes[1] + "\" ";
		s += "LightsActive=\"" + lightsActive + "\" ";
		s += "PSActive=\"" + psActive + "\" ";
		s += "ShowFog=\"" + showFog + "\" ";
		s += "ShowSkybox=\"" + showSkybox + "\" ";
		s += "WorldReflection=\"" + worldReflection + "\" />";
		return s;
	}
}