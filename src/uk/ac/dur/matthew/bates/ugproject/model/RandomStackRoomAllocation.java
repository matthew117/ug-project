package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;

public class RandomStackRoomAllocation implements RoomAllocationRule
{

	@Override
	public List<Room> allocateRooms(FloorPlan fp)
	{
		RoomType[] roomTypes = RoomType.values();
		Stack<RoomType> rs = new Stack<RoomType>();
		for (RoomType t : roomTypes) rs.push(t);
		rs.push(RoomType.BEDROOM);
		Collections.shuffle(rs);
		
		List<Room> rooms = new ArrayList<Room>();
		List<Rect> roomBounds = fp.roomBounds();
		for (int i = 0; i < roomBounds.size(); i++)
		{
			Rect r = roomBounds.get(i);
			rooms.add(new Room(r, i, rs.pop()));
		}
		
		return rooms;
	}

}
