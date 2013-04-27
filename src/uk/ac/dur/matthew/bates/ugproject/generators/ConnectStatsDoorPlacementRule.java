package uk.ac.dur.matthew.bates.ugproject.generators;

import uk.ac.dur.matthew.bates.ugproject.data.RoomConnectionStats;
import uk.ac.dur.matthew.bates.ugproject.model.DoorPlacingRule;
import uk.ac.dur.matthew.bates.ugproject.model.Line;
import uk.ac.dur.matthew.bates.ugproject.model.Wall;

public class ConnectStatsDoorPlacementRule implements DoorPlacingRule
{
	@Override
	public boolean canPlaceDoor(Wall a, Wall b)
	{
		Line c = Line.overlap(a, b);
		if (c != null && c.length() >= 4)
		{
			return RoomConnectionStats.canConnectTo(a.parent().type(), b.parent().type());
		}
		else return false;
	}

	@Override
	public Line getDoorPlacement(Wall a, Wall b)
	{
		Line c = Line.overlap(a, b);
		int x = c.midpoint().x;
		int y = c.midpoint().y;

		if (c.isHorizontal())
		{
			if (x % 2 != 0) x++;
		}
		else
		{
			if (y % 2 != 0) y++;
		}

		if (c.isHorizontal())
		{
			return new Line(x - 2, y, x + 2, y);
		}
		else
		{
			return new Line(x, y - 2, x, y + 2);
		}
	}

}
