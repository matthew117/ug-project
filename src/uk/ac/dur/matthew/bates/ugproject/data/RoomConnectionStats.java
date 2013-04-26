package uk.ac.dur.matthew.bates.ugproject.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.model.Room;
import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;

public class RoomConnectionStats
{
	private static DataFrame df = DataFrame.loadFromFile(new File("room_connections.tsv"), true, true);
	
	public static boolean canConnectTo(RoomType a, RoomType b)
	{
		return df.get(a.toString(), b.toString()).equals("x");
	}
	
	public static List<Room.RoomType> connectsTo(RoomType t)
	{
		List<Room.RoomType> xs = new ArrayList<Room.RoomType>();
		for (RoomType i : RoomType.values())
		{
			if (canConnectTo(t, i)) xs.add(i);
		}
		return xs;
	}
	
	public static List<Room.RoomType> connectsTo(RoomType t, Collection<RoomType> s)
	{
		List<Room.RoomType> xs = new ArrayList<Room.RoomType>();
		for (RoomType i : s)
		{
			if (canConnectTo(t, i)) xs.add(i);
		}
		return xs;
	}
}
