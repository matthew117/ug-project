package uk.ac.dur.matthew.bates.ugproject.model;

public interface DoorPlacingRule
{
	public boolean canPlaceDoor(Wall a, Wall b);
	public Line getDoorPlacement(Wall a, Wall b);
}
