package uk.ac.dur.matthew.bates.ugproject.hpl2;

public class ParticleSystem extends Primitive
{
	private boolean active = true;
	private float[] color = { 1.0f, 1.0f, 1.0f, 1.0f };
	private boolean fadeAtDistance = true;
	private String file = "/Applications/Amnesia.app/Contents/Resources/particles/ps_light_dust_large.ps";
	private float maxFadeDistanceEnd = 110.0f;
	private float maxFadeDistanceStart = 100.0f;
	private float MinFadeDistanceEnd = 1.0f;
	private float MinFadeDistanceStart = 2.0f;
	public boolean isActive()
	{
		return active;
	}
	public void setActive(boolean active)
	{
		this.active = active;
	}
	public float[] getColor()
	{
		return color;
	}
	public void setColor(float[] color)
	{
		this.color = color;
	}
	public boolean isFadeAtDistance()
	{
		return fadeAtDistance;
	}
	public void setFadeAtDistance(boolean fadeAtDistance)
	{
		this.fadeAtDistance = fadeAtDistance;
	}
	public String getFile()
	{
		return file;
	}
	public void setFile(String file)
	{
		this.file = file;
	}
	public float getMaxFadeDistanceEnd()
	{
		return maxFadeDistanceEnd;
	}
	public void setMaxFadeDistanceEnd(float maxFadeDistanceEnd)
	{
		this.maxFadeDistanceEnd = maxFadeDistanceEnd;
	}
	public float getMaxFadeDistanceStart()
	{
		return maxFadeDistanceStart;
	}
	public void setMaxFadeDistanceStart(float maxFadeDistanceStart)
	{
		this.maxFadeDistanceStart = maxFadeDistanceStart;
	}
	public float getMinFadeDistanceEnd()
	{
		return MinFadeDistanceEnd;
	}
	public void setMinFadeDistanceEnd(float minFadeDistanceEnd)
	{
		MinFadeDistanceEnd = minFadeDistanceEnd;
	}
	public float getMinFadeDistanceStart()
	{
		return MinFadeDistanceStart;
	}
	public void setMinFadeDistanceStart(float minFadeDistanceStart)
	{
		MinFadeDistanceStart = minFadeDistanceStart;
	}
	
	public String toString()
	{
		return (String.format("<ParticleSystem Active=\"%s\" " +
				"Color=\"%f %f %f %f\" " +
				"FadeAtDistance=\"%s\" " +
				"File=\"/%s\" " +
				"Group=\"%d\" ID=\"%d\" " +
				"MaxFadeDistanceEnd=\"%f\" " +
				"MaxFadeDistanceStart=\"%f\" " +
				"MinFadeDistanceEnd=\"%f\" " +
				"MinFadeDistanceStart=\"%f\" " +
				"Name=\"%s\" " +
				"Rotation=\"%f %f %f\" " +
				"Scale=\"%f %f %f\" " +
				"Tag=\"%s\" " +
				"WorldPos=\"%f %f %f\" />", active, color[0], color[1], color[2], color[3], fadeAtDistance, 
				file, getGroup(), getId(), maxFadeDistanceEnd, maxFadeDistanceStart,
				MinFadeDistanceEnd, MinFadeDistanceStart, getName(),
				Math.toRadians(getRotation()[0]), Math.toRadians(getRotation()[1]), Math.toRadians(getRotation()[2]), 
				getScale()[0], getScale()[1], getScale()[2], getTag(), getWorldPos()[0], getWorldPos()[1], getWorldPos()[2]));
	}
}
