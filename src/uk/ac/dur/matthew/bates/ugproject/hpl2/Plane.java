package uk.ac.dur.matthew.bates.ugproject.hpl2;


public class Plane extends Primitive
{
	private boolean active = true;
	private boolean castShadows = true;
	private boolean collides = true;
	private boolean alignToWorldCoords = false;
	private float[] corner1UV = new float[2];
	private float[] corner2UV = new float[2];
	private float[] corner3UV = new float[2];
	private float[] corner4UV = new float[2];
	private float[] startCorner = new float[3];
	private float[] endCorner = new float[3];
	private String material;
	private float textureAngle = 0;
	private int[] tileAmount = { 1, 1, 1 };
	private float[] tileOffset = { 0, 0, 0 };

	public Plane(String filePathToMaterial)
	{
		material = filePathToMaterial;
	}

	public boolean isActive()
	{
		return active;
	}

	public boolean doesCastShadows()
	{
		return castShadows;
	}

	public void setCastShadows(boolean castShadows)
	{
		this.castShadows = castShadows;
	}

	public boolean doesCollides()
	{
		return collides;
	}

	public void setCollides(boolean collides)
	{
		this.collides = collides;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public boolean isAlignToWorldCoords()
	{
		return alignToWorldCoords;
	}

	public void setAlignToWorldCoords(boolean alignToWorldCoords)
	{
		this.alignToWorldCoords = alignToWorldCoords;
	}

	public float[] getCorner1UV()
	{
		return corner1UV;
	}

	public void setCorner1UV(float[] corner1uv)
	{
		corner1UV = corner1uv;
	}

	public float[] getCorner2UV()
	{
		return corner2UV;
	}

	public void setCorner2UV(float[] corner2uv)
	{
		corner2UV = corner2uv;
	}

	public float[] getCorner3UV()
	{
		return corner3UV;
	}

	public void setCorner3UV(float[] corner3uv)
	{
		corner3UV = corner3uv;
	}

	public float[] getCorner4UV()
	{
		return corner4UV;
	}

	public void setCorner4UV(float[] corner4uv)
	{
		corner4UV = corner4uv;
	}

	public float[] getStartCorner()
	{
		return startCorner;
	}

	public void setStartCorner(float[] startCorner)
	{
		this.startCorner = startCorner;
		this.corner1UV[0] = startCorner[0];
		this.corner1UV[1] = endCorner[2];
		this.corner2UV[0] = startCorner[0];
		this.corner2UV[1] = startCorner[2];
		this.corner3UV[0] = endCorner[0];
		this.corner3UV[1] = startCorner[2];
		this.corner4UV[0] = endCorner[0];
		this.corner4UV[1] = endCorner[2];
	}

	public float[] getEndCorner()
	{
		return endCorner;
	}

	public void setEndCorner(float[] endCorner)
	{
		this.endCorner = endCorner;
		this.corner1UV[0] = startCorner[0];
		this.corner1UV[1] = endCorner[2];
		this.corner2UV[0] = startCorner[0];
		this.corner2UV[1] = startCorner[2];
		this.corner3UV[0] = endCorner[0];
		this.corner3UV[1] = startCorner[2];
		this.corner4UV[0] = endCorner[0];
		this.corner4UV[1] = endCorner[2];
	}

	public String getMaterial()
	{
		return material;
	}

	public void setMaterial(String material)
	{
		this.material = material;
	}

	public float getTextureAngle()
	{
		return textureAngle;
	}

	public void setTextureAngle(float textureAngle)
	{
		this.textureAngle = textureAngle;
	}

	public int[] getTileAmount()
	{
		return tileAmount;
	}

	public void setTileAmount(int[] tileAmount)
	{
		this.tileAmount = tileAmount;
	}

	public float[] getTileOffset()
	{
		return tileOffset;
	}

	public void setTileOffset(float[] tileOffset)
	{
		this.tileOffset = tileOffset;
	}

	public String toString()
	{
		String s = "<Plane ";
		s += "Active=\"" + active + "\" ";
		s += "AlignToWorldCoords=\"" + alignToWorldCoords + "\" ";
		s += "CastShadows=\"" + castShadows + "\" ";
		s += "Collides=\"" + collides + "\" ";
		s += "Corner1UV=\"" + corner1UV[0] + " " + corner1UV[1] + "\" ";
		s += "Corner2UV=\"" + corner2UV[0] + " " + corner2UV[1] + "\" ";
		s += "Corner3UV=\"" + corner3UV[0] + " " + corner3UV[1] + "\" ";
		s += "Corner4UV=\"" + corner4UV[0] + " " + corner4UV[1] + "\" ";
		s += "EndCorner=\"" + endCorner[0] + " " + endCorner[1] + " " + endCorner[2] + "\" ";
		s += "Group=\"" + getGroup() + "\" ";
		s += "ID=\"" + getId() + "\" ";
		s += "Material=\"" + (material != null ? material : "") + "\" ";
		s += "Name=\"" + (getName() != null ? getName() : "") + "\" ";
		s += "Rotation=\"" + Math.toRadians(getRotation()[0]) + " " + Math
				.toRadians(getRotation()[1]) + " " + Math.toRadians(getRotation()[2]) + "\" ";
		s += "Scale=\"" + getScale()[0] + " " + getScale()[1] + " " + getScale()[2] + "\" ";
		s += "StartCorner=\"" + startCorner[0] + " " + startCorner[1] + " " + startCorner[2] + "\" ";
		s += "Tag=\"" + (getTag() != null ? getTag() : "") + "\" ";
		s += "TextureAngle=\"" + textureAngle + "\" ";
		s += "TileAmount=\"" + tileAmount[0] + " " + tileAmount[1] + " " + tileAmount[2] + "\" ";
		s += "TileOffset=\"" + tileOffset[0] + " " + tileOffset[1] + " " + tileOffset[2] + "\" ";
		s += "WorldPos=\"" + getWorldPos()[0] + " " + getWorldPos()[1] + " " + getWorldPos()[2] + "\" />";
		return s;
	}

}
