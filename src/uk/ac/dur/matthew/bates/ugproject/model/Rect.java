package uk.ac.dur.matthew.bates.ugproject.model;

/**
 * Defines a rectangle using an origin point, a width and a height
 * 
 * @author Matthew Bates
 * 
 */
public class Rect
{
	// variables are declared public to decrease the verboseness of graphics code
	public int x;
	public int y;
	public int width;
	public int height;

	public Rect(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int centerX()
	{
		return (x + width) >> 1;
	}

	public int centerY()
	{
		return (y + height) >> 1;
	}

	public boolean contains(int x, int y)
	{
		// TODO unimplemented method
		return false;
	}
	
	public void inset(int dx, int dy)
	{
		// TODO unimplemented method
	}
	
	public void offset(int dx, int dy)
	{
		// TODO unimplemented method
	}

	public String toString()
	{
		return String.format("[x:%d, y:%d, w:%d, h:%d]", x, y, width, height);
	}

}
