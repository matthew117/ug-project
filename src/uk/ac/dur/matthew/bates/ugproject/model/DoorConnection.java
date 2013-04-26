package uk.ac.dur.matthew.bates.ugproject.model;

public class DoorConnection extends WallConnection
{
	private Line doorPlacement;
	private Line leftWallSegment;
	private Line rightWallSegment;
	
	public DoorConnection(Wall frontFacing, Wall backFacing, Line doorPlacement)
	{
		super(frontFacing, backFacing);
		this.doorPlacement = doorPlacement;
		Line o = Line.overlap(frontFacing, backFacing);
		leftWallSegment = new Line(o.p, doorPlacement.p);
		rightWallSegment = new Line(doorPlacement.q, o.q);
	}
	
	public Line doorPlacement()
	{
		return doorPlacement;
	}
	
	public Line leftWallSegment()
	{
		if (leftWallSegment != null) return leftWallSegment;
		return leftWallSegment;
	}
	
	public Line rightWallSegment()
	{
		if (rightWallSegment != null) return rightWallSegment;
		return rightWallSegment;
	}
	
}
