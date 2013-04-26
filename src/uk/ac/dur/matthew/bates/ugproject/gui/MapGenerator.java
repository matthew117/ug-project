package uk.ac.dur.matthew.bates.ugproject.gui;

import uk.ac.dur.matthew.bates.ugproject.hpl2.Area;
import uk.ac.dur.matthew.bates.ugproject.hpl2.Door;
import uk.ac.dur.matthew.bates.ugproject.hpl2.EditorSession;
import uk.ac.dur.matthew.bates.ugproject.hpl2.HPL2Map;
import uk.ac.dur.matthew.bates.ugproject.hpl2.Light;
import uk.ac.dur.matthew.bates.ugproject.hpl2.Plane;
import uk.ac.dur.matthew.bates.ugproject.hpl2.StaticObject;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PathConfig;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PrettyPrinter;
import uk.ac.dur.matthew.bates.ugproject.model.DoorConnection;
import uk.ac.dur.matthew.bates.ugproject.model.FloorPlan;
import uk.ac.dur.matthew.bates.ugproject.model.Room;
import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;
import uk.ac.dur.matthew.bates.ugproject.model.Wall;
import uk.ac.dur.matthew.bates.ugproject.util.RandomUtils;

public class MapGenerator
{
	private static final float WALL_PADDING = 0.005f;

	private FloorPlan fp;
	private HPL2Map map;

	public MapGenerator(FloorPlan floorPlan)
	{
		this.fp = floorPlan;
		this.map = new HPL2Map();
		int width = fp.width();
		int height = fp.height();
		this.map.setEditorSession(EditorSession.lookatEditorSession(new double[] { width / 2, 40, 40 }, new double[] {
				width / 2.0, 2, height / 2.0 }));
		this.map.getMapData().addArea(getPlayerStart());
		this.map.getMapData().addLight(getGlobalAmbientLight());
		generateFloors();
		generateWalls();
		placeDoors();
	}

	private void placeDoors()
	{
		for (DoorConnection d : fp.doorConnections())
		{
			float x = d.doorPlacement().midpoint().x;
			float y = d.doorPlacement().midpoint().y;
			int front = d.frontFacing().orientation();
			int back = d.backFacing().orientation();

			StaticObject a = new StaticObject(getDoorWayModelByType(d.frontFacing().parent().type()));
			if (d.frontFacing().orientation() == Wall.NORTH) a.setWorldPos(new float[] { x, 0, y - WALL_PADDING });
			if (d.frontFacing().orientation() == Wall.SOUTH) a.setWorldPos(new float[] { x, 0, y + WALL_PADDING });
			if (d.frontFacing().orientation() == Wall.EAST) a.setWorldPos(new float[] { x - WALL_PADDING, 0, y });
			if (d.frontFacing().orientation() == Wall.WEST) a.setWorldPos(new float[] { x + WALL_PADDING, 0, y });
			a.setRotation(new float[] { 0, front, 0 });
			this.map.getMapData().addStaticObject(a);

			StaticObject b = new StaticObject(getDoorWayModelByType(d.backFacing().parent().type()));
			if (d.backFacing().orientation() == Wall.NORTH) b.setWorldPos(new float[] { x, 0, y - WALL_PADDING });
			if (d.backFacing().orientation() == Wall.SOUTH) b.setWorldPos(new float[] { x, 0, y + WALL_PADDING });
			if (d.backFacing().orientation() == Wall.EAST) b.setWorldPos(new float[] { x - WALL_PADDING, 0, y });
			if (d.backFacing().orientation() == Wall.WEST) b.setWorldPos(new float[] { x + WALL_PADDING, 0, y });
			b.setRotation(new float[] { 0, back, 0 });
			this.map.getMapData().addStaticObject(b);

			StaticObject f = new StaticObject(PathConfig.MANSION_DOOR_FRAME);
			f.setWorldPos(new float[] { x, 0, y });
			f.setRotation(new float[] { 0, front, 0 });
			this.map.getMapData().addStaticObject(f);

			Door c = new Door(PathConfig.DOOR_MANSION);
			c.setWorldPos(new float[] { x, 0, y });
			c.setRotation(new float[] { 0, back, 0 });
			c.setCollides(true);
			c.setStaticPhysics(false);
			this.map.getMapData().addEntity(c);
		}
	}

	private String getDoorWayModelByType(RoomType t)
	{
		switch (t)
		{
		case BATHROOM:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case BEDROOM:
			return PathConfig.MANSION_DOORWAY_RED;
		case DINING_ROOM:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case FOYER:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case GUEST_ROOM:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case KITCHEN:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case LAUNDRY:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case LIVING_ROOM:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case MASTER_BEDROOM:
			return PathConfig.MANSION_DOORWAY_RED;
		case STORAGE:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case STUDY:
			return PathConfig.MANSION_DOORWAY_RED;
		case TOILET:
			return PathConfig.MANSION_DOORWAY_RED;
		default:
			return PathConfig.MANSION_DOORWAY_WHITE;
		}
	}

	private Light getGlobalAmbientLight()
	{
		Light a = new Light();
		a.setWorldPos(new float[] { fp.width() / 2.0f, 2, fp.height() / 2.0f });
		a.setSize(new float[] { fp.width() + 2, 6, fp.height() + 2 });
		a.setSize(new float[] { fp.width() + 2, 6, fp.height() + 2 });
		return a;
	}

	private Area getPlayerStart()
	{
		Area a = new Area();
		a.setAreaType(Area.PLAYER_START);
		a.setWorldPos(new float[] { 1, 0.05f, 1 });
		a.setRotation(new float[] { 0, 45, 0 });
		return a;
	}

	private void generateFloors()
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

	private void generateWalls()
	{
		for (Wall t : fp.tessellation())
		{
			StaticObject o = new StaticObject(
					RandomUtils.getRandomBoolean() ? (fp.couldBeWindow(t) ? PathConfig.WINDOW_LARGE_BLUE
							: getWallModel(t)) : getWallModel(t));
			if (t.orientation() == Wall.NORTH)
				o.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y - WALL_PADDING });
			if (t.orientation() == Wall.SOUTH)
				o.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y + WALL_PADDING });
			if (t.orientation() == Wall.EAST)
				o.setWorldPos(new float[] { t.midpoint().x - WALL_PADDING, 0, t.midpoint().y });
			if (t.orientation() == Wall.WEST)
				o.setWorldPos(new float[] { t.midpoint().x + WALL_PADDING, 0, t.midpoint().y });
			o.setRotation(new float[] { 0, t.orientation(), 0 });
			map.getMapData().addStaticObject(o);
		}
	}

	protected String getWallModel(Wall t)
	{
		switch (t.parent().type())
		{
		case BATHROOM:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case BEDROOM:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
		case DINING_ROOM:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case FOYER:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case GUEST_ROOM:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case KITCHEN:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case LAUNDRY:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case LIVING_ROOM:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case MASTER_BEDROOM:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
		case STORAGE:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case STUDY:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
		case TOILET:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
		default:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
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
