package uk.ac.dur.matthew.bates.ugproject.model;

public class WallConnection
{	
	// Door will open towards the back-facing wall
	private Wall frontFacing;
	private Wall backFacing;
	
	public WallConnection(Wall frontFacing, Wall backFacing)
	{
		this.frontFacing = frontFacing;
		this.backFacing = backFacing;
	}
	
	public static WallConnection create(Wall frontFacing, Wall backFacing, DoorPlacingRule rule)
	{
		Line overlap = Line.overlap(frontFacing, backFacing);
		if (overlap == null) throw new IllegalArgumentException("No connection between walls!");
		if (overlap.length() < 1) throw new IllegalArgumentException("Not enough overlap to form a connection");
		if (rule.canPlaceDoor(frontFacing, backFacing))
		{
			return new DoorConnection(frontFacing, backFacing, rule.getDoorPlacement(frontFacing, backFacing));
		}
		else
		{
			return new  WallConnection(frontFacing, backFacing);
		}
	}
}
