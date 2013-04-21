package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.List;

public class ConnectionNode extends Node
{
	private DoorConnection conn;

	public ConnectionNode(int x, int y, List<Node> edges, DoorConnection conn)
	{
		super(x, y, edges);
		this.conn = conn;
	}
	
	public ConnectionNode(Point p, List<Node> edges, DoorConnection conn)
	{
		super(p, edges);
		this.conn = conn;
	}
	
	public DoorConnection connection()
	{
		return conn;
	}

}
