package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.ArrayList;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.generators.Squarify;
import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;

public class FloorPlan
{
	private List<Double> mAreas;
	private List<Rect> mRoomBounds;
	private List<Line> mLines;
	private List<Wall> mWalls;
	private List<WallConnection> mWallConnections;
	private List<Room> mRooms;
	private List<Wall> mTessellation;
	private Node mNodeRoot;

	public FloorPlan(int width, int height, ArrayList<Double> areas)
	{
		this.mAreas = new ArrayList<Double>(areas);
		mRoomBounds = Squarify.squarify(areas, new RectF(0, 0, width, height));
	}

	public List<Rect> roomBounds()
	{
		return new ArrayList<Rect>(mRoomBounds);
	}

	public List<Room> rooms()
	{
		if (mRooms != null) return mRooms;
		RoomAllocationRule rule = new RandomStackRoomAllocation();
		mRooms = rule.allocateRooms(this);
		return mRooms;
	}

	public List<Integer> areas()
	{
		List<Integer> xs = new ArrayList<Integer>();
		for (Double d : mAreas)
		{
			xs.add(d.intValue());
		}
		return xs;
	}

	public List<Wall> walls()
	{
		if (mWalls != null) return mWalls;
		List<Wall> walls = new ArrayList<Wall>();
		for (Room r : rooms())
		{
			walls.add(new Wall(r.top(), r, Wall.SOUTH));
			walls.add(new Wall(r.right(), r, Wall.WEST));
			walls.add(new Wall(r.bottom(), r, Wall.NORTH));
			walls.add(new Wall(r.left(), r, Wall.EAST));
		}
		mWalls = walls;
		return walls;
	}

	public List<Welder> welders()
	{
		List<Welder> welders = new ArrayList<Welder>();
		for (Room r : rooms())
		{
			if (r.type() == RoomType.BATHROOM) continue;
			if (r.type() == RoomType.STORAGE) continue;
			if (r.type() == RoomType.PANTRY) continue;
			if (r.type() == RoomType.TOILET) continue;

			Point tl = new Point(r.x, r.y);
			Point tr = new Point(r.x + r.width, r.y);
			Point bl = new Point(r.x, r.y + r.height);
			Point br = new Point(r.x + r.width, r.y + r.height);

			if (tl.x % 2 != 0 || tl.y % 2 != 0) welders.add(new Welder(r, tl, Welder.TOP_LEFT));
			if (tr.x % 2 != 0 || tr.y % 2 != 0) welders.add(new Welder(r, tr, Welder.TOP_RIGHT));
			if (bl.x % 2 != 0 || bl.y % 2 != 0) welders.add(new Welder(r, bl, Welder.BOTTOM_LEFT));
			if (br.x % 2 != 0 || br.y % 2 != 0)
				welders.add(new Welder(r, br, Welder.BOTTOM_RIGHT));
		}
		return welders;
	}

	public List<Line> wallLines()
	{
		if (mLines != null) return mLines;
		List<Line> lines = new ArrayList<Line>();
		for (Rect r : mRoomBounds)
		{
			lines.add(r.top());
			lines.add(r.right());
			lines.add(r.bottom());
			lines.add(r.left());
		}
		mLines = lines;
		return lines;
	}

	public List<WallConnection> wallConnections()
	{
		if (mWallConnections != null) return new ArrayList<WallConnection>(mWallConnections);
		List<WallConnection> connections = new ArrayList<WallConnection>();
		DoorPlacingRule placementRule = new SimpleDoorPlacementRule();
		for (int i = 0; i < walls().size(); i++)
		{
			for (int j = i + 1; j < walls().size(); j++)
			{
				Wall a = walls().get(i);
				Wall b = walls().get(j);
				WallConnection c = WallConnection.create(a, b, placementRule);
				if (c != null) connections.add(c);
			}
		}
		mWallConnections = connections;
		return connections;
	}

	public List<Wall> tessellation()
	{
		return mTessellation;
	}

	public Node pathGraph()
	{
		if (mNodeRoot != null) return mNodeRoot;

		List<Node> nodes = new ArrayList<Node>();

		for (Room r : rooms())
		{
			Node n = new Node(r.midpoint(), new ArrayList<Node>());
			nodes.add(n);
		}

		for (WallConnection wc : wallConnections())
		{
			if (wc instanceof DoorConnection)
			{
				DoorConnection dc = (DoorConnection)wc;
				List<Node> edges = new ArrayList<Node>();
				Node n = new Node(dc.doorPlacement().midpoint(), edges);
				if (!nodes.contains(n))
				{
					Wall a = dc.frontFacing();
					Wall b = dc.backFacing();
					edges.add(nodes.get(a.parent().id()));
					nodes.get(a.parent().id()).edges().add(n);
					edges.add(nodes.get(b.parent().id()));
					nodes.get(b.parent().id()).edges().add(n);
					nodes.add(n);
				}
			}
		}
		
		mNodeRoot = nodes.get(rooms().size());

		return mNodeRoot;

	}

	public int width()
	{
		int maxX = 0;
		int minX = Integer.MAX_VALUE;
		for (Rect r : mRoomBounds)
		{
			int rRight = r.x + r.width;
			if (rRight > maxX) maxX = rRight;
			if (r.x < minX) minX = r.x;
		}
		return maxX - minX;
	}

	public int height()
	{
		int maxY = 0;
		int minY = Integer.MAX_VALUE;
		for (Rect r : mRoomBounds)
		{
			int rBottom = r.y + r.height;
			if (rBottom > maxY) maxY = rBottom;
			if (r.y < minY) minY = r.y;
		}
		return maxY - minY;
	}
}