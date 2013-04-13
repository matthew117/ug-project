package uk.ac.dur.matthew.bates.ugproject.model;

import static uk.ac.dur.matthew.bates.ugproject.model.Line.overlap;

import java.util.ArrayList;
import java.util.List;

public class FeatureDetection
{
	/**
	 * Returns true if a is connected to b's top face
	 */
	public static boolean isDirectlyAbove(Rect a, Rect b)
	{
		if (isOnTopLeftCorner(a, b)) return false;
		if (isOnTopRightCorner(a, b)) return false;
		return overlap(a.bottom(), b.top()) != null;
	}
	
	/**
	 * Returns true if a is connected to b's bottom face
	 */
	public static boolean isDirectlyBelow(Rect a, Rect b)
	{
		if (isOnBottomLeftCorner(a, b)) return false;
		if (isOnBottomRightCorner(a, b)) return false;
		return overlap(a.top(), b.bottom()) != null;
	}
	
	/**
	 * Returns true if a is connected to b's left face
	 */
	public static boolean isDirectlyLeftOf(Rect a, Rect b)
	{
		if (isOnTopLeftCorner(a, b)) return false;
		if (isOnBottomLeftCorner(a, b)) return false;
		return overlap(a.right(), b.left()) != null;
	}
	
	/**
	 * Returns true is a is connected to b's right face
	 */
	public static boolean isDirectlyRightOf(Rect a, Rect b)
	{
		if (isOnTopRightCorner(a, b)) return false;
		if (isOnBottomRightCorner(a, b)) return false;
		return overlap(a.left(), b.right()) != null;
	}
	
	/**
	 * Returns true is a is connected to b's top-left corner
	 */
	public static boolean isOnTopLeftCorner(Rect a, Rect b)
	{
		Point ap = new Point(a.x + a.width, a.y + a.height);
		Point bp = new Point(b.x, b.y);
		return ap.equals(bp);
	}
	
	/**
	 * Returns true is a is connected to b's top-right corner
	 */
	public static boolean isOnTopRightCorner(Rect a, Rect b)
	{
		Point ap = new Point(a.x, a.y + a.height);
		Point bp = new Point(b.x + b.width, b.y);
		return ap.equals(bp);
	}
	
	/**
	 * Returns true is a is connected to b's bottom-left corner
	 */
	public static boolean isOnBottomLeftCorner(Rect a, Rect b)
	{
		Point ap = new Point(a.x + a.width, a.y);
		Point bp = new Point(b.x, b.y + b.height);
		return ap.equals(bp);
	}
	
	/**
	 * Returns true is a is connected to b's bottom-right corner
	 */
	public static boolean isOnBottomRightCorner(Rect a, Rect b)
	{
		Point ap = new Point(a.x, a.y);
		Point bp = new Point(b.x + b.width, b.y + b.height);
		return ap.equals(bp);
	}
	
	/**
	 * Returns a list of bottom-most rectangles
	 */
	public static List<Rect> listBottomMostRectangles(List<Rect> rects)
	{
		int lowestPoint = 0;
		for (Rect r : rects)
		{
			int baseline = r.y + r.height;
			if (baseline > lowestPoint) lowestPoint = baseline;
		}
		List<Rect> returnList = new ArrayList<Rect>();
		for (Rect r : rects)
		{
			int baseline = r.y + r.height;
			if (baseline == lowestPoint) returnList.add(r);
		}
		return returnList;
	}
	
}
