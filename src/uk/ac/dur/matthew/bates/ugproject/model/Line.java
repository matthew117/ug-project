package uk.ac.dur.matthew.bates.ugproject.model;

import static java.lang.Math.abs;

public class Line
{
	/**
	 * These fields are declared a public to make code more concise. Don't store a <code>Line</code>
	 * in a <code>HashMap</code> if you want to change mutable fields.
	 */
	public Point p;
	public Point q;

	public Line(Point p, Point q)
	{
		this.p = p;
		this.q = q;
	}

	public Line(int x1, int y1, int x2, int y2)
	{
		this.p = new Point(x1, y1);
		this.q = new Point(x2, y2);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null) return false;
		if (o instanceof Line)
		{
			Line l = (Line) o;
			return this.p.equals(l.p) && this.q.equals(l.q);
		}
		else
		{
			return false;
		}
	}

	@Override
	public int hashCode()
	{
		return p.hashCode() * q.hashCode();
	}

	/**
	 * Returns the gradient of this line. A horizontal line will yield 0, whilst a vertical line
	 * will yield infinity and can be tested for using {@link Double#isInfinite(double)}.
	 * 
	 * @return the gradient of this line.
	 */
	public double gradient()
	{
		return abs(q.y - p.y) / abs(q.x - p.x);
	}

	/**
	 * Determines whether this line is a straight horizontal line. Note that this method is
	 * preferable to testing using the {@link #gradient()} function.
	 * 
	 * @return <code>true</code> if this line is horizontal, <code>false</code> otherwise.
	 */
	public boolean isHorizontal()
	{
		return p.y == q.y;
	}

	/**
	 * Determines whether this line is a straight vertical line. Note that this method is preferable
	 * to testing using the {@link #gradient()} function.
	 * 
	 * @return <code>true</code> if this line is vertical, <code>false</code> otherwise.
	 */
	public boolean isVertical()
	{
		return p.x == q.x;
	}
}
