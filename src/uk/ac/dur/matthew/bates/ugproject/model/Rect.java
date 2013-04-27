package uk.ac.dur.matthew.bates.ugproject.model;

import java.awt.geom.Rectangle2D;

/**
 * Defines a rectangle using an origin point, a width and a height
 * 
 * @author Matthew Bates
 * 
 */
public class Rect
{
	// variables are declared public to decrease the verboseness of graphics code
	/**
	 * These fields are declared a public to make code more concise. Don't store a <code>Rect</code>
	 * in a <code>HashMap</code> if you want to change mutable fields.
	 */
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

	public Line left()
	{
		return new Line(new Point(x, y), new Point(x, y + height));
	}

	public Line right()
	{
		return new Line(new Point(x + width, y), new Point(x + width, y + height));
	}

	public Line top()
	{
		return new Line(new Point(x, y), new Point(x + width, y));
	}

	public Line bottom()
	{
		return new Line(new Point(x, y + height), new Point(x + width, y + height));
	}

	public int centerX()
	{
		return x + (width >> 1);
	}

	public int centerY()
	{
		return y + (height >> 1);
	}

	public boolean contains(int x, int y)
	{
		return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
	}
	
	public boolean intersects(Rect r)
	{
		Rectangle2D a = new Rectangle2D.Double(x, y, width, height);
		Rectangle2D b = new Rectangle2D.Double(r.x, r.y, r.width, r.height);
		
		return a.intersects(b);
	}

	public Point midpoint()
	{
		return new Point(x + (width >> 1), y + (height >> 1));
	}

	public String toString()
	{
		return String.format("[x:%d, y:%d, w:%d, h:%d]", x, y, width, height);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Rect other = (Rect) obj;
		if (height != other.height) return false;
		if (width != other.width) return false;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}

}
