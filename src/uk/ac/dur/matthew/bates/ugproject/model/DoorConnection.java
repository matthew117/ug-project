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
	}
	
	
}
