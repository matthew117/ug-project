package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.dur.matthew.bates.ugproject.model.Line.*;

import uk.ac.dur.matthew.bates.ugproject.generators.Squarify;

public class FloorPlan
{
	private List<Double> mAreas;
	private List<Rect> mRoomBounds;
	private List<Line> mLines;
	private List<Line> mPossibleDoorLocations;
	private List<Wall> mWalls;

	public FloorPlan(int width, int height, ArrayList<Double> areas)
	{
		this.mAreas = new ArrayList<Double>(areas);
		mRoomBounds = Squarify.squarify(areas, new RectF(0, 0, width, height));
	}

	public List<Rect> roomBounds()
	{
		return new ArrayList<Rect>(mRoomBounds);
	}

	public List<Integer> areas()
	{
		List<Integer> xs = new ArrayList<Integer>();
		for (Double d : mAreas)
		{
			xs.add(d.intValue());
		}
		return xs;
	}
	
	public List<Wall> walls()
	{
		if (mWalls != null) return mWalls;
		List<Wall> walls = new ArrayList<Wall>();
		for (Rect r : mRoomBounds)
		{
			walls.add(new Wall(r.top(), r, Wall.SOUTH));
			walls.add(new Wall(r.right(), r, Wall.WEST));
			walls.add(new Wall(r.bottom(), r, Wall.NORTH));
			walls.add(new Wall(r.left(), r, Wall.EAST));
		}
		mWalls = walls;
		return walls;
	}

	public List<Line> wallLines()
	{
		if (mLines != null) return mLines;
		List<Line> lines = new ArrayList<Line>();
		for (Rect r : mRoomBounds)
		{
			lines.add(r.top());
			lines.add(r.right());
			lines.add(r.bottom());
			lines.add(r.left());
		}
		mLines = lines;
		return lines;
	}

	public List<Line> possibleDoorLocations()
	{
		if (mPossibleDoorLocations != null) return mPossibleDoorLocations;
		List<Line> locations = new ArrayList<Line>();
		for (int i = 0; i < wallLines().size(); i++)
		{
			for (int j = i + 1; j < wallLines().size(); j++)
			{
				Line a = wallLines().get(i);
				Line b = wallLines().get(j);
				Line o = overlap(a, b);
				if (o != null && o.length() >= 4) locations.add(o);
			}
		}
		mPossibleDoorLocations = locations;
		return locations;
	}
	
	public List<Point> doorLocations()
	{
		List<Point> doorLocations = new ArrayList<Point>();
		for (Line l : possibleDoorLocations())
		{
			doorLocations.add(l.midpoint());
		}
		return doorLocations;
	}

	public int width()
	{
		int maxX = 0;
		int minX = Integer.MAX_VALUE;
		for (Rect r : mRoomBounds)
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
		for (Rect r : mRoomBounds)
		{
			int rBottom = r.y + r.height;
			if (rBottom > maxY) maxY = rBottom;
			if (r.y < minY) minY = r.y;
		}
		return maxY - minY;
	}
}