package uk.ac.dur.matthew.bates.ugproject.hpl2;

public class Light extends Primitive
{
	private boolean active = true;
	private int blendFunc = 0;
	private boolean castShadows = false;
	private float[] diffuseColor = new float[] { 1.0f, 1.0f, 1.0f, 0 };
	private String falloffMap = "";
	private boolean flickerActive = false;
	private boolean flickerFade = false;
	private float[] flickerOffColor = new float[] { 0, 0, 0, 1 };
	private float flickerOffFadeMaxLength = 0;
	private float flickerOffFadeMinLength = 0;
	private float flickerOffMaxLength = 0;
	private float flickerOffMinLength = 0;
	private String flickerOffPS = "";
	private float flickerOffRadius = 0;
	private String flickerOffSound = "";
	private float flickerOnFadeMaxLength = 0;
	private float flickerOnFadeMinLength = 0;
	private float flickerOnMaxLength = 0;
	private float flickerOnMinLength = 0;
	private String flickerOnPS = "";
	private String flickerOnSound = "";
	private String gobo = "";
	private float goboAnimFrameTime = 0;
	private String goboAnimMode = "None";
	private float radius = 1;
	private String shadowResolution = "High";
	private boolean shadowsAffectDynamic = true;
	private boolean shadowsAffectStatic = true;
	private float[] size = new float[] { 1, 1, 1 };

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public int getBlendFunc()
	{
		return blendFunc;
	}

	public void setBlendFunc(int blendFunc)
	{
		this.blendFunc = blendFunc;
	}

	public boolean isCastShadows()
	{
		return castShadows;
	}

	public void setCastShadows(boolean castShadows)
	{
		this.castShadows = castShadows;
	}

	public float[] getDiffuseColor()
	{
		return diffuseColor;
	}

	public void setDiffuseColor(float[] diffuseColor)
	{
		this.diffuseColor = diffuseColor;
	}

	public String getFalloffMap()
	{
		return falloffMap;
	}

	public void setFalloffMap(String falloffMap)
	{
		this.falloffMap = falloffMap;
	}

	public boolean isFlickerActive()
	{
		return flickerActive;
	}

	public void setFlickerActive(boolean flickerActive)
	{
		this.flickerActive = flickerActive;
	}

	public boolean isFlickerFade()
	{
		return flickerFade;
	}

	public void setFlickerFade(boolean flickerFade)
	{
		this.flickerFade = flickerFade;
	}

	public float[] getFlickerOffColor()
	{
		return flickerOffColor;
	}

	public void setFlickerOffColor(float[] flickerOffColor)
	{
		this.flickerOffColor = flickerOffColor;
	}

	public float getFlickerOffFadeMaxLength()
	{
		return flickerOffFadeMaxLength;
	}

	public void setFlickerOffFadeMaxLength(float flickerOffFadeMaxLength)
	{
		this.flickerOffFadeMaxLength = flickerOffFadeMaxLength;
	}

	public float getFlickerOffFadeMinLength()
	{
		return flickerOffFadeMinLength;
	}

	public void setFlickerOffFadeMinLength(float flickerOffFadeMinLength)
	{
		this.flickerOffFadeMinLength = flickerOffFadeMinLength;
	}

	public float getFlickerOffMaxLength()
	{
		return flickerOffMaxLength;
	}

	public void setFlickerOffMaxLength(float flickerOffMaxLength)
	{
		this.flickerOffMaxLength = flickerOffMaxLength;
	}

	public float getFlickerOffMinLength()
	{
		return flickerOffMinLength;
	}

	public void setFlickerOffMinLength(float flickerOffMinLength)
	{
		this.flickerOffMinLength = flickerOffMinLength;
	}

	public String getFlickerOffPS()
	{
		return flickerOffPS;
	}

	public void setFlickerOffPS(String flickerOffPS)
	{
		this.flickerOffPS = flickerOffPS;
	}

	public float getFlickerOffRadius()
	{
		return flickerOffRadius;
	}

	public void setFlickerOffRadius(float flickerOffRadius)
	{
		this.flickerOffRadius = flickerOffRadius;
	}

	public String getFlickerOffSound()
	{
		return flickerOffSound;
	}

	public void setFlickerOffSound(String flickerOffSound)
	{
		this.flickerOffSound = flickerOffSound;
	}

	public float getFlickerOnFadeMaxLength()
	{
		return flickerOnFadeMaxLength;
	}

	public void setFlickerOnFadeMaxLength(float flickerOnFadeMaxLength)
	{
		this.flickerOnFadeMaxLength = flickerOnFadeMaxLength;
	}

	public float getFlickerOnFadeMinLength()
	{
		return flickerOnFadeMinLength;
	}

	public void setFlickerOnFadeMinLength(float flickerOnFadeMinLength)
	{
		this.flickerOnFadeMinLength = flickerOnFadeMinLength;
	}

