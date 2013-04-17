package uk.ac.dur.matthew.bates.ugproject.model;

public class Wall extends Line
{
	public static final int NORTH = 0;
	public static final int EAST = 90;
	public static final int SOUTH = 180;
	public static final int WEST = 270;
	
	private Rect room;
	private int orientation;
	
	public Wall(int x1, int y1, int x2, int y2, Rect parent, int angle)
	{
		super(x1, y1, x2, y2);
		this.room = parent;
		this.orientation = angle;
	}
	
	public Wall(Point p, Point q, Rect parent, int angle)
	{
		super(p, q);
		this.room = parent;
		this.orientation = angle;
	}
	
	public Wall(Line line, Rect parent, int angle)
	{
		super(line.p, line.q);
		this.room = parent;
		this.orientation = angle;
	}
	
	public int angle()
	{
		return this.orientation;
	}

}
