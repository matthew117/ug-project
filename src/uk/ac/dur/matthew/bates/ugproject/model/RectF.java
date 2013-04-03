package uk.ac.dur.matthew.bates.ugproject.model;

public class RectF
{
	public double x;
	public double y;
	public double width;
	public double height;

	public RectF(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public String toString()
	{
		return "(" + x + "," + y + ") width: " + width + "  height: " + height;
	}
}
