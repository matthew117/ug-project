package uk.ac.dur.matthew.bates.ugproject.generators;

import java.util.ArrayList;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.model.FloorPlan;
import uk.ac.dur.matthew.bates.ugproject.model.Room;
import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;
import uk.ac.dur.matthew.bates.ugproject.model.RoomAllocationRule;

public class RandomStackRoomAllocation implements RoomAllocationRule
{

	@Override
	public List<RoomType> allocateRoomTypes(int frontFrontID, FloorPlan fp)
	{
		List<RoomType> ys = new ArrayList<Room.RoomType>();
		for (RoomType t : RoomType.values())
		{
			ys.add(t);
		}
		ys.add(RoomType.TOILET);
		ys.add(RoomType.BEDROOM);
		
		int n = fp.roomBounds().size();
		return new ArrayList<Room.RoomType>(ys.subList(0, n));
	}

}
