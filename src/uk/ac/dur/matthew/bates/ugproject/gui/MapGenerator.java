package uk.ac.dur.matthew.bates.ugproject.gui;

import uk.ac.dur.matthew.bates.ugproject.hpl2.HPL2Map;
import uk.ac.dur.matthew.bates.ugproject.hpl2.Plane;
import uk.ac.dur.matthew.bates.ugproject.hpl2.StaticObject;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PathConfig;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PrettyPrinter;
import uk.ac.dur.matthew.bates.ugproject.model.FloorPlan;
import uk.ac.dur.matthew.bates.ugproject.model.Room;
import uk.ac.dur.matthew.bates.ugproject.model.Wall;
import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;

public class MapGenerator
{
	private FloorPlan fp;
	private HPL2Map map;

	public MapGenerator(FloorPlan floorPlan)
	{
		this.fp = floorPlan;
		this.map = new HPL2Map();
		generateFloors();
		generateWalls();
	}

	public void generateFloors()
	{
		for (Room r : fp.rooms())
		{
			Plane plane = new Plane(getFloorMaterial(r.type()));
			plane.setStartCorner(new float[] { 0, 0, 0 });
			plane.setEndCorner(new float[] { r.width, 0, r.height });
			plane.setWorldPos(new float[] { r.x, 0, r.y });
			map.getMapData().addPrimitive(plane);
		}
	}

	public void generateWalls()
	{
		for (Wall t : fp.tessellation())
		{
			StaticObject o = new StaticObject(t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED);
			o.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y });
			o.setRotation(new float[] { 0, t.orientation(), 0 });
			map.getMapData().addStaticObject(o);
		}
	}

	private String getFloorMaterial(RoomType type)
	{
		// @formatter:off
		switch (type)
		{
		case BATHROOM: return PathConfig.TILED_FLOOR_MATERIAL;
		case BEDROOM: return PathConfig.MANSION_FLOOR_MATERIAL;
		case DINING_ROOM: return PathConfig.MANSION_FLOORBOARDS_MATERIAL;
		case FOYER: return PathConfig.MANSION_FLOORBOARDS_MATERIAL;
		case GUEST_ROOM: return PathConfig.MANSION_FLOORBOARDS_MATERIAL;
		case KITCHEN: return PathConfig.TILED_FLOOR_MATERIAL;
		case LAUNDRY: return PathConfig.MANSION_FLOORBOARDS_MATERIAL;
		case LIVING_ROOM: return PathConfig.MANSION_FLOORBOARDS_MATERIAL;
		case MASTER_BEDROOM: return PathConfig.MANSION_FLOOR_MATERIAL;
		case PANTRY: return PathConfig.MANSION_FLOORBOARDS_MATERIAL;
		case STORAGE: return PathConfig.MANSION_FLOORBOARDS_MATERIAL;
		case STUDY: return PathConfig.MANSION_FLOOR_MATERIAL;
		case TOILET: return PathConfig.TILED_FLOOR_MATERIAL;
		default: return PathConfig.MANSION_FLOORBOARDS_MATERIAL;
		}
		// @formatter:off
	}

	public void writeToFile(String customStoryFolder)
	{
		map.writeToFile(customStoryFolder);
		try
		{
			PrettyPrinter.printDocument(map.toString());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