	public float getFlickerOnMaxLength()
	{
		return flickerOnMaxLength;
	}

	public void setFlickerOnMaxLength(float flickerOnMaxLength)
	{
		this.flickerOnMaxLength = flickerOnMaxLength;
	}

	public float getFlickerOnMinLength()
	{
		return flickerOnMinLength;
	}

	public void setFlickerOnMinLength(float flickerOnMinLength)
	{
		this.flickerOnMinLength = flickerOnMinLength;
	}

	public String getFlickerOnPS()
	{
		return flickerOnPS;
	}

	public void setFlickerOnPS(String flickerOnPS)
	{
		this.flickerOnPS = flickerOnPS;
	}

	public String getFlickerOnSound()
	{
		return flickerOnSound;
	}

	public void setFlickerOnSound(String flickerOnSound)
	{
		this.flickerOnSound = flickerOnSound;
	}

	public String getGobo()
	{
		return gobo;
	}

	public void setGobo(String gobo)
	{
		this.gobo = gobo;
	}

	public float getGoboAnimFrameTime()
	{
		return goboAnimFrameTime;
	}

	public void setGoboAnimFrameTime(float goboAnimFrameTime)
	{
		this.goboAnimFrameTime = goboAnimFrameTime;
	}

	public String getGoboAnimMode()
	{
		return goboAnimMode;
	}

	public void setGoboAnimMode(String goboAnimMode)
	{
		this.goboAnimMode = goboAnimMode;
	}

	public float getRadius()
	{
		return radius;
	}

	public void setRadius(float radius)
	{
		this.radius = radius;
	}

	public String getShadowResolution()
	{
		return shadowResolution;
	}

	public void setShadowResolution(String shadowResolution)
	{
		this.shadowResolution = shadowResolution;
	}

	public boolean isShadowsAffectDynamic()
	{
		return shadowsAffectDynamic;
	}

	public void setShadowsAffectDynamic(boolean shadowsAffectDynamic)
	{
		this.shadowsAffectDynamic = shadowsAffectDynamic;
	}

	public boolean isShadowsAffectStatic()
	{
		return shadowsAffectStatic;
	}

	public void setShadowsAffectStatic(boolean shadowsAffectStatic)
	{
		this.shadowsAffectStatic = shadowsAffectStatic;
	}

	public float[] getSize()
	{
		return size;
	}

	public void setSize(float[] size)
	{
		this.size = size;
	}

	public String toString()
	{
		return String
				.format("<BoxLight Active=\"%s\" BlendFunc=\"%d\" CastShadows=\"%s\" DiffuseColor=\"%f %f %f %f\" FalloffMap=\"%s\" FlickerActive=\"%s\" FlickerFade=\"%s\" FlickerOffColor=\"%f %f %f %f\" FlickerOffFadeMaxLength=\"%f\" FlickerOffFadeMinLength=\"%f\" FlickerOffMaxLength=\"%f\" FlickerOffMinLength=\"%f\" FlickerOffPS=\"%s\" FlickerOffRadius=\"%f\" FlickerOffSound=\"%s\" FlickerOnFadeMaxLength=\"%f\" FlickerOnFadeMinLength=\"%f\" FlickerOnMaxLength=\"%f\" FlickerOnMinLength=\"%f\" FlickerOnPS=\"%s\" FlickerOnSound=\"%s\" Gobo=\"%s\" GoboAnimFrameTime=\"%f\" GoboAnimMode=\"%s\" Group=\"%d\" ID=\"%d\" Name=\"%s\" Radius=\"%f\" Rotation=\"%f %f %f\" Scale=\"%f %f %f\" ShadowResolution=\"%s\" ShadowsAffectDynamic=\"%s\" ShadowsAffectStatic=\"%s\" Size=\"%f %f %f\" Tag=\"%s\" WorldPos=\"%f %f %f\" />",
						active, blendFunc, castShadows, diffuseColor[0], diffuseColor[1], diffuseColor[2],
						diffuseColor[3], falloffMap, flickerActive, flickerFade, flickerOffColor[0],
						flickerOffColor[1], flickerOffColor[2], flickerOffColor[3], flickerOffFadeMaxLength,
						flickerOffFadeMinLength, flickerOffMaxLength, flickerOffFadeMinLength, flickerOffPS,
						flickerOffRadius, flickerOffSound, flickerOnFadeMaxLength, flickerOnFadeMinLength,
						flickerOnMaxLength, flickerOnMinLength, flickerOnPS, flickerOnSound, gobo, goboAnimFrameTime,
						goboAnimMode, getGroup(), getId(), getName(), radius, getRotation()[0], getRotation()[1],
						getRotation()[2], getScale()[0], getScale()[1], getScale()[2], shadowResolution,
						shadowsAffectDynamic, shadowsAffectStatic, size[0], size[1], size[2], getTag(),
						getWorldPos()[0], getWorldPos()[1], getWorldPos()[2]);
	}

}
