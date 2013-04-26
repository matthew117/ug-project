package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

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
	private List<Room.RoomType> mRoomTypes;

	public FloorPlan()
	{
	}

	public FloorPlan(int width, int height, ArrayList<Double> areas)
	{
		this.mAreas = new ArrayList<Double>(areas);
		mRoomBounds = Squarify.squarify(areas, new RectF(0, 0, width, height));
	}

	public FloorPlan(List<Rect> roomBounds)
	{
		mRoomBounds = roomBounds;
	}

	public FloorPlan(FloorPlan org, List<RoomType> roomTypes)
	{
		List<Double> a = new ArrayList<Double>();
		for (int i : org.areas())
			a.add((double) i);
		this.mAreas = a;
		this.mRoomBounds = new ArrayList<Rect>(org.roomBounds());

		List<Room> rooms = new ArrayList<Room>();
		List<Rect> roomBounds = roomBounds();
		for (int i = 0; i < roomBounds.size(); i++)
		{
			Rect r = roomBounds.get(i);
			rooms.add(new Room(r, i, roomTypes.get(i)));
		}

		this.mRooms = rooms;
	}

	public FloorPlan(int width, int height, ArrayList<Double> areas, List<RoomType> roomTypes)
	{
		mAreas = new ArrayList<Double>(areas);
		mRoomBounds = Squarify.squarify(areas, new RectF(0, 0, width, height));
		mRoomTypes = roomTypes;
	}

	public List<Rect> roomBounds()
	{
		return new ArrayList<Rect>(mRoomBounds);
	}

	public List<Room> rooms()
	{
		if (mRooms != null) return mRooms;
		if (mRoomTypes != null)
		{
			List<Room> rooms = new ArrayList<Room>();
			List<Rect> roomBounds = roomBounds();
			for (int i = 0; i < roomBounds.size(); i++)
			{
				Rect r = roomBounds.get(i);
				rooms.add(new Room(r, i, mRoomTypes.get(i)));
			}
			return rooms;
		}
		RoomAllocationRule rule = new RandomStackRoomAllocation();
		mRooms = rule.allocateRooms(this);
		mRoomTypes = new ArrayList<Room.RoomType>();
		for (Room r : mRooms)
		{
			mRoomTypes.add(r.type());
		}
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
			Point tl = new Point(r.x, r.y);
			Point tr = new Point(r.x + r.width, r.y);
			Point bl = new Point(r.x, r.y + r.height);
			Point br = new Point(r.x + r.width, r.y + r.height);

			if (tl.x % 2 != 0 || tl.y % 2 != 0) welders.add(new Welder(r, tl, Welder.TOP_LEFT));
			if (tr.x % 2 != 0 || tr.y % 2 != 0) welders.add(new Welder(r, tr, Welder.TOP_RIGHT));
			if (bl.x % 2 != 0 || bl.y % 2 != 0) welders.add(new Welder(r, bl, Welder.BOTTOM_LEFT));
			if (br.x % 2 != 0 || br.y % 2 != 0) welders.add(new Welder(r, br, Welder.BOTTOM_RIGHT));
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
		DoorPlacingRule placementRule = new ConnectStatsDoorPlacementRule();
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

	public int smallestRoomHeight()
	{
		int min = roomBounds().get(0).height;
		for (Rect r : roomBounds())
		{
			if (r.height < min) min = r.height;
		}
		return min;
	}

	public int smallestRoomWidth()
	{
		int min = roomBounds().get(0).width;
		for (Rect r : roomBounds())
		{
			if (r.width < min) min = r.width;
		}
		return min;
	}

	public Rect leftExternalBound()
	{
		return new Rect(0, 0, smallestRoomWidth() - 1, height());
	}

	public Rect rightExternalBound()
	{
		return new Rect(width() - (smallestRoomWidth() - 1), 0, (smallestRoomWidth() - 1), height());
	}

	public Rect topExternalBound()
	{
		return new Rect(0, 0, width(), smallestRoomHeight() - 1);
	}

	public Rect bottomExternalBound()
	{
		return new Rect(0, height() - (smallestRoomHeight() - 1), width(), (smallestRoomHeight() - 1));
	}

	public boolean isExternalWall(Wall w)
	{
		return !isInternalWall(w);
	}

	public boolean isInternalWall(Wall w)
	{
		for (WallConnection c : wallConnections())
		{
			if (c.frontFacing().equals(w) || c.backFacing().equals(w)) return true;
		}
		return false;
	}
	
	public boolean couldBeWindow(Wall q)
	{
		if (q.length() != 4) return false;
		
		for (WallConnection c : wallConnections())
		{
			Line a = Line.overlap(c.frontFacing(), q);
			Line b = Line.overlap(c.backFacing(), q);
			if (a == null || b == null) continue;
			if (a.length() > 1 || b.length() > 1) return false;
		}
		
		return true;
	}

	public boolean isDoorWall(Wall w)
	{
		for (DoorConnection c : doorConnections())
		{
			if (c.frontFacing().equals(w) || c.backFacing().equals(w)) return true;
		}
		return false;
	}

	public List<DoorConnection> doorConnections()
	{
		List<DoorConnection> dcs = new ArrayList<DoorConnection>();
		for (WallConnection wc : wallConnections())
		{
			if (wc instanceof DoorConnection)
			{
				dcs.add((DoorConnection) wc);
			}
		}
		return dcs;
	}

	public List<RoomType> roomTypes()
	{
		List<RoomType> roomTypeList = new ArrayList<Room.RoomType>();
		for (Room r : rooms())
		{
			roomTypeList.add(r.type());
		}
		return roomTypeList;
	}

	public FloorPlan changeRoomType(int roomID, RoomType type)
	{
		List<RoomType> roomTypes = roomTypes();
		roomTypes.set(roomID, type);
		return new FloorPlan(this, roomTypes);
	}

	public List<DoorConnection> relatedDoorConnections(Wall w)
	{
		List<DoorConnection> xs = new ArrayList<DoorConnection>();
		for (DoorConnection dc : doorConnections())
		{
			if (dc.frontFacing().equals(w) || dc.backFacing().equals(w)) xs.add(dc);
		}
		return xs;
	}

	public List<Wall> tessellation()
	{
		if (mTessellation != null) return mTessellation;
		List<Wall> tessellation = new ArrayList<Wall>();
		for (Room r : rooms())
		{
			Line left = r.left();
			int leftLen = (int) left.length();
			List<DoorConnection> xs = relatedDoorConnections(new Wall(left, r, Wall.WEST));
			if (!xs.isEmpty())
			{
				for (DoorConnection dc : xs)
				{
					Line a = dc.leftWallSegment();
					Line b = dc.rightWallSegment();
					int aLenth = (int) a.length();
					int bLength = (int) b.length();

					for (int i = 0; i < aLenth;)
					{
						if (aLenth - i >= 4)
						{
							tessellation.add(new Wall(new Line(a.p.x, a.p.y + i, a.q.x, a.p.y + i + 4), r, Wall.WEST));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(a.p.x, a.p.y + i, a.q.x, a.p.y + i + 2), r, Wall.WEST));
							i += 2;
						}
					}

					for (int i = 0; i < bLength;)
					{
						if (bLength - i >= 4)
						{
							tessellation.add(new Wall(new Line(b.p.x, b.p.y + i, b.q.x, b.p.y + i + 4), r, Wall.WEST));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(b.p.x, b.p.y + i, b.q.x, b.p.y + i + 2), r, Wall.WEST));
							i += 2;
						}
					}

					for (int i = 0; b.q.y + i < left.q.y;)
					{
						System.out.println("odguidg");
						if (left.q.y - (b.q.y + i) >= 4)
						{
							tessellation.add(new Wall(new Line(b.p.x, b.p.y + i, b.q.x, b.p.y + i + 4), r, Wall.WEST));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(b.p.x, b.p.y + i, b.q.x, b.p.y + i + 2), r, Wall.WEST));
							i += 2;
						}
					}
				}
			}
			else
			{
				for (int i = 0; i < leftLen;)
				{
					if (leftLen - i >= 4)
					{
						tessellation.add(new Wall(new Line(left.p.x, left.p.y + i, left.q.x, left.p.y + i + 4), r,
								Wall.WEST));
						i += 4;
					}
					else
					{
						tessellation.add(new Wall(new Line(left.p.x, left.p.y + i, left.q.x, left.p.y + i + 2), r,
								Wall.WEST));
						i += 2;
					}
				}
			}

			Line right = r.right();
			int rightLen = (int) right.length();
			xs = relatedDoorConnections(new Wall(right, r, Wall.EAST));
			if (!xs.isEmpty())
			{
				for (DoorConnection dc : xs)
				{
					Line a = dc.leftWallSegment();
					Line b = dc.rightWallSegment();
					int aLenth = (int) a.length();
					int bLength = (int) b.length();

					for (int i = 0; i < aLenth;)
					{
						if (aLenth - i >= 4)
						{
							tessellation.add(new Wall(new Line(a.p.x, a.p.y + i, a.q.x, a.p.y + i + 4), r, Wall.EAST));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(a.p.x, a.p.y + i, a.q.x, a.p.y + i + 2), r, Wall.EAST));
							i += 2;
						}
					}

					for (int i = 0; i < bLength;)
					{
						if (bLength - i >= 4)
						{
							tessellation.add(new Wall(new Line(b.p.x, b.p.y + i, b.q.x, b.p.y + i + 4), r, Wall.EAST));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(b.p.x, b.p.y + i, b.q.x, b.p.y + i + 2), r, Wall.EAST));
							i += 2;
						}
					}
				}

				// TODO just do it normally but if next tessellation is door..

				// for (int i = 0; b.q.y + i < right.q.y;)
				// {
				// System.out.println("odguidg");
				// if (right.q.y - (b.q.y + i) >= 4)
				// {
				// tessellation.add(new Wall(new Line(b.p.x, b.p.y + i, b.q.x, b.p.y + i + 4), r,
				// Wall.EAST));
				// i += 4;
				// }
				// else
				// {
				// tessellation.add(new Wall(new Line(b.p.x, b.p.y + i, b.q.x, b.p.y + i + 2), r,
				// Wall.EAST));
				// i += 2;
				// }
				// }
			}
			else
			{
				for (int i = 0; i < rightLen;)
				{
					if (rightLen - i >= 4)
					{
						tessellation.add(new Wall(new Line(right.p.x, right.p.y + i, right.q.x, right.p.y + i + 4), r,
								Wall.EAST));
						i += 4;
					}
					else
					{
						tessellation.add(new Wall(new Line(right.p.x, right.p.y + i, right.q.x, right.p.y + i + 2), r,
								Wall.EAST));
						i += 2;
					}
				}
			}

			Line top = r.top();
			int topLen = (int) top.length();
			xs = relatedDoorConnections(new Wall(top, r, Wall.SOUTH));
			if (!xs.isEmpty())
			{
				for (DoorConnection dc : xs)
				{
					Line a = dc.leftWallSegment();
					Line b = dc.rightWallSegment();
					int aLenth = (int) a.length();
					int bLength = (int) b.length();

					for (int i = 0; i < aLenth;)
					{
						if (aLenth - i >= 4)
						{
							tessellation.add(new Wall(new Line(a.p.x + i, a.p.y, a.p.x + i + 4, a.q.y), r, Wall.SOUTH));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(a.p.x + i, a.p.y, a.p.x + i + 2, a.q.y), r, Wall.SOUTH));
							i += 2;
						}
					}

					for (int i = 0; i < bLength;)
					{
						if (bLength - i >= 4)
						{
							tessellation.add(new Wall(new Line(b.p.x + i, b.p.y, b.p.x + i + 4, b.q.y), r, Wall.SOUTH));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(b.p.x + i, b.p.y, b.p.x + i + 2, b.q.y), r, Wall.SOUTH));
							i += 2;
						}
					}

					for (int i = 0; b.q.x + i < top.q.x;)
					{
						if (top.q.x - (b.q.x + i) >= 4)
						{
							tessellation.add(new Wall(new Line(b.p.x + i, b.p.y, b.p.x + i + 4, b.q.y), r, Wall.SOUTH));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(b.p.x + i, b.p.y, b.p.x + i + 2, b.q.y), r, Wall.SOUTH));
							i += 2;
						}
					}
				}
			}
			else
			{
				for (int i = 0; i < topLen;)
				{
					if (topLen - i >= 4)
					{
						tessellation.add(new Wall(new Line(top.p.x + i, top.p.y, top.p.x + i + 4, top.q.y), r,
								Wall.SOUTH));
						i += 4;
					}
					else
					{
						tessellation.add(new Wall(new Line(top.p.x + i, top.p.y, top.p.x + i + 2, top.q.y), r,
								Wall.SOUTH));
						i += 2;
					}
				}
			}

			Line bottom = r.bottom();
			int bottomLen = (int) bottom.length();
			xs = relatedDoorConnections(new Wall(bottom, r, Wall.NORTH));
			if (!xs.isEmpty())
			{
				for (DoorConnection dc : xs)
				{
					Line a = dc.leftWallSegment();
					Line b = dc.rightWallSegment();
					int aLenth = (int) a.length();
					int bLength = (int) b.length();

					for (int i = 0; i < aLenth;)
					{
						if (aLenth - i >= 4)
						{
							tessellation.add(new Wall(new Line(a.p.x + i, a.p.y, a.p.x + i + 4, a.q.y), r, Wall.NORTH));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(a.p.x + i, a.p.y, a.p.x + i + 2, a.q.y), r, Wall.NORTH));
							i += 2;
						}
					}

					for (int i = 0; i < bLength;)
					{
						if (bLength - i >= 4)
						{
							tessellation.add(new Wall(new Line(b.p.x + i, b.p.y, b.p.x + i + 4, b.q.y), r, Wall.NORTH));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(b.p.x + i, b.p.y, b.p.x + i + 2, b.q.y), r, Wall.NORTH));
							i += 2;
						}
					}

					for (int i = 0; b.q.x + i < bottom.q.x;)
					{
						if (bottom.q.x - (b.q.x + i) >= 4)
						{
							tessellation.add(new Wall(new Line(b.p.x + i, b.p.y, b.p.x + i + 4, b.q.y), r, Wall.NORTH));
							i += 4;
						}
						else
						{
							tessellation.add(new Wall(new Line(b.p.x + i, b.p.y, b.p.x + i + 2, b.q.y), r, Wall.NORTH));
							i += 2;
						}
					}
				}
			}
			else
			{
				for (int i = 0; i < bottomLen;)
				{
					if (bottomLen - i >= 4)
					{
						tessellation.add(new Wall(new Line(bottom.p.x + i, bottom.p.y, bottom.p.x + i + 4, bottom.q.y),
								r, Wall.NORTH));
						i += 4;
					}
					else
					{
						tessellation.add(new Wall(new Line(bottom.p.x + i, bottom.p.y, bottom.p.x + i + 2, bottom.q.y),
								r, Wall.NORTH));
						i += 2;
					}
				}
			}
		}
		mTessellation = tessellation;
		return mTessellation;
	}

	public Node pathRoot()
	{
		if (mNodeRoot != null) return mNodeRoot;

		List<Node> nodes = new ArrayList<Node>();

		for (Room r : rooms())
		{
			Node n = new RoomNode(r.midpoint(), new ArrayList<Node>(), r);
			nodes.add(n);
		}

		for (WallConnection wc : wallConnections())
		{
			if (wc instanceof DoorConnection)
			{
				DoorConnection dc = (DoorConnection) wc;
				List<Node> edges = new ArrayList<Node>();
				Node n = new ConnectionNode(dc.doorPlacement().midpoint(), edges, dc);
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

		mNodeRoot = nodes.get(0);

		return mNodeRoot;
	}

	public Set<Node> nodeSet()
	{
		Queue<Node> Q = new ArrayBlockingQueue<Node>(rooms().size());
		Node v = pathRoot();
		Set<Node> m = new HashSet<Node>();
		Q.add(v);
		m.add(v);
		while (!Q.isEmpty())
		{
			Node t = Q.poll();
			for (Node e : t.edges())
			{
				if (!m.contains(e))
				{
					m.add(e);
					Q.add(e);
				}
			}
		}
		return m;
	}

	public int numberOfTessellationProblems()
	{
		int sum = 0;
		for (int i = 0; i < tessellation().size() - 1; i++)
		{
			Wall t = tessellation().get(i);
			for (int j = i + 1; j < tessellation().size(); j++)
			{
				Wall s = tessellation().get(j);
				if (!s.equals(t))
				{
					Line v = Line.overlap(t, s);
					if (s.isHorizontal() && t.isHorizontal() && s.orientation() == t.orientation())
					{
						if (v != null && v.length() >= 1) sum++;
					}
					else if (s.isVertical() && t.isVertical() && s.orientation() == t.orientation())
					{
						if (v != null && v.length() >= 1) sum++;
					}
					else
					{
						// if (v != null && v.length() >= 1) sum++;
					}
				}
			}
		}
		return sum;
	}

	public FloorPlan normalizeRoomSizes()
	{
		FloorPlan nFP = new FloorPlan(this.width(), this.height(), new ArrayList<Double>(mAreas),
				new ArrayList<RoomType>(mRoomTypes));
		for (int i = 0; i < nFP.roomBounds().size(); i++)
		{
			Rect r = nFP.roomBounds().get(i);
			if (r.width % 2 != 0) nFP = increaseWidthOfRoomByID(i);
			if (r.height % 2 != 0) nFP = increaseHeightOfRoomByID(i);
		}
		return nFP;
	}

	public int getIDByRoomBound(Rect r)
	{
		return roomBounds().indexOf(r);
	}

	public FloorPlan increaseWidthOfRoomByID(int roomID)
	{
		if (roomID > roomBounds().size() || roomID < 0) return this;
		List<Rect> rb = new ArrayList<Rect>(roomBounds());
		Rect a = rb.get(roomID);
		FloorPlan newFP = new FloorPlan(rb);
		for (int i = 0; i < rb.size(); i++)
		{
			Rect b = rb.get(i);
			if (!a.equals(b) && FeatureDetection.isDirectlyRightOf(b, a)) newFP = increaseXOfRoomByID(i);
		}
		a.width++;
		newFP.mAreas = this.mAreas;
		return newFP;
	}

	public FloorPlan increaseXOfRoomByID(int roomID)
	{
		if (roomID > roomBounds().size() || roomID < 0) return this;
		List<Rect> rb = new ArrayList<Rect>(roomBounds());
		Rect a = rb.get(roomID);
		FloorPlan newFP = new FloorPlan(rb);
		for (int i = 0; i < rb.size(); i++)
		{
			Rect b = rb.get(i);
			if (!a.equals(b) && FeatureDetection.isDirectlyRightOf(b, a)) newFP = increaseXOfRoomByID(i);
		}
		a.x++;
		newFP.mAreas = this.mAreas;
		return newFP;
	}

	public FloorPlan decreaseWidthOfRoomByID(int roomID)
	{
		if (roomID > roomBounds().size() || roomID < 0) return this;
		List<Rect> rb = new ArrayList<Rect>(roomBounds());
		Rect a = rb.get(roomID);
		FloorPlan newFP = new FloorPlan(rb);
		for (int i = 0; i < rb.size(); i++)
		{
			Rect b = rb.get(i);
			if (!a.equals(b) && FeatureDetection.isDirectlyRightOf(b, a)) newFP = decreaseXOfRoomByID(i);
		}
		a.width--;
		newFP.mAreas = this.mAreas;
		return newFP;
	}

	public FloorPlan decreaseXOfRoomByID(int roomID)
	{
		if (roomID > roomBounds().size() || roomID < 0) return this;
		List<Rect> rb = new ArrayList<Rect>(roomBounds());
		Rect a = rb.get(roomID);
		FloorPlan newFP = new FloorPlan(rb);
		for (int i = 0; i < rb.size(); i++)
		{
			Rect b = rb.get(i);
			if (!a.equals(b) && FeatureDetection.isDirectlyRightOf(b, a)) newFP = decreaseXOfRoomByID(i);
		}
		a.x--;
		newFP.mAreas = this.mAreas;
		return newFP;
	}

	public FloorPlan increaseHeightOfRoomByID(int roomID)
	{
		if (roomID > roomBounds().size() || roomID < 0) return this;
		List<Rect> rb = new ArrayList<Rect>(roomBounds());
		Rect a = rb.get(roomID);
		FloorPlan newFP = new FloorPlan(rb);
		for (int i = 0; i < rb.size(); i++)
		{
			Rect b = rb.get(i);
			if (!a.equals(b) && FeatureDetection.isDirectlyBelow(b, a)) newFP = increaseYOfRoomByID(i);
		}
		a.height++;
		newFP.mAreas = this.mAreas;
		return newFP;
	}

	public FloorPlan increaseYOfRoomByID(int roomID)
	{
		if (roomID > roomBounds().size() || roomID < 0) return this;
		List<Rect> rb = new ArrayList<Rect>(roomBounds());
		Rect a = rb.get(roomID);
		FloorPlan newFP = new FloorPlan(rb);
		for (int i = 0; i < rb.size(); i++)
		{
			Rect b = rb.get(i);
			if (!a.equals(b) && FeatureDetection.isDirectlyBelow(b, a)) newFP = increaseYOfRoomByID(i);
		}
		a.y++;
		newFP.mAreas = this.mAreas;
		return newFP;
	}

	public FloorPlan decreaseHeightOfRoomByID(int roomID)
	{
		if (roomID > roomBounds().size() || roomID < 0) return this;
		List<Rect> rb = new ArrayList<Rect>(roomBounds());
		Rect a = rb.get(roomID);
		FloorPlan newFP = new FloorPlan(rb);
		for (int i = 0; i < rb.size(); i++)
		{
			Rect b = rb.get(i);
			if (!a.equals(b) && FeatureDetection.isDirectlyBelow(b, a)) newFP = decreaseYOfRoomByID(i);
		}
		a.height--;
		newFP.mAreas = this.mAreas;
		return newFP;
	}

	public FloorPlan decreaseYOfRoomByID(int roomID)
	{
		if (roomID > roomBounds().size() || roomID < 0) return this;
		List<Rect> rb = new ArrayList<Rect>(roomBounds());
		Rect a = rb.get(roomID);
		FloorPlan newFP = new FloorPlan(rb);
		for (int i = 0; i < rb.size(); i++)
		{
			Rect b = rb.get(i);
			if (!a.equals(b) && FeatureDetection.isDirectlyBelow(b, a)) newFP = decreaseYOfRoomByID(i);
		}
		a.y--;
		newFP.mAreas = this.mAreas;
		return newFP;
	}

	public Rect getSelected(int x, int y)
	{
		for (Rect r : roomBounds())
		{
			if (r.contains(x, y)) return r;
		}
		return null;
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