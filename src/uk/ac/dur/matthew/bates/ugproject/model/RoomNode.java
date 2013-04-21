package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.List;

public class RoomNode extends Node
{
	private Room r;

	public RoomNode(int x, int y, List<Node> edges, Room r)
	{
		super(x, y, edges);
		this.r = r;
	}
	
	public RoomNode(Point p, List<Node> edges, Room r)
	{
		super(p.x, p.y, edges);
		this.r = r;
	}
	
	public Room room()
	{
		return r;
	}

}
