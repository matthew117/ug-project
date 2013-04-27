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
		if (overlap == null) return null;
		if (overlap.length() < 1) return null;
		if (rule.canPlaceDoor(frontFacing, backFacing))
		{
			return new DoorConnection(frontFacing, backFacing, rule.getDoorPlacement(frontFacing, backFacing));
		}
		else
		{
			return new  WallConnection(frontFacing, backFacing);
		}
	}
	
	public Wall frontFacing()
	{
		return frontFacing;
	}
	
	public Wall backFacing()
	{
		return backFacing;
	}
	
	public Line overlap()
	{
		return Line.overlap(frontFacing, backFacing);
	}
}
