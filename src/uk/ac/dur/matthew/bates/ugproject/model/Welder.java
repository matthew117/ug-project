package uk.ac.dur.matthew.bates.ugproject.model;

public class Welder
{
	public static final int TOP_LEFT = 90;
	public static final int TOP_RIGHT = 180;
	public static final int BOTTOM_LEFT = 0;
	public static final int BOTTOM_RIGHT = 270;
	
	private Room parent;
	private Point location;
	private int orientation;
	
	public Welder(Room parent, Point location, int orientation)
	{
		this.location = location;
		this.parent = parent;
		this.orientation = orientation;
	}
	
	public Room parent()
	{
		return parent;
	}
	
	public Point location()
	{
		return location;
	}
	
	public int orientation()
	{
		return orientation;
	}	
}
