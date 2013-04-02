package uk.ac.dur.matthew.bates.ugproject.model;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

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
		return p.hashCode() * (41 * q.hashCode());
	}

	/**
	 * Flip the direction of the line by switching point p and point q.
	 * 
	 * @return a new <code>Line</code> which its direction opposite to this line.
	 */
	public Line flip()
	{
		return new Line(q.x, q.y, p.x, p.y);
	}

	/**
	 * Returns the gradient of this line. A horizontal line will yield 0, whilst a vertical line
	 * will yield infinity and can be tested for using {@link Double#isInfinite(double)}.
	 * 
	 * @return the gradient of this line.
	 */
	public double gradient()
	{
		return abs(q.y - p.y) / (double)abs(q.x - p.x);
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
	
	public double length()
	{
		return sqrt((q.x - p.x)*(q.x - p.x) + (q.y - p.y)*(q.y - p.y));
	}
	
	public boolean isPositiveDirection()
	{
		return sqrt(q.x*q.x + q.y*q.y) >= sqrt(p.x*p.x + p.y*p.y);
	}
	
	@Override
	public String toString()
	{
		return p.toString() + " -> " + q.toString();
	}
	
	public Line normalizeDirection()
	{
		if (this.isPositiveDirection())
		{
			return this;
		}
		else
		{
			return this.flip();
		}
	}
	
	public static Line overlap(Line a, Line b)
	{
		Line an = a.normalizeDirection();
		Line bn = b.normalizeDirection();
		
		if (a.isHorizontal() && b.isHorizontal() && (a.p.y == b.p.y))
		{
			if (a.p.x <= b.p.x)
			{
				return new Line(b.p.x, a.p.y, a.q.x, a.p.y);
			}
			else
			{
				return new Line(a.p.x, a.p.y, b.q.x, a.p.y);
			}
		}
		else if (a.isVertical() && b.isVertical() && (a.p.x == b.p.x))
		{
			if (a.p.y <= b.p.y)
			{
				return new Line(a.p.x, b.p.y, a.p.x, a.q.y);
			}
			else
			{
				return new Line(a.p.x, a.p.y, a.p.x, b.q.y);
			}
		}
		else
		{
			return null;
		}
	}
}
