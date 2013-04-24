package uk.ac.dur.matthew.bates.ugproject.model;

public class Wall extends Line
{
	public static final int NORTH = 180;
	public static final int EAST = 270;
	public static final int SOUTH = 0;
	public static final int WEST = 90;
	
	private Room parent;
	private int orientation;
	private int id;
	
	private static int current_ID;
	
	public Wall(int x1, int y1, int x2, int y2, Room parent, int angle)
	{
		super(x1, y1, x2, y2);
		this.parent = parent;
		this.orientation = angle;
		this.id = ++current_ID;
	}
	
	public Wall(Point p, Point q, Room parent, int angle)
	{
		super(p, q);
		this.parent = parent;
		this.orientation = angle;
		this.id = ++current_ID;
	}
	
	public Wall(Line line, Room parent, int angle)
	{
		super(line.p, line.q);
		this.parent = parent;
		this.orientation = angle;
		this.id = ++current_ID;
	}
	
	public int id()
	{
		return id;
	}
	
	public Room parent()
	{
		return parent;
	}
	
	public int orientation()
	{
		return this.orientation;
	}

}
