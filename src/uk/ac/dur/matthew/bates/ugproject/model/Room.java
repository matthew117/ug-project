package uk.ac.dur.matthew.bates.ugproject.model;

public class Room extends Rect
{
	private int id;
	private RoomType type;

	public enum RoomType
	{
		KITCHEN,
		PANTRY,
		LAUNDRY,
		LIVING_ROOM,
		DINING_ROOM,
		TOILET,
		BEDROOM,
		MASTER_BEDROOM,
		BATHROOM,
		GUEST_ROOM,
		STUDY,
		STORAGE
	}
	
	public Room(Rect r, int id, RoomType type)
	{
		super(r.x, r.y, r.width, r.height);
		this.id = id;
		this.type = type;
	}
	
	public int id()
	{
		return id;
	}
	
	

}
