package uk.ac.dur.matthew.bates.ugproject;

public class Plane extends StaticObject
{
	// <Plane Active="true" AlignToWorldCoords="false" CastShadows="false" Collides="true"
	// Corner1UV="0 20" Corner2UV="0 0" Corner3UV="22.081 0" Corner4UV="22.081 20"
	// EndCorner="22.081 0 20" Group="0" ID="104"
	// Material="C:/Program Files/Amnesia - The Dark Descent/redist/static_objects/mansionbase/floor/mansionbase_floor_boards.mat"
	// Name="Plane" Rotation="0 0 0" Scale="1 1 1" StartCorner="0 0 0" Tag="" TextureAngle="0"
	// TileAmount="1 1 1" TileOffset="0 0 0" WorldPos="-12.1546 0 -2.80547" />

	private boolean active = true;
	private boolean alignToWorldCoords = false;
	private float[] corner1UV;
	private float[] corner2UV;
	private float[] corner3UV;
	private float[] corner4UV;
	private float[] startCorner;
	private float[] endCorner;
	private String material;
	private float textureAngle;
	private int[] tileAmount = { 1, 1, 1 };
	private float[] tileOffset = { 0, 0, 0 };
}
