package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;

public interface RoomAllocationRule
{
	public List<RoomType> allocateRoomTypes(int frontFrontID, FloorPlan fp);
}
