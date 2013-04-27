package uk.ac.dur.matthew.bates.ugproject.model;

public class Tessellation extends Wall
{
	public enum Type
	{
		WALL, WINDOW, ALCOVE, STOVE;
	}

	private Type type = Type.WALL;

	public Tessellation(int x1, int y1, int x2, int y2, Room parent, int angle)
	{
		super(x1, y1, x2, y2, parent, angle);
	}

	public Tessellation(int x1, int y1, int x2, int y2, Room parent, int angle, Tessellation.Type type)
	{
		super(x1, y1, x2, y2, parent, angle);
		this.type = type;
	}

	public Tessellation(Line line, Room r, int angle)
	{
		super(line.p.x, line.p.y, line.q.x, line.q.y, r, angle);
	}
	
	

	@Override
	public int hashCode()
	{
		return super.hashCode() * type.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		Line a = (Line)this;
		Line b = (Line)o;
		return a.equals(b) && this.type == ((Tessellation)o).type;
	}

	public Type type()
	{
		return type;
	}

}
