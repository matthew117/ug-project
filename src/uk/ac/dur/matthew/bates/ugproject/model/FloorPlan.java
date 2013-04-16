package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.ArrayList;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.generators.Squarify;

public class FloorPlan
{
	private List<Double> areas;
	private List<Rect> roomBounds;
	
	public FloorPlan(int width, int height, ArrayList<Double> areas)
	{
		this.areas = new ArrayList<Double>(areas);
		roomBounds = Squarify.squarify(areas, new RectF(0, 0, width, height));
	}

	public List<Rect> roomBounds()
	{
		return new ArrayList<Rect>(roomBounds);
	}
	
	public List<Integer> areas()
	{
		List<Integer> xs = new ArrayList<Integer>();
		for (Double d : areas)
		{
			xs.add(d.intValue());
		}
		return xs;
	}
	
	public int width()
	{
		int maxX = 0;
		int minX = Integer.MAX_VALUE;
		for (Rect r : roomBounds)
		{
			int rRight = r.x + r.width;
			if (rRight > maxX) maxX = rRight;
			if (r.x < minX) minX = r.x;
		}
		return maxX - minX;
	}
	
	public int height()
	{
		int maxY = 0;
		int minY = Integer.MAX_VALUE;
		for (Rect r : roomBounds)
		{
			int rBottom = r.y + r.height;
			if (rBottom > maxY) maxY = rBottom;
			if (r.y < minY) minY = r.y;
		}
		return maxY - minY;
	}
}