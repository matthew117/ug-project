package uk.ac.dur.matthew.bates.ugproject.hpl2;

public class Group
{
	private int id = 0;
	private String name = "default";
	private boolean visible = true;

	public Group()
	{
	}
	
	public String toString()
	{
		String s = "<Group ";
		s += "ID=\"" + id + "\" ";
		s += "Name=\"" + (name != null ? name : "") + "\" ";
		s += "Visible=\"" + visible + "\" />";
		return s;
	}

}
