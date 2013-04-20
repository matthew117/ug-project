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
		STORAGE;
		
		public String toString()
		{
			switch (this)
			{
			case KITCHEN: return "Kitchen";
			case PANTRY: return "Pantry";
			case LAUNDRY: return "Laundry";
			case LIVING_ROOM: return "Living Room";
			case DINING_ROOM: return "Dining Room";
			case TOILET: return "Toilet";
			case BEDROOM: return "Bedroom";
			case MASTER_BEDROOM: return "Master Bedroom";
			case BATHROOM: return "Bathroom";
			case GUEST_ROOM: return "Guest Room";
			case STUDY: return "Study";
			case STORAGE: return "Storage";
			default : return "Undefined";
			}
			
		}
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
	
	public String type()
	{
		return type.toString();
	}
	
	

}
