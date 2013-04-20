package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.List;

public interface RoomAllocationRule
{
	public List<Room> allocateRooms(FloorPlan fp);
}
