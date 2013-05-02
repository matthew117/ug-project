package uk.ac.dur.matthew.bates.ugproject.gui;

import static uk.ac.dur.matthew.bates.ugproject.hpl2.util.PathConfig.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bates.jamie.graphics.collision.OBB;
import bates.jamie.graphics.util.Matrix;

import uk.ac.dur.matthew.bates.ugproject.hpl2.Area;
import uk.ac.dur.matthew.bates.ugproject.hpl2.Door;
import uk.ac.dur.matthew.bates.ugproject.hpl2.EditorSession;
import uk.ac.dur.matthew.bates.ugproject.hpl2.Entity;
import uk.ac.dur.matthew.bates.ugproject.hpl2.HPL2Map;
import uk.ac.dur.matthew.bates.ugproject.hpl2.Light;
import uk.ac.dur.matthew.bates.ugproject.hpl2.MapData;
import uk.ac.dur.matthew.bates.ugproject.hpl2.ObjectFactory;
import uk.ac.dur.matthew.bates.ugproject.hpl2.Plane;
import uk.ac.dur.matthew.bates.ugproject.hpl2.StaticObject;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PathConfig;
import uk.ac.dur.matthew.bates.ugproject.model.DoorConnection;
import uk.ac.dur.matthew.bates.ugproject.model.FloorPlan;
import uk.ac.dur.matthew.bates.ugproject.model.Room;
import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;
import uk.ac.dur.matthew.bates.ugproject.model.Tessellation;
import uk.ac.dur.matthew.bates.ugproject.model.Wall;
import uk.ac.dur.matthew.bates.ugproject.util.ListUtils;
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
		furnishRooms();
		getBoundingBoxes();
	}

	private void furnishRooms()
	{
		for (Room r : fp.rooms())
		{
			furnishRoom(r);
		}
	}

	private void furnishRoom(Room r)
	{
		int x = r.x;
		int y = r.y;
		int width = r.width;
		int height = r.height;

		float mx = (float) (x + width / 2.0);
		float my = (float) (y + height / 2.0);

		RoomType type = r.type();
		List<String> possibleObjects = PathConfig.getObjectListByRoomType(type);

		MapData map = this.map.getMapData();
		ObjectFactory o = new ObjectFactory("");

		switch (type)
		{
		case BATHROOM:
			furnishBathroom(r, map);
			break;
		case BEDROOM:
			furnishBedroom(r, map);
			break;
		case CORRIDOR:
			break;
		case DINING_ROOM:
			map.addObject(o.file(TABLE_NICE_WOOD).position(mx, 0, my).scale(1.3f, 1.0f, 1.3f).build());

			o.scale(1, 1, 1);
			o.file(RandomUtils.getRandomBoolean() ? CHAIR_NICE01 : CHAIR_NICE02);

			map.addObject(o.position(mx - 0.5f, 0, my - 0.5f).rotation(0, 0, 0).build());
			map.addObject(o.position(mx + 0.5f, 0, my - 0.5f).rotation(0, 0, 0).build());
			map.addObject(o.position(mx - 1.25f, 0, my).rotation(0, 90, 0).build());
			map.addObject(o.position(mx - 0.5f, 0, my + 0.5f).rotation(0, 180, 0).build());
			map.addObject(o.position(mx + 0.5f, 0, my + 0.5f).rotation(0, 180, 0).build());
			map.addObject(o.position(mx + 1.25f, 0, my).rotation(0, -90, 0).build());

			map.addObject(o.file(PLATE).position(mx - 0.5f, 1, my - 0.4f).build());
			map.addObject(o.file(PLATE).position(mx + 0.5f, 1, my - 0.4f).build());
			map.addObject(o.file(PLATE).position(mx - 1.05f, 1, my).build());
			map.addObject(o.file(PLATE).position(mx - 0.5f, 1, my + 0.4f).build());
			map.addObject(o.file(PLATE).position(mx + 0.5f, 1, my + 0.4f).build());
			map.addObject(o.file(PLATE).position(mx + 1.05f, 1, my).build());

			break;
		case FOYER:
			break;
		case GUEST_ROOM:
			break;
		case KITCHEN:
			break;
		case LAUNDRY:
			break;
		case LIVING_ROOM:
			break;
		case MASTER_BEDROOM:
			break;
		case STORAGE:
			break;
		case STUDY:
			break;
		case TOILET:
			furnishToilet(r, map);
			break;
		default:
			break;

		}
	}

	protected void furnishToilet(Room r, MapData map)
	{
		ObjectFactory o = new ObjectFactory("");

		List<Tessellation> xs = fp.wallTessellationsByRoomID(r.id());
		Tessellation p = ListUtils.random(xs);
		xs.remove(p);
		int px = p.midpoint().x;
		int py = p.midpoint().y;

		StaticObject s = o.file(TOILET).position(px, 0, py).rotate(0, 0, 0.235f, 0, p.orientation(), 0).build();

		map.addObject(s);
		map.addObject(o.file(TOILET_PAPER).rotate(-0.4f, 0, 0, 0, 0, 0).build());
		map.addObject(o.file(TOILET_PAPER).position(s.getX(), 0.932f, s.getZ()).build());

		p = ListUtils.random(xs);
		px = p.midpoint().x;
		py = p.midpoint().y;

		map.addObject(o.file(HANDWASH_BASIN).position(px, 0, py).rotation(0, -90, 0)
				.rotate(0, 0, 0.235f, 0, p.orientation(), 0).build());
	}

	protected void furnishBathroom(Room r, MapData map)
	{
		ObjectFactory o = new ObjectFactory("");

		List<Tessellation> xs = fp.wallTessellationsByRoomID(r.id());
		Tessellation p = ListUtils.random(xs);
		xs.remove(p);
		int px = p.midpoint().x;
		int py = p.midpoint().y;

		StaticObject s = o.file(TOILET).position(px, 0, py).rotate(0, 0, 0.235f, 0, p.orientation(), 0).build();

		map.addObject(s);
		map.addObject(o.file(TOILET_PAPER).rotate(-0.4f, 0, 0, 0, 0, 0).build());
		map.addObject(o.file(TOILET_PAPER).position(s.getX(), 0.932f, s.getZ()).build());

		p = ListUtils.random(xs);
		xs.remove(p);
		px = p.midpoint().x;
		py = p.midpoint().y;

		map.addObject(o.file(HANDWASH_BASIN).position(px, 0, py).rotation(0, -90, 0)
				.rotate(0, 0, 0.235f, 0, p.orientation(), 0).build());

		for (Tessellation w : xs)
		{
			if (w.length() >= 4)
			{
				int wx = w.midpoint().x;
				int wy = w.midpoint().y;

				map.addObject(o.file(BATHTUB).position(wx, 0, wy).rotation(0, 0, 0)
						.rotate(0, 0, 1.1f, 0, w.orientation(), 0).build());
				break;
			}
		}

	}

	protected void furnishBedroom(Room r, MapData map)
	{
		ObjectFactory o = new ObjectFactory("");

		List<Tessellation> xs = fp.tessellationsByRoomID(r.id());
		for (Iterator<Tessellation> iterator = xs.iterator(); iterator.hasNext();)
		{
			Tessellation t = iterator.next();
			if (t.type() == Tessellation.Type.DOOR || t.type() == Tessellation.Type.ALCOVE)
			{
				iterator.remove();
			}
		}

		Tessellation p = ListUtils.random(xs);
		xs.remove(p);
		int px = p.midpoint().x;
		int py = p.midpoint().y;

		StaticObject s = o.file(CABINET_NICE).position(px, 0, py).rotate(0, 0, 0.75f, 0, p.orientation(), 0).build();

		map.addObject(s);

		for (Tessellation w : xs)
		{
			if (w.length() >= 4)
			{
				int wx = w.midpoint().x;
				int wy = w.midpoint().y;

				map.addObject(o.file(BED_NICE).position(wx, 0, wy).rotation(0, 0, 0)
						.rotate(0, 0, 2, 0, w.orientation(), 0).build());
				break;
			}
		}

	}

	private boolean collides(List<StaticObject> xs, StaticObject x)
	{
		for (StaticObject s : xs)
		{
			if (collides(x, s)) return true;
		}
		return false;
	}

	private boolean collides(StaticObject a, StaticObject b)
	{
		OBB aOBB = a.getOBB();
		OBB bOBB = b.getOBB();

		return aOBB.testOBB(bOBB);
	}

	private void addObjects(List<StaticObject> xs)
	{
		for (StaticObject o : xs)
		{
			this.map.getMapData().addObject(o);
		}
	}

	private void placeDoors()
	{
		for (DoorConnection d : fp.doorConnections())
		{
			float x = d.doorPlacement().midpoint().x;
			float y = d.doorPlacement().midpoint().y;

			int front;
			int back;

			if (d.frontFacing().orientation() == Wall.NORTH || d.frontFacing().orientation() == Wall.SOUTH)
			{
				front = d.frontFacing().orientation();
				back = d.frontFacing().flippedOrientation();
			}
			else
			{
				front = d.backFacing().orientation();
				back = d.frontFacing().orientation();
			}

			StaticObject a = new StaticObject(getDoorWayModelByType(d.frontFacing().parent().type(), d.frontFacing()));
			if (d.frontFacing().orientation() == Wall.NORTH) a.setWorldPos(new float[] { x, 0, y - WALL_PADDING });
			if (d.frontFacing().orientation() == Wall.SOUTH) a.setWorldPos(new float[] { x, 0, y + WALL_PADDING });
			if (d.frontFacing().orientation() == Wall.EAST) a.setWorldPos(new float[] { x + WALL_PADDING, 0, y });
			if (d.frontFacing().orientation() == Wall.WEST) a.setWorldPos(new float[] { x - WALL_PADDING, 0, y });
			a.setRotation(new float[] { 0, front, 0 });
			this.map.getMapData().addStaticObject(a);

			StaticObject b = new StaticObject(getDoorWayModelByType(d.backFacing().parent().type(), d.frontFacing()));
			if (d.backFacing().orientation() == Wall.NORTH) b.setWorldPos(new float[] { x, 0, y - WALL_PADDING });
			if (d.backFacing().orientation() == Wall.SOUTH) b.setWorldPos(new float[] { x, 0, y + WALL_PADDING });
			if (d.backFacing().orientation() == Wall.EAST) b.setWorldPos(new float[] { x + WALL_PADDING, 0, y });
			if (d.backFacing().orientation() == Wall.WEST) b.setWorldPos(new float[] { x - WALL_PADDING, 0, y });
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

	private String getDoorWayModelByType(RoomType t, Wall w)
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
			return PathConfig.MANSION_DOORWAY_RED;
		case LIVING_ROOM:
			return PathConfig.MANSION_DOORWAY_WHITE;
		case MASTER_BEDROOM:
			return PathConfig.MANSION_DOORWAY_RED;
		case STORAGE:
			return PathConfig.MANSION_DOORWAY_RED;
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
		a.setSize(new float[] { fp.width() + 4, 6, fp.height() + 4 });
		return a;
	}

	private Area getPlayerStart()
	{
		Area a = new Area();
		a.setAreaType(Area.PLAYER_START);
		a.setWorldPos(new float[] { 3, 0.05f, 3 });
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

	private void getBoundingBoxes()
	{
		List<StaticObject> ys = new ArrayList<StaticObject>();
		for (Entity e : map.getMapData().getEntities())
		{
			OBB obb = e.getOBB();

			float[] position = e.getWorldPos();
			float[] extents = e.getBounds();
			float[] rotation = e.getRotation();

			float cx = e.getMinX() + (e.getWidth() / 2.0f)  ;
			float cy = e.getMinY() - (e.getHeight() / 2.0f) ;
			float cz = e.getMinZ() + (e.getDepth()/ 2.0f);

			Area bound = new Area();
			bound.setAreaType(Area.SCRIPT_AREA);
			bound.setWorldPos(e.getX() - cx, e.getY() - cy, e.getZ() - cz);
			bound.setRotation(rotation[0], rotation[1], rotation[2]);
			bound.setScale(extents[0], extents[1], extents[2]);

			map.getMapData().addArea(bound);

		}

		addObjects(ys);
	}

	private void generateWalls()
	{
		for (Tessellation t : fp.tessellation())
		{
			switch (t.type())
			{
			case ALCOVE:

				List<StaticObject> xs = new ArrayList<StaticObject>();

				if (RandomUtils.getRandomBoolean())
				{
					xs = generateAlcove(t);

					int tx = t.midpoint().x;
					int ty = t.midpoint().y;

					Plane plane = new Plane(getFloorMaterial(t.parent().type()));

					if (t.orientation() == Wall.NORTH)
					{
						plane.setStartCorner(new float[] { 0, 0, 0 });
						plane.setEndCorner(new float[] { 6, 0, 2 });
						plane.setWorldPos(new float[] { tx - 3, 0, ty });
					}
					else if (t.orientation() == Wall.SOUTH)
					{
						plane.setStartCorner(new float[] { 0, 0, 0 });
						plane.setEndCorner(new float[] { 6, 0, 2 });
						plane.setWorldPos(new float[] { tx - 3, 0, ty - 2 });
					}
					else if (t.orientation() == Wall.EAST)
					{
						plane.setStartCorner(new float[] { 0, 0, 0 });
						plane.setEndCorner(new float[] { 2, 0, 6 });
						plane.setWorldPos(new float[] { tx, 0, ty - 3 });
					}
					else if (t.orientation() == Wall.WEST)
					{
						plane.setStartCorner(new float[] { 0, 0, 0 });
						plane.setEndCorner(new float[] { 2, 0, 6 });
						plane.setWorldPos(new float[] { tx - 2, 0, ty - 3 });
					}

					map.getMapData().addPrimitive(plane);
				}
				else
				{
					xs = generateRandomAlcoveReplacement(t);
				}

				for (StaticObject x : xs)
				{
					map.getMapData().addObject(x);
				}
				break;
			case DOOR:
				// Do nothing, placeDoors() will handle this
				break;
			case STOVE:
				if (RandomUtils.getRandomBoolean())
				{
					StaticObject a = new StaticObject(PathConfig.WALL_STOVE);
					if (t.orientation() == Wall.NORTH)
						a.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y - WALL_PADDING });
					if (t.orientation() == Wall.SOUTH)
						a.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y + WALL_PADDING });
					if (t.orientation() == Wall.EAST)
						a.setWorldPos(new float[] { t.midpoint().x - WALL_PADDING, 0, t.midpoint().y });
					if (t.orientation() == Wall.WEST)
						a.setWorldPos(new float[] { t.midpoint().x + WALL_PADDING, 0, t.midpoint().y });
					a.setRotation(new float[] { 0, t.orientation(), 0 });
					map.getMapData().addStaticObject(a);
				}
				else
				{
					StaticObject b = new StaticObject(getWallModel(t));
					if (t.orientation() == Wall.NORTH)
						b.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y - WALL_PADDING });
					if (t.orientation() == Wall.SOUTH)
						b.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y + WALL_PADDING });
					if (t.orientation() == Wall.EAST)
						b.setWorldPos(new float[] { t.midpoint().x - WALL_PADDING, 0, t.midpoint().y });
					if (t.orientation() == Wall.WEST)
						b.setWorldPos(new float[] { t.midpoint().x + WALL_PADDING, 0, t.midpoint().y });
					b.setRotation(new float[] { 0, t.orientation(), 0 });
					map.getMapData().addStaticObject(b);
				}
				break;

			case WALL:
				StaticObject b = new StaticObject(getWallModel(t));
				if (t.orientation() == Wall.NORTH)
					b.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y - WALL_PADDING });
				if (t.orientation() == Wall.SOUTH)
					b.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y + WALL_PADDING });
				if (t.orientation() == Wall.EAST)
					b.setWorldPos(new float[] { t.midpoint().x - WALL_PADDING, 0, t.midpoint().y });
				if (t.orientation() == Wall.WEST)
					b.setWorldPos(new float[] { t.midpoint().x + WALL_PADDING, 0, t.midpoint().y });
				b.setRotation(new float[] { 0, t.orientation(), 0 });
				map.getMapData().addStaticObject(b);
				break;
			case WINDOW:
				StaticObject d = new StaticObject(getWindowModel(t));
				if (t.orientation() == Wall.NORTH)
					d.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y - WALL_PADDING });
				if (t.orientation() == Wall.SOUTH)
					d.setWorldPos(new float[] { t.midpoint().x, 0, t.midpoint().y + WALL_PADDING });
				if (t.orientation() == Wall.EAST)
					d.setWorldPos(new float[] { t.midpoint().x - WALL_PADDING, 0, t.midpoint().y });
				if (t.orientation() == Wall.WEST)
					d.setWorldPos(new float[] { t.midpoint().x + WALL_PADDING, 0, t.midpoint().y });
				d.setRotation(new float[] { 0, t.orientation(), 0 });
				map.getMapData().addStaticObject(d);
				break;
			default:
				break;
			}

		}
	}

	private List<StaticObject> generateAlcove(Tessellation t)
	{
		List<StaticObject> xs = new ArrayList<StaticObject>();

		int x = t.midpoint().x;
		int y = t.midpoint().y;

		if (t.orientation() == Wall.NORTH)
		{
			StaticObject a = (new ObjectFactory(PathConfig.PILLAR)).position(x - 3, 0, y - WALL_PADDING).build();
			StaticObject b = (new ObjectFactory(PathConfig.PILLAR)).position(x - 1.95f, 0, y + 1.775f - WALL_PADDING)
					.build();
			StaticObject c = (new ObjectFactory(PathConfig.PILLAR)).position(x + 1.95f, 0, y + 1.775f - WALL_PADDING)
					.build();
			StaticObject d = (new ObjectFactory(PathConfig.PILLAR)).position(x + 3, 0, y - WALL_PADDING).build();

			StaticObject window = (new ObjectFactory(getWindowModel(t))).position(x, 0, y + 2 - WALL_PADDING)
					.rotation(0, t.orientation(), 0).build();

			StaticObject short01 = (new ObjectFactory(getHalfWallStatic(t.parent().type())))
					.position(x - 2.5f, 0, y + 1 - WALL_PADDING).rotation(0, t.orientation() - 60, 0).build();
			StaticObject short02 = (new ObjectFactory(getHalfWallStatic(t.parent().type())))
					.position(x + 2.5f, 0, y + 1 - WALL_PADDING).rotation(0, t.orientation() + 60, 0).build();

			StaticObject sofa = (new ObjectFactory(PathConfig.SOFA)).position(x, 0, y + 1.5f - WALL_PADDING)
					.rotation(0, t.orientation(), 0).build();

			StaticObject chair01 = (new ObjectFactory(RandomUtils.getRandomBoolean() ? PathConfig.CHAIR_NICE01
					: PathConfig.CHAIR_NICE02)).position(x - 2, 0, y + 1 - WALL_PADDING)
					.rotation(0, t.orientation() - 60, 0).build();
			StaticObject chair02 = (new ObjectFactory(RandomUtils.getRandomBoolean() ? PathConfig.CHAIR_NICE01
					: PathConfig.CHAIR_NICE02)).position(x + 2, 0, y + 1 - WALL_PADDING)
					.rotation(0, t.orientation() + 60, 0).build();

			xs.add(a);
			xs.add(b);
			xs.add(c);
			xs.add(d);
			xs.add(window);
			xs.add(short01);
			xs.add(short02);
			xs.add(sofa);

			if (RandomUtils.getRandomBoolean()) xs.add(chair01);
			if (RandomUtils.getRandomBoolean()) xs.add(chair02);
		}

		if (t.orientation() == Wall.SOUTH)
		{
			StaticObject a = (new ObjectFactory(PathConfig.PILLAR)).position(x - 3, 0, y + WALL_PADDING).build();
			StaticObject b = (new ObjectFactory(PathConfig.PILLAR)).position(x - 1.95f, 0, y - 1.775f + WALL_PADDING)
					.build();
			StaticObject c = (new ObjectFactory(PathConfig.PILLAR)).position(x + 1.95f, 0, y - 1.775f + WALL_PADDING)
					.build();
			StaticObject d = (new ObjectFactory(PathConfig.PILLAR)).position(x + 3, 0, y + WALL_PADDING).build();

			StaticObject window = (new ObjectFactory(getWindowModel(t))).position(x, 0, y - 2 + WALL_PADDING)
					.rotation(0, t.orientation(), 0).build();

			StaticObject short01 = (new ObjectFactory(getHalfWallStatic(t.parent().type())))
					.position(x - 2.5f, 0, y - 1 + WALL_PADDING).rotation(0, t.orientation() + 60, 0).build();
			StaticObject short02 = (new ObjectFactory(getHalfWallStatic(t.parent().type())))
					.position(x + 2.5f, 0, y - 1 + WALL_PADDING).rotation(0, t.orientation() - 60, 0).build();

			StaticObject sofa = (new ObjectFactory(PathConfig.SOFA)).position(x, 0, y - 1.5f + WALL_PADDING)
					.rotation(0, t.orientation(), 0).build();

			StaticObject chair01 = (new ObjectFactory(RandomUtils.getRandomBoolean() ? PathConfig.CHAIR_NICE01
					: PathConfig.CHAIR_NICE02)).position(x - 2, 0, y - 1 + WALL_PADDING)
					.rotation(0, t.orientation() + 60, 0).build();
			StaticObject chair02 = (new ObjectFactory(RandomUtils.getRandomBoolean() ? PathConfig.CHAIR_NICE01
					: PathConfig.CHAIR_NICE02)).position(x + 2, 0, y - 1 + WALL_PADDING)
					.rotation(0, t.orientation() - 60, 0).build();

			xs.add(a);
			xs.add(b);
			xs.add(c);
			xs.add(d);
			xs.add(window);
			xs.add(short01);
			xs.add(short02);
			xs.add(sofa);

			if (RandomUtils.getRandomBoolean()) xs.add(chair01);
			if (RandomUtils.getRandomBoolean()) xs.add(chair02);
		}
		if (t.orientation() == Wall.EAST)
		{
			StaticObject a = (new ObjectFactory(PathConfig.PILLAR)).position(x - WALL_PADDING, 0, y - 3).build();
			StaticObject b = (new ObjectFactory(PathConfig.PILLAR)).position(x + 1.775f - WALL_PADDING, 0, y - 1.95f)
					.build();
			StaticObject c = (new ObjectFactory(PathConfig.PILLAR)).position(x + 1.775f - WALL_PADDING, 0, y + 1.95f)
					.build();
			StaticObject d = (new ObjectFactory(PathConfig.PILLAR)).position(x + WALL_PADDING, 0, y + 3).build();

			StaticObject window = (new ObjectFactory(getWindowModel(t))).position(x + 2 - WALL_PADDING, 0, y)
					.rotation(0, t.orientation(), 0).build();

			StaticObject short01 = (new ObjectFactory(getHalfWallStatic(t.parent().type())))
					.position(x + 1 - WALL_PADDING, 0, y - 2.5f).rotation(0, t.orientation() + 60, 0).build();
			StaticObject short02 = (new ObjectFactory(getHalfWallStatic(t.parent().type())))
					.position(x + 1 - WALL_PADDING, 0, y + 2.5f).rotation(0, t.orientation() - 60, 0).build();

			StaticObject sofa = (new ObjectFactory(PathConfig.SOFA)).position(x + 1.5f - WALL_PADDING, 0, y)
					.rotation(0, t.orientation(), 0).build();

			StaticObject chair01 = (new ObjectFactory(RandomUtils.getRandomBoolean() ? PathConfig.CHAIR_NICE01
					: PathConfig.CHAIR_NICE02)).position(x + 1 - WALL_PADDING, 0, y - 2)
					.rotation(0, t.orientation() + 60, 0).build();
			StaticObject chair02 = (new ObjectFactory(RandomUtils.getRandomBoolean() ? PathConfig.CHAIR_NICE01
					: PathConfig.CHAIR_NICE02)).position(x + 1 - WALL_PADDING, 0, y + 2)
					.rotation(0, t.orientation() - 60, 0).build();

			xs.add(a);
			xs.add(b);
			xs.add(c);
			xs.add(d);
			xs.add(window);
			xs.add(short01);
			xs.add(short02);
			xs.add(sofa);

			if (RandomUtils.getRandomBoolean()) xs.add(chair01);
			if (RandomUtils.getRandomBoolean()) xs.add(chair02);
		}
		if (t.orientation() == Wall.WEST)
		{
			StaticObject a = (new ObjectFactory(PathConfig.PILLAR)).position(x + WALL_PADDING, 0, y - 3).build();
			StaticObject b = (new ObjectFactory(PathConfig.PILLAR)).position(x - 1.775f + WALL_PADDING, 0, y - 1.95f)
					.build();
			StaticObject c = (new ObjectFactory(PathConfig.PILLAR)).position(x - 1.775f + WALL_PADDING, 0, y + 1.95f)
					.build();
			StaticObject d = (new ObjectFactory(PathConfig.PILLAR)).position(x + WALL_PADDING, 0, y + 3).build();

			StaticObject window = (new ObjectFactory(getWindowModel(t))).position(x - 2 + WALL_PADDING, 0, y)
					.rotation(0, t.orientation(), 0).build();

			StaticObject short01 = (new ObjectFactory(getHalfWallStatic(t.parent().type())))
					.position(x - 1 + WALL_PADDING, 0, y - 2.5f).rotation(0, t.orientation() - 60, 0).build();
			StaticObject short02 = (new ObjectFactory(getHalfWallStatic(t.parent().type())))
					.position(x - 1 + WALL_PADDING, 0, y + 2.5f).rotation(0, t.orientation() + 60, 0).build();

			StaticObject sofa = (new ObjectFactory(PathConfig.SOFA)).position(x - 1.5f + WALL_PADDING, 0, y)
					.rotation(0, t.orientation(), 0).build();

			StaticObject chair01 = (new ObjectFactory(RandomUtils.getRandomBoolean() ? PathConfig.CHAIR_NICE01
					: PathConfig.CHAIR_NICE02)).position(x - 1 + WALL_PADDING, 0, y - 2)
					.rotation(0, t.orientation() - 60, 0).build();
			StaticObject chair02 = (new ObjectFactory(RandomUtils.getRandomBoolean() ? PathConfig.CHAIR_NICE01
					: PathConfig.CHAIR_NICE02)).position(x - 1 + WALL_PADDING, 0, y + 2)
					.rotation(0, t.orientation() + 60, 0).build();

			xs.add(a);
			xs.add(b);
			xs.add(c);
			xs.add(d);
			xs.add(window);
			xs.add(short01);
			xs.add(short02);
			xs.add(sofa);

			if (RandomUtils.getRandomBoolean()) xs.add(chair01);
			if (RandomUtils.getRandomBoolean()) xs.add(chair02);
		}

		return xs;
	}

	protected String getWallStatic(RoomType t)
	{
		switch (t)
		{
		case BATHROOM:
			return PathConfig.MANSION_WALL_WHITE;
		case BEDROOM:
			return PathConfig.MANSION_WALL_RED;
		case DINING_ROOM:
			return PathConfig.MANSION_WALL_WHITE;
		case FOYER:
			return PathConfig.MANSION_WALL_WHITE;
		case GUEST_ROOM:
			return PathConfig.MANSION_WALL_WHITE;
		case KITCHEN:
			return PathConfig.MANSION_WALL_WHITE;
		case LAUNDRY:
			return PathConfig.MANSION_WALL_RED;
		case LIVING_ROOM:
			return PathConfig.MANSION_WALL_WHITE;
		case MASTER_BEDROOM:
			return PathConfig.MANSION_WALL_RED;
		case STORAGE:
			return PathConfig.MANSION_WALL_RED;
		case STUDY:
			return PathConfig.MANSION_WALL_RED;
		case TOILET:
			return PathConfig.MANSION_WALL_RED;
		default:
			return PathConfig.MANSION_WALL_WHITE;
		}
	}

	protected String getHalfWallStatic(RoomType t)
	{
		switch (t)
		{
		case BATHROOM:
			return PathConfig.MANSION_HALFWALL_WHITE;
		case BEDROOM:
			return PathConfig.MANSION_HALFWALL_RED;
		case DINING_ROOM:
			return PathConfig.MANSION_HALFWALL_WHITE;
		case FOYER:
			return PathConfig.MANSION_HALFWALL_WHITE;
		case GUEST_ROOM:
			return PathConfig.MANSION_HALFWALL_WHITE;
		case KITCHEN:
			return PathConfig.MANSION_HALFWALL_WHITE;
		case LAUNDRY:
			return PathConfig.MANSION_HALFWALL_RED;
		case LIVING_ROOM:
			return PathConfig.MANSION_HALFWALL_WHITE;
		case MASTER_BEDROOM:
			return PathConfig.MANSION_HALFWALL_RED;
		case STORAGE:
			return PathConfig.MANSION_HALFWALL_RED;
		case STUDY:
			return PathConfig.MANSION_HALFWALL_RED;
		case TOILET:
			return PathConfig.MANSION_HALFWALL_RED;
		default:
			return PathConfig.MANSION_HALFWALL_WHITE;
		}
	}

	private List<StaticObject> generateRandomAlcoveReplacement(Tessellation t)
	{
		List<StaticObject> xs = new ArrayList<StaticObject>();

		int x = t.midpoint().x;
		int y = t.midpoint().y;

		if (t.orientation() == Wall.NORTH)
		{
			if (RandomUtils.getRandomBoolean())
			{
				if (RandomUtils.getRandomBoolean()) // 1 -> 2
				{
					xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type())))
							.position(x - 2f, 0, y + WALL_PADDING).rotation(0, t.orientation(), 0).build());
					xs.add((new ObjectFactory(RandomUtils.getRandomBoolean() ? getHalfWallStatic(t.parent().type())
							: getWindowModel(t))).position(x + 1, 0, y + WALL_PADDING).rotation(0, t.orientation(), 0)
							.build());
				}
				else
				// 2 -> 1
				{
					xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type())))
							.position(x + 2f, 0, y + WALL_PADDING).rotation(0, t.orientation(), 0).build());
					xs.add((new ObjectFactory(RandomUtils.getRandomBoolean() ? getHalfWallStatic(t.parent().type())
							: getWindowModel(t))).position(x - 1, 0, y + WALL_PADDING).rotation(0, t.orientation(), 0)
							.build());
				}
			}
			else
			// 3
			{
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x - 2, 0, y + WALL_PADDING)
						.rotation(0, t.orientation(), 0).build());
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x, 0, y + WALL_PADDING)
						.rotation(0, t.orientation(), 0).build());
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x + 2, 0, y + WALL_PADDING)
						.rotation(0, t.orientation(), 0).build());
			}
		}

		if (t.orientation() == Wall.SOUTH)
		{
			if (RandomUtils.getRandomBoolean())
			{
				if (RandomUtils.getRandomBoolean()) // 1 -> 2
				{
					xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type())))
							.position(x - 2f, 0, y - WALL_PADDING).rotation(0, t.orientation(), 0).build());
					xs.add((new ObjectFactory(RandomUtils.getRandomBoolean() ? getHalfWallStatic(t.parent().type())
							: getWindowModel(t))).position(x + 1, 0, y + WALL_PADDING).rotation(0, t.orientation(), 0)
							.build());
				}
				else
				// 2 -> 1
				{
					xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type())))
							.position(x + 2f, 0, y - WALL_PADDING).rotation(0, t.orientation(), 0).build());
					xs.add((new ObjectFactory(getWindowModel(t))).position(x - 1, 0, y + WALL_PADDING)
							.rotation(0, t.orientation(), 0).build());
				}
			}
			else
			// 3
			{
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x - 2, 0, y - WALL_PADDING)
						.rotation(0, t.orientation(), 0).build());
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x, 0, y - WALL_PADDING)
						.rotation(0, t.orientation(), 0).build());
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x + 2, 0, y - WALL_PADDING)
						.rotation(0, t.orientation(), 0).build());
			}
		}
		if (t.orientation() == Wall.EAST)
		{
			if (RandomUtils.getRandomBoolean())
			{
				if (RandomUtils.getRandomBoolean()) // 1 -> 2
				{
					xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type())))
							.position(x - WALL_PADDING, 0, y - 2f).rotation(0, t.orientation(), 0).build());
					xs.add((new ObjectFactory(getWindowModel(t))).position(x - WALL_PADDING, 0, y + 1)
							.rotation(0, t.orientation(), 0).build());
				}
				else
				// 2 -> 1
				{
					xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type())))
							.position(x - WALL_PADDING, 0, y + 2f).rotation(0, t.orientation(), 0).build());
					xs.add((new ObjectFactory(getWindowModel(t))).position(x - WALL_PADDING, 0, y - 1)
							.rotation(0, t.orientation(), 0).build());
				}
			}
			else
			// 3
			{
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x - WALL_PADDING, 0, y - 2)
						.rotation(0, t.orientation(), 0).build());
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x - WALL_PADDING, 0, y)
						.rotation(0, t.orientation(), 0).build());
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x - WALL_PADDING, 0, y + 2)
						.rotation(0, t.orientation(), 0).build());
			}
		}
		if (t.orientation() == Wall.WEST)
		{
			if (RandomUtils.getRandomBoolean())
			{
				if (RandomUtils.getRandomBoolean()) // 1 -> 2
				{
					xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type())))
							.position(x + WALL_PADDING, 0, y - 2f).rotation(0, t.orientation(), 0).build());
					xs.add((new ObjectFactory(getWindowModel(t))).position(x + WALL_PADDING, 0, y + 1)
							.rotation(0, t.orientation(), 0).build());
				}
				else
				// 2 -> 1
				{
					xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type())))
							.position(x + WALL_PADDING, 0, y + 2f).rotation(0, t.orientation(), 0).build());
					xs.add((new ObjectFactory(RandomUtils.getRandomBoolean() ? getHalfWallStatic(t.parent().type())
							: getWindowModel(t))).position(x + WALL_PADDING, 0, y - 1).rotation(0, t.orientation(), 0)
							.build());
				}
			}
			else
			// 3
			{
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x + WALL_PADDING, 0, y - 2)
						.rotation(0, t.orientation(), 0).build());
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x + WALL_PADDING, 0, y)
						.rotation(0, t.orientation(), 0).build());
				xs.add((new ObjectFactory(getHalfWallStatic(t.parent().type()))).position(x + WALL_PADDING, 0, y + 2)
						.rotation(0, t.orientation(), 0).build());
			}
		}

		return xs;
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
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
		case LIVING_ROOM:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		case MASTER_BEDROOM:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
		case STORAGE:
			if (fp.isExternalWallNew(t))
			{
				return t.length() == 2 ? PathConfig.CELLAR_SHORT
						: RandomUtils.getRandomBoolean() ? PathConfig.CELLAR_WALL_BROKEN : PathConfig.CELLAR_WALL;
			}
			else
			{
				return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
			}
		case STUDY:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
		case TOILET:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
		default:
			return t.length() == 2 ? PathConfig.MANSION_HALFWALL_WHITE : PathConfig.MANSION_WALL_WHITE;
		}
	}

	protected String getWindowModel(Wall t)
	{
		switch (t.parent().type())
		{
		case BATHROOM:
			return PathConfig.WINDOW_BLUE;
		case BEDROOM:
			return PathConfig.WINDOW02_BLUE;
		case DINING_ROOM:
			return PathConfig.WINDOW_BLUE;
		case FOYER:
			return PathConfig.WINDOW_BLUE;
		case GUEST_ROOM:
			return PathConfig.WINDOW_BLUE;
		case KITCHEN:
			return PathConfig.WINDOW_BLUE;
		case LAUNDRY:
			return PathConfig.WINDOW02_BLUE;
		case LIVING_ROOM:
			return PathConfig.WINDOW_BLUE;
		case MASTER_BEDROOM:
			return PathConfig.WINDOW02_BLUE;
		case STORAGE:
			if (fp.isExternalWallNew(t))
			{
				return t.length() == 2 ? PathConfig.CELLAR_SHORT
						: RandomUtils.getRandomBoolean() ? PathConfig.CELLAR_WALL_BROKEN : PathConfig.CELLAR_WALL;
			}
			else
			{
				return t.length() == 2 ? PathConfig.MANSION_HALFWALL_RED : PathConfig.MANSION_WALL_RED;
			}
		case STUDY:
			return PathConfig.WINDOW02_BLUE;
		case TOILET:
			return PathConfig.WINDOW02_BLUE;
		default:
			return PathConfig.WINDOW_BLUE;
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
//		try
//		{
//			PrettyPrinter.printDocument(map.toString());
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
	}

}
