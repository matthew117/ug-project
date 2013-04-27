package uk.ac.dur.matthew.bates.ugproject.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import uk.ac.dur.matthew.bates.ugproject.generators.BFSRoomAllocation;
import uk.ac.dur.matthew.bates.ugproject.generators.ConnectStatsDoorPlacementRule;
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
	private List<Tessellation> mTessellation;
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

	public FloorPlan(List<Rect> roomBounds, List<RoomType> roomTypes)
	{
		mRoomBounds = roomBounds;
		this.mRoomTypes = new ArrayList<Room.RoomType>(roomTypes);
	}

	public FloorPlan(FloorPlan org, List<RoomType> roomTypes)
	{
		List<Double> a = new ArrayList<Double>();
		for (int i : org.areas())
			a.add((double) i);
		this.mAreas = a;
		this.mRoomBounds = new ArrayList<Rect>(org.roomBounds());
		this.mRoomTypes = new ArrayList<Room.RoomType>(roomTypes);
	}

	public FloorPlan(int width, int height, ArrayList<Double> areas, List<RoomType> roomTypes)
	{
		mAreas = new ArrayList<Double>(areas);
		mRoomBounds = Squarify.squarify(areas, new RectF(0, 0, width, height));
		mRoomTypes = new ArrayList<Room.RoomType>(roomTypes);
	}

	public List<Rect> roomBounds()
	{
		return new ArrayList<Rect>(mRoomBounds);
	}

	public List<Room> rooms()
	{
		if (mRooms != null) return mRooms;

		mRooms = new ArrayList<Room>();
		for (int i = 0; i < roomTypes().size(); i++)
		{
			mRooms.add(new Room(roomBounds().get(i), i, roomTypes().get(i)));
		}
		return mRooms;
	}

	public List<RoomType> roomTypes()
	{
		if (mRoomTypes != null) return mRoomTypes;

		List<RoomType> roomTypeList = new ArrayList<Room.RoomType>();
		RoomAllocationRule rule = new BFSRoomAllocation();
		roomTypeList = rule.allocateRoomTypes(0, this);
		mRoomTypes = roomTypeList;
		return roomTypeList;
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

	public List<Room> getLeftMostRooms()
	{
		List<Room> xs = new ArrayList<Room>();
		Rect b = leftExternalBound();
		for (Room r : rooms())
		{
			if (r.intersects(b)) xs.add(r);
		}
		return xs;
	}

	public boolean isLeftMostRoom(Room r)
	{
		return getLeftMostRooms().contains(r);
	}

	public List<Room> getRightMostRooms()
	{
		List<Room> xs = new ArrayList<Room>();
		Rect b = rightExternalBound();
		for (Room r : rooms())
		{
			if (r.intersects(b)) xs.add(r);
		}
		return xs;
	}

	public boolean isRightMostRoom(Room r)
	{
		return getRightMostRooms().contains(r);
	}

	public List<Room> getTopMostRooms()
	{
		List<Room> xs = new ArrayList<Room>();
		Rect b = topExternalBound();
		for (Room r : rooms())
		{
			if (r.intersects(b)) xs.add(r);
		}
		return xs;
	}

	public boolean isTopMostRoom(Room r)
	{
		return getTopMostRooms().contains(r);
	}

	public List<Room> getBottomMostRooms()
	{
		List<Room> xs = new ArrayList<Room>();
		Rect b = bottomExternalBound();
		for (Room r : rooms())
		{
			if (r.intersects(b)) xs.add(r);
		}
		return xs;
	}

	public boolean isBottomMostRoom(Room r)
	{
		return getBottomMostRooms().contains(r);
	}

	public List<Room> getExternalRooms()
	{
		List<Room> xs = new ArrayList<Room>();
		xs.addAll(getTopMostRooms());
		xs.addAll(getRightMostRooms());
		xs.addAll(getBottomMostRooms());
		xs.addAll(getLeftMostRooms());
		return xs;
	}

	public boolean isExternalRoom(Room r)
	{
		return getExternalRooms().contains(r);
	}

	public boolean isInternalRoom(Room r)
	{
		return !isExternalRoom(r);
	}

	public List<Room> getInternalRooms()
	{
		List<Room> xs = new ArrayList<Room>(rooms());
		xs.removeAll(getExternalRooms());
		return xs;
	}

	public boolean isExternalWall(Wall w)
	{
		return !isInternalWall(w);
	}

	public boolean isExternalWallNew(Wall w)
	{
		if (leftExternalBound().contains(w.p.x, w.p.y) && leftExternalBound().contains(w.q.x, w.q.y)
				&& w.orientation() == Wall.WEST) return true;
		if (rightExternalBound().contains(w.p.x, w.p.y) && rightExternalBound().contains(w.q.x, w.q.y)
				&& w.orientation() == Wall.EAST) return true;
		if (topExternalBound().contains(w.p.x, w.p.y) && topExternalBound().contains(w.q.x, w.q.y)
				&& w.orientation() == Wall.SOUTH) return true;
		if (bottomExternalBound().contains(w.p.x, w.p.y) && bottomExternalBound().contains(w.q.x, w.q.y)
				&& w.orientation() == Wall.NORTH) return true;
		return false;
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

	public List<Line> getDoorPlacements()
	{
		List<Line> xs = new ArrayList<Line>();
		for (DoorConnection dc : doorConnections())
		{
			xs.add(dc.doorPlacement());
		}
		return xs;
	}

	public List<Tessellation> tessellation()
	{
		if (mTessellation != null) return mTessellation;
		List<Tessellation> tessellationList = new ArrayList<Tessellation>();
		for (Room r : rooms())
		{
			List<Tessellation> temp = new ArrayList<Tessellation>();

			Line z;
			int n;
			int o;

			// LEFT TESSELLATIONS ================================================================

			z = r.left();
			n = (int) z.length();
			o = Wall.WEST;

			for (int i = 0; i < n;)
			{
				Line a = new Line(z.p.x, z.p.y + i, z.q.x, z.p.y + i + 6);
				Line t = new Line(z.p.x, z.p.y + i, z.q.x, z.p.y + i + 4);
				Line s = new Line(z.p.x, z.p.y + i, z.q.x, z.p.y + i + 2);

				i = complex(r, temp, n, o, i, a, t, s);
			}

			// RIGHT TESSELLATIONS ================================================================

			z = r.right();
			n = (int) z.length();
			o = Wall.EAST;

			for (int i = 0; i < n;)
			{
				Line a = new Line(z.p.x, z.p.y + i, z.q.x, z.p.y + i + 6);
				Line t = new Line(z.p.x, z.p.y + i, z.q.x, z.p.y + i + 4);
				Line s = new Line(z.p.x, z.p.y + i, z.q.x, z.p.y + i + 2);

				i = complex(r, temp, n, o, i, a, t, s);
			}

			// TOP TESSELLATIONS ================================================================

			z = r.top();
			n = (int) z.length();
			o = Wall.SOUTH;

			for (int i = 0; i < n;)
			{
				Line a = new Line(z.p.x + i, z.p.y, z.p.x + i + 6, z.q.y);
				Line t = new Line(z.p.x + i, z.p.y, z.p.x + i + 4, z.q.y);
				Line s = new Line(z.p.x + i, z.p.y, z.p.x + i + 2, z.q.y);

				i = complex(r, temp, n, o, i, a, t, s);

			}

			// BOTTOM TESSELLATIONS ================================================================

			z = r.bottom();
			n = (int) z.length();
			o = Wall.NORTH;

			for (int i = 0; i < n;)
			{
				Line a = new Line(z.p.x + i, z.p.y, z.p.x + i + 6, z.q.y);
				Line t = new Line(z.p.x + i, z.p.y, z.p.x + i + 4, z.q.y);
				Line s = new Line(z.p.x + i, z.p.y, z.p.x + i + 2, z.q.y);

				i = complex(r, temp, n, o, i, a, t, s);
			}

			tessellationList.addAll(temp);
		}
		mTessellation = tessellationList;
		return mTessellation;
	}

	protected int complex(Room r, List<Tessellation> temp, int n, int o, int i, Line a, Line t, Line s)
	{
		if (partOfDoorTessellation(t) && partOfDoorTessellation(s))
		{
			i += 2;
			return i;
		}

		if (partOfDoorTessellation(t) && !partOfDoorTessellation(s))
		{
			temp.add(new Tessellation(s, r, o));
			i += 2;
			return i;
		}

		if (isDoorTessellation(t))
		{
			temp.add(new Tessellation(t, r, o, Tessellation.Type.DOOR));
			i += 4;
			return i;
		}

		if (n - i >= 4)
		{

			if (isExternalWallNew(new Wall(t, r, o)))
			{
				if ((r.type() == RoomType.LIVING_ROOM || r.type() == RoomType.KITCHEN
						|| r.type() == RoomType.GUEST_ROOM || r.type() == RoomType.FOYER)
						&& !containsTessellationType(temp, Tessellation.Type.STOVE))
				{
					temp.add(new Tessellation(t, r, o, Tessellation.Type.STOVE));
					i += 4;
					return i;
				}

				if ((n - i >= 6) && (r.type() == RoomType.LIVING_ROOM || r.type() == RoomType.MASTER_BEDROOM
						|| r.type() == RoomType.GUEST_ROOM || r.type() == RoomType.FOYER || r.type() == RoomType.STUDY))
				{
					temp.add(new Tessellation(a, r, o, Tessellation.Type.ALCOVE));
					i += 6;
				}
				else
				{
					temp.add(new Tessellation(t, r, o, Tessellation.Type.WINDOW));
					i += 4;
				}
			}
			else
			{
				temp.add(new Tessellation(t, r, o));
				i += 4;
			}

		}
		else
		{
			temp.add(new Tessellation(s, r, o));
			i += 2;
		}
		return i;
	}

	private static boolean containsTessellationType(List<Tessellation> xs, Tessellation.Type t)
	{
		for (Tessellation s : xs)
		{
			if (s.type() == t) return true;
		}
		return false;
	}

	public boolean isDoorTessellation(Line x)
	{
		for (Line y : getDoorPlacements())
		{
			if ((x.isHorizontal() && y.isHorizontal()) || (x.isVertical() && y.isVertical()))
			{
				if (x.equals(y)) return true;
			}
		}
		return false;
	}

	public boolean partOfDoorTessellation(Line x)
	{
		for (Line y : getDoorPlacements())
		{
			if ((x.isHorizontal() && y.isHorizontal()) || (x.isVertical() && y.isVertical()))
			{
				if (Line.overlap(x, y) != null && Line.overlap(x, y).length() > 0 && !x.equals(y)) return true;
			}
		}
		return false;
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

	public List<Integer> roomSizeProblemIndex()
	{
		List<Integer> xs = new ArrayList<Integer>();
		for (Room r : rooms())
		{
			int area = r.height * r.width;
			Point minMax = minMaxRoomSize(r.type());
			int min = minMax.x;
			int max = minMax.y;

			if (area < min) xs.add(-Math.abs(min - area));
			else xs.add(Math.abs(max - area));
		}
		return xs;
	}

	private Point minMaxRoomSize(RoomType t)
	{
		switch (t)
		{
		case BATHROOM:
			return new Point(16, 24);
		case BEDROOM:
			return new Point(16, 30);
		case CORRIDOR:
			return new Point(20, 40);
		case DINING_ROOM:
			return new Point(16, 28);
		case FOYER:
			return new Point(16, 32);
		case GUEST_ROOM:
			return new Point(12, 30);
		case KITCHEN:
			return new Point(16, 28);
		case LAUNDRY:
			return new Point(16, 12);
		case LIVING_ROOM:
			return new Point(16, 28);
		case MASTER_BEDROOM:
			return new Point(18, 35);
		case STORAGE:
			return new Point(16, 16);
		case STUDY:
			return new Point(16, 18);
		case TOILET:
			return new Point(16, 20);
		default:
			return new Point(0, Integer.MAX_VALUE);
		}
	}

	public List<Integer> unreachableRooms(int root)
	{
		Queue<Integer> q = new ArrayBlockingQueue<Integer>(n());
		Set<Integer> seen = new HashSet<Integer>();

		q.add(root);
		seen.add(root);

		while (!q.isEmpty())
		{
			int n = q.poll();

			for (int i : getDoorAdjacents(n))
			{
				if (!seen.contains(i))
				{
					seen.add(i);
					q.add(i);
				}
			}
		}

		List<Integer> xs = new ArrayList<Integer>();
		for (int i = 0; i < n(); i++)
		{
			if (!seen.contains(i)) xs.add(i);
		}
		return xs;
	}

	public int n()
	{
		return roomBounds().size();
	}

	class INode
	{
		private int mValue;
		private INode mParent;
		private List<INode> mChildren;

		INode(int x, INode parent, List<INode> xs)
		{
			mValue = x;
			mParent = parent;
			mChildren = xs;
		}

		int value()
		{
			return mValue;
		}

		List<INode> children()
		{
			return mChildren;
		}

		INode parent()
		{
			return mParent;
		}

		public String toString()
		{
			return "" + mValue;
		}
	}

	public INode iNodeTreeRoot(int root)
	{
		Queue<INode> q = new ArrayBlockingQueue<INode>(n());
		Set<Integer> seen = new HashSet<Integer>();

		INode rootNode = new INode(root, null, new ArrayList<INode>());
		q.add(rootNode);
		seen.add(root);

		while (!q.isEmpty())
		{
			INode n = q.poll();

			for (int i : getDoorAdjacents(n.value()))
			{
				if (!seen.contains(i))
				{
					INode m = new INode(i, n, new ArrayList<INode>());
					n.children().add(m);
					m.mParent = n;
					seen.add(i);
					q.add(m);
				}
			}
		}

		return rootNode;
	}

	public List<Point> iNodeTreeEdges(int root)
	{
		Queue<Integer> q = new ArrayBlockingQueue<Integer>(n());
		Set<Integer> seen = new HashSet<Integer>();
		List<Point> xs = new ArrayList<Point>();

		q.add(root);
		seen.add(root);

		while (!q.isEmpty())
		{
			int n = q.poll();

			for (int i : getDoorAdjacents(n))
			{
				if (!seen.contains(i))
				{
					xs.add(new Point(n, i));

					seen.add(i);
					q.add(i);
				}
			}
		}

		return xs;
	}

	public List<ArrayList<Integer>> dfsRoutes(int root)
	{
		List<ArrayList<Integer>> xss = new ArrayList<ArrayList<Integer>>();

		Queue<Integer> q = new ArrayBlockingQueue<Integer>(n());
		Set<Integer> seen = new HashSet<Integer>();

		q.add(root);
		seen.add(root);

		ArrayList<Integer> ys = new ArrayList<Integer>();
		ys.add(root);
		xss.add(ys);

		while (!q.isEmpty())
		{
			int n = q.poll();

			List<Integer> doorAdjacents = getDoorAdjacents(n);
			for (int k = 0; k < doorAdjacents.size(); k++)
			{
				int i = doorAdjacents.get(k);

				if (!seen.contains(i))
				{
					if (k > 0)
					{
						ArrayList<Integer> xs = new ArrayList<Integer>(xss.get(xss.size() - 1));
						xs.add(i);
						xss.add(xs);
					}
					else
					{
						ArrayList<Integer> xs = xss.get(xss.size() - 1);
						xs.add(i);
						xss.add(xs);
					}
					seen.add(i);
					q.add(i);
				}
			}
		}

		return xss;
	}

	public void newDFS(List<ArrayList<Integer>> xss, ArrayList<Integer> xs, INode n, int root, int depth)
	{
		if (n != null)
		{
			if (n.children().isEmpty()) return;
		}

		if (xs == null)
		{
			n = iNodeTreeRoot(root);
			xs = new ArrayList<Integer>();
			xs.add(root);
			xss.add(xs);
		}

		ArrayList<Integer> ys = new ArrayList<Integer>(xs);
		List<INode> children = n.children();
		for (int i = 0; i < children.size(); i++)
		{
			INode m = children.get(i);

			if (i == 0)
			{
				xs.add(m.value());
				newDFS(xss, xs, m, root, depth++);
			}
			else
			{
				ArrayList<Integer> zs = new ArrayList<Integer>(ys);
				xss.add(zs);
				zs.add(m.value());
				newDFS(xss, zs, m, root, depth++);
			}
		}

	}

	public int connectivityGraphDistance(int root)
	{
		int max = 0;
		List<ArrayList<Integer>> xss = new ArrayList<ArrayList<Integer>>();
		newDFS(xss, null, null, root, 0);
		for (ArrayList<Integer> xs : xss)
		{
			if (xs.size() > max) max = xs.size();
		}
		return max;
	}

	public List<ArrayList<RoomType>> privacyProblems(int root)
	{
		List<ArrayList<RoomType>> xss = new ArrayList<ArrayList<RoomType>>();
		List<ArrayList<Integer>> yss = new ArrayList<ArrayList<Integer>>();
		newDFS(yss, null, null, root, 0);
		for (ArrayList<Integer> ys : yss)
		{
			boolean hasPassedPublic = false;
			boolean problem = false;
			for (Integer i : ys)
			{
				if (hasPassedPublic && roomTypes().get(i).isPublic())
				{
					problem = true;
					break;
				}
				else
				{
					if (roomTypes().get(i).isPrivate()) hasPassedPublic = true;
				}
			}

			if (problem)
			{
				ArrayList<RoomType> xs = new ArrayList<RoomType>();
				for (Integer j : ys)
				{
					xs.add(roomTypes().get(j));
				}
				xss.add(xs);
			}
		}

		return xss;
	}

	public List<Integer> getAdjacents(int roomID)
	{
		Rect r = roomBounds().get(roomID);
		List<Integer> xs = new ArrayList<Integer>();
		for (int i = 0; i < roomBounds().size(); i++)
		{
			Rect s = roomBounds().get(i);
			if (FeatureDetection.isDirectlyAbove(r, s) && Line.overlap(r.bottom(), s.top()).length() >= 4) xs.add(i);
			if (FeatureDetection.isDirectlyBelow(r, s) && Line.overlap(r.top(), s.bottom()).length() >= 4) xs.add(i);
			if (FeatureDetection.isDirectlyLeftOf(r, s) && Line.overlap(r.right(), s.left()).length() >= 4) xs.add(i);
			if (FeatureDetection.isDirectlyRightOf(r, s) && Line.overlap(r.left(), s.right()).length() >= 4) xs.add(i);
		}
		return xs;
	}

	public List<Point> getDoorEdges()
	{
		List<Point> xs = new ArrayList<Point>();

		for (DoorConnection x : doorConnections())
		{
			xs.add(new Point(x.frontFacing().parent().id(), x.backFacing().parent().id()));
		}

		return xs;
	}

	public List<Integer> getDoorAdjacents(int roomID)
	{
		List<Integer> xs = new ArrayList<Integer>();

		for (Point p : getDoorEdges())
		{
			if (p.x == roomID) xs.add(p.y);
			if (p.y == roomID) xs.add(p.x);
		}

		return xs;
	}

	public List<Integer> breadthFirstTransversal(int roomID)
	{
		Queue<Integer> Q = new ArrayBlockingQueue<Integer>(roomBounds().size());
		List<Integer> m = new ArrayList<Integer>();
		Q.add(roomID);
		m.add(roomID);
		while (!Q.isEmpty())
		{
			int i = Q.poll();
			for (int j : getAdjacents(i))
			{
				if (!m.contains(j))
				{
					m.add(j);
					Q.add(j);
				}
			}
		}
		return m;
	}

	public FloorPlan normalizeRoomSizes()
	{
		FloorPlan nFP = new FloorPlan(this, new ArrayList<Room.RoomType>(roomTypes()));
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
		FloorPlan newFP = new FloorPlan(rb, new ArrayList<Room.RoomType>(roomTypes()));
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
		FloorPlan newFP = new FloorPlan(rb, new ArrayList<Room.RoomType>(roomTypes()));
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
		FloorPlan newFP = new FloorPlan(rb, new ArrayList<Room.RoomType>(roomTypes()));
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
		FloorPlan newFP = new FloorPlan(rb, new ArrayList<Room.RoomType>(roomTypes()));
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
		FloorPlan newFP = new FloorPlan(rb, new ArrayList<Room.RoomType>(roomTypes()));
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
		FloorPlan newFP = new FloorPlan(rb, new ArrayList<Room.RoomType>(roomTypes()));
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
		FloorPlan newFP = new FloorPlan(rb, new ArrayList<Room.RoomType>(roomTypes()));
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
		FloorPlan newFP = new FloorPlan(rb, new ArrayList<Room.RoomType>(roomTypes()));
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