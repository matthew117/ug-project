package uk.ac.dur.matthew.bates.ugproject.hpl2;

public class Area extends Primitive
{
	public static final String PATH_NODE = "PathNode";
	public static final String PLAYER_START = "PlayerStart";

	private boolean active = true;
	private String areaType = "";
	private String mesh = "";

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getAreaType()
	{
		return areaType;
	}

	public void setAreaType(String areaType)
	{
		this.areaType = areaType;
	}

	public String getMesh()
	{
		return mesh;
	}

	public void setMesh(String mesh)
	{
		this.mesh = mesh;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("<Area Active=\"%s\" AreaType=\"%s\" Group=\"%d\" ID=\"%d\" Mesh=\"%s\" "
				+ "Name=\"%s\" Rotation=\"%f %f %f\" Scale=\"%f %f %f\" Tag=\"%s\" WorldPos=\"%f %f %f\">\n",
				active ? "true" : "false", areaType, getGroup(), getId(), mesh, getName() != null ? getName() : "",
				Math.toRadians(getRotation()[0]), Math.toRadians(getRotation()[1]), Math.toRadians(getRotation()[2]),
				getScale()[0], getScale()[1], getScale()[2], getTag(), getWorldPos()[0], getWorldPos()[1],
				getWorldPos()[2]));
		sb.append("   <UserVariables />\n");
		sb.append("</Area>");
		return sb.toString();
	}

}
