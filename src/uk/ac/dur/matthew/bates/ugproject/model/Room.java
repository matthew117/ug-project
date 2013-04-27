package uk.ac.dur.matthew.bates.ugproject.model;

public class Room extends Rect
{
	private int id;
	private RoomType type;

	public enum RoomType
	{
		FOYER, KITCHEN, LAUNDRY, LIVING_ROOM, DINING_ROOM, TOILET, BEDROOM, MASTER_BEDROOM, BATHROOM, GUEST_ROOM, STUDY, STORAGE, CORRIDOR;

		public String toString()
		{
			switch (this)
			{
			case FOYER:
				return "Foyer";
			case KITCHEN:
				return "Kitchen";
			case LAUNDRY:
				return "Laundry";
			case LIVING_ROOM:
				return "Living Room";
			case DINING_ROOM:
				return "Dining Room";
			case TOILET:
				return "Toilet";
			case BEDROOM:
				return "Bedroom";
			case MASTER_BEDROOM:
				return "Master Bedroom";
			case BATHROOM:
				return "Bathroom";
			case GUEST_ROOM:
				return "Guest Room";
			case STUDY:
				return "Study";
			case STORAGE:
				return "Storage";
			case CORRIDOR:
				return "Corridor";
			default:
				return "Undefined";
			}
		}

		public boolean isPublic()
		{
			boolean PUBLIC = true;
			boolean PRIVATE = false;
			
			switch (this)
			{
			case FOYER:
				return PUBLIC;
			case KITCHEN:
				return PUBLIC;
			case LAUNDRY:
				return PRIVATE;
			case LIVING_ROOM:
				return PUBLIC;
			case DINING_ROOM:
				return PUBLIC;
			case TOILET:
				return PUBLIC;
			case BEDROOM:
				return PRIVATE;
			case MASTER_BEDROOM:
				return PRIVATE;
			case BATHROOM:
				return PRIVATE;
			case GUEST_ROOM:
				return PRIVATE;
			case STUDY:
				return PRIVATE;
			case STORAGE:
				return PRIVATE;
			case CORRIDOR:
				return PUBLIC;
			default:
				return PUBLIC;
			}
		}
		
		public boolean isPrivate()
		{
			return !isPublic();
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

	public RoomType type()
	{
		return type;
	}
	
	public void type(RoomType t)
	{
		this.type = t;
	}
	
	@Override
	public String toString()
	{
		return type.toString();
	}

}
