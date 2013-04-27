package uk.ac.dur.matthew.bates.ugproject.generators;

import uk.ac.dur.matthew.bates.ugproject.model.DoorPlacingRule;
import uk.ac.dur.matthew.bates.ugproject.model.Line;
import uk.ac.dur.matthew.bates.ugproject.model.Point;
import uk.ac.dur.matthew.bates.ugproject.model.Wall;



public class SimpleDoorPlacementRule implements DoorPlacingRule
{	
	@Override
	public boolean canPlaceDoor(Wall a, Wall b)
	{
		Line c = Line.overlap(a, b);
		return (c != null && c.length() >= 4);
	}

	@Override
	public Line getDoorPlacement(Wall a, Wall b)
	{
		Line c = Line.overlap(a, b);
		if (c.isHorizontal())
		{
			Point m = c.midpoint();
			return new Line(m.x - 2, m.y, m.x + 2, m.y);
		}
		else
		{
			Point m = c.midpoint();
			return new Line(m.x, m.y - 2, m.x, m.y + 2);
		}
	}

}
