package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.List;

public class Node extends Point
{
	private List<Node> edges;

	public Node(int x, int y, List<Node> edges)
	{
		super(x, y);
		this.edges = edges;
	}
	
	public Node(Point p, List<Node> edges)
	{
		super(p.x, p.y);
		this.edges = edges;
	}
	
	public List<Node> edges()
	{
		return edges;
	}

}
