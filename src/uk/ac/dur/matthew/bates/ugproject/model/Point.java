package uk.ac.dur.matthew.bates.ugproject.model;

public class Point
{
	/**
	 * These fields are declared a public to make code more concise. Don't store a
	 * <code>Point</code> in a <code>HashMap</code> if you want to change mutable fields.
	 */
	public int x;
	public int y;

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null) return false;
		if (o instanceof Point)
		{
			Point p = (Point) o;
			return (this.x == p.x && this.y == p.y);
		}
		else
		{
			return false;
		}
	}

	@Override
	public int hashCode()
	{
		return (41 * (41 + x) + y);
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
	
	private static float cross(Point u, Point v)
	{
		return u.y * v.x - u.x * v.y;
	}

}
