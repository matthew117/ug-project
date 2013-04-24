package uk.ac.dur.matthew.bates.ugproject.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PathConfig;
import uk.ac.dur.matthew.bates.ugproject.model.DoorConnection;
import uk.ac.dur.matthew.bates.ugproject.model.FloorPlan;
import uk.ac.dur.matthew.bates.ugproject.model.Line;
import uk.ac.dur.matthew.bates.ugproject.model.Node;
import uk.ac.dur.matthew.bates.ugproject.model.Point;
import uk.ac.dur.matthew.bates.ugproject.model.Rect;
import uk.ac.dur.matthew.bates.ugproject.model.Room;
import uk.ac.dur.matthew.bates.ugproject.model.Wall;
import uk.ac.dur.matthew.bates.ugproject.model.WallConnection;
import uk.ac.dur.matthew.bates.ugproject.model.Welder;

import com.google.common.base.Splitter;

public class UIWindow extends JFrame
{
	private FloorPlanViewer fpViewer;
	private OptionsPanel optPanel;

	private FloorPlan floorPlan;

	private int uFloorPlanWidth = 48;
	private int uFloorPlanHeight = 36;
	private List<Double> uDefinedRoomAreas;
	private boolean uShowMinorGridlines = true;
	private boolean uShowMajorGridlines = true;
	private boolean uShowRoomIDs = true;
	private boolean uShowRoomAreas = true;
	private boolean uShowPossibleDoorLocations = true;
	private boolean uShowWallNormals = false;
	private boolean uShowRoomTypes = true;
	private boolean uShowDoorRadius = true;
	private boolean uShowPathNodes = true;
	private boolean uShowWelders = true;
	private boolean uShowTessellation = true;

	private JTextField txtFloorPlanWidth;
	private JTextField txtFloorPlanHeight;
	private JTextField txtFloorPlanAreas;
	private JCheckBox ckbShowMinorGridlines;
	private JCheckBox ckbShowMajorGridlines;
	private JCheckBox ckbShowRoomIDs;
	private JCheckBox ckbShowRoomAreas;
	private JCheckBox ckbShowPossibleDoorLocations;
	private JCheckBox ckbShowWallNormals;
	private JCheckBox ckbShowRoomTypes;
	private JCheckBox ckbShowDoorRadius;
	private JCheckBox ckbShowPathNodes;
	private JCheckBox ckbShowWelders;
	private JCheckBox ckbShowTessellation;
	private JButton btnGenerateAmnesiaMap;

	public UIWindow()
	{
		super("Procedural Generation of Buildings using HPL2");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 800);
		setLocation(1200, 10);
		setMinimumSize(new Dimension(800, 600));

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		floorPlan = generateFloorPlan();

		fpViewer = new FloorPlanViewer();
		optPanel = new OptionsPanel();

		optPanel.setLayout(new BoxLayout(optPanel, BoxLayout.Y_AXIS));

		optPanel.add(new JLabel("FloorPlan Width"));
		txtFloorPlanWidth = new JTextField("80", 5);
		txtFloorPlanWidth.setMaximumSize(new Dimension(txtFloorPlanWidth.getPreferredSize().width, txtFloorPlanWidth
				.getPreferredSize().height));
		txtFloorPlanWidth.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				reloadFloorPlan();
			}
		});
		optPanel.add(txtFloorPlanWidth);

		optPanel.add(new JLabel("FloorPlan Height"));
		txtFloorPlanHeight = new JTextField("50", 5);
		txtFloorPlanHeight.setMaximumSize(new Dimension(txtFloorPlanHeight.getPreferredSize().width, txtFloorPlanHeight
				.getPreferredSize().height));
		txtFloorPlanHeight.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				reloadFloorPlan();
			}
		});
		optPanel.add(txtFloorPlanHeight);

		optPanel.add(new JLabel("FloorPlan Areas"));
		txtFloorPlanAreas = new JTextField(floorPlan.areas().toString()
				.substring(1, floorPlan.areas().toString().length() - 1).replaceAll(",", ""), 20);
		txtFloorPlanAreas.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtFloorPlanAreas.getPreferredSize().height));
		txtFloorPlanAreas.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				reloadFloorPlan();
			}
		});
		optPanel.add(txtFloorPlanAreas);

		ckbShowMinorGridlines = new JCheckBox("Show Minor Gridlines");
		ckbShowMinorGridlines.setSelected(uShowMinorGridlines);
		ckbShowMinorGridlines.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowMinorGridlines = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowMinorGridlines);

		ckbShowMajorGridlines = new JCheckBox("Show Major Gridlines");
		ckbShowMajorGridlines.setSelected(uShowMajorGridlines);
		ckbShowMajorGridlines.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowMajorGridlines = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowMajorGridlines);

		ckbShowRoomIDs = new JCheckBox("Show Room IDs");
		ckbShowRoomIDs.setSelected(uShowRoomIDs);
		ckbShowRoomIDs.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowRoomIDs = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowRoomIDs);

		ckbShowRoomAreas = new JCheckBox("Show Room Areas");
		ckbShowRoomAreas.setSelected(uShowRoomAreas);
		ckbShowRoomAreas.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowRoomAreas = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowRoomAreas);

		ckbShowPossibleDoorLocations = new JCheckBox("Show Possible Door Locations");
		ckbShowPossibleDoorLocations.setSelected(uShowPossibleDoorLocations);
		ckbShowPossibleDoorLocations.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowPossibleDoorLocations = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowPossibleDoorLocations);

		ckbShowWallNormals = new JCheckBox("Show Wall Normals");
		ckbShowWallNormals.setSelected(uShowWallNormals);
		ckbShowWallNormals.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowWallNormals = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowWallNormals);

		ckbShowRoomTypes = new JCheckBox("Show Room Types");
		ckbShowRoomTypes.setSelected(uShowRoomTypes);
		ckbShowRoomTypes.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowRoomTypes = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowRoomTypes);

		ckbShowDoorRadius = new JCheckBox("Show Door Radius");
		ckbShowDoorRadius.setSelected(uShowDoorRadius);
		ckbShowDoorRadius.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowDoorRadius = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowDoorRadius);

		ckbShowPathNodes = new JCheckBox("Show Path Nodes");
		ckbShowPathNodes.setSelected(uShowPathNodes);
		ckbShowPathNodes.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowPathNodes = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowPathNodes);

		ckbShowWelders = new JCheckBox("Show Welders");
		ckbShowWelders.setSelected(uShowWelders);
		ckbShowWelders.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowWelders = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowWelders);

		ckbShowTessellation = new JCheckBox("Show Tessellation");
		ckbShowTessellation.setSelected(uShowTessellation);
		ckbShowTessellation.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				uShowTessellation = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowTessellation);
		
		btnGenerateAmnesiaMap = new JButton("Generate HPL2 Map");
		btnGenerateAmnesiaMap.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MapGenerator mg = new MapGenerator(floorPlan);
				mg.writeToFile(PathConfig.AMNESIA_RESOURCES_DIR + "custom_stories/UGProject/maps/map01.map");
			}
		});
		optPanel.add(btnGenerateAmnesiaMap);

		contentPane.add(fpViewer, BorderLayout.CENTER);
		contentPane.add(optPanel, BorderLayout.EAST);

		setVisible(true);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	private FloorPlan generateFloorPlan()
	{
		List<Double> roomAreas = new ArrayList<Double>();
		Random r = new Random();
		for (int i = 1; i <= 9; i++)
		{
			if (i > 6) roomAreas.add((double) (r.nextInt(2) + 3));
			else roomAreas.add((double) (r.nextInt(6) + 5));
		}
		Collections.shuffle(roomAreas);
		return new FloorPlan(uFloorPlanWidth, uFloorPlanHeight, (ArrayList<Double>) roomAreas);
	}

	protected void reloadFloorPlan()
	{
		System.out.println("Regenerating Floor Plan");

		uFloorPlanWidth = Integer.parseInt(txtFloorPlanWidth.getText());
		uFloorPlanHeight = Integer.parseInt(txtFloorPlanHeight.getText());
		uDefinedRoomAreas = new ArrayList<Double>();
		String areaString = txtFloorPlanAreas.getText();
		for (String s : Splitter.on(' ').split(areaString))
		{
			uDefinedRoomAreas.add(Double.parseDouble(s));
		}

		floorPlan = new FloorPlan(uFloorPlanWidth, uFloorPlanHeight, (ArrayList<Double>) uDefinedRoomAreas);

		fpViewer.repaint();
	}

	class FloorPlanViewer extends JPanel
	{
		@Override
		public void paint(Graphics context)
		{
			Graphics2D g = (Graphics2D) context;

			int width = getWidth();
			int height = getHeight();

			g.clearRect(0, 0, width, height);

			int centerX = width >> 1;
			int centerY = height >> 1;

			int floorplanWidth = floorPlan.width();
			int floorplanHeight = floorPlan.height();

			double widthRatio = (double) (width - (width / floorplanWidth)) / (double) floorplanWidth;
			double heightRatio = (double) (height - (height / floorplanHeight)) / (double) floorplanHeight;
			double scaleFactor = heightRatio < widthRatio ? heightRatio : widthRatio;
			int scale = (int) scaleFactor;

			int displayWidth = (int) (floorplanWidth * scaleFactor);
			int displayHeight = (int) (floorplanHeight * scaleFactor);

			if (uShowMinorGridlines)
			{
				// Draw Grid Lines
				g.setStroke(new BasicStroke(1));
				g.setColor(new Color(0xffd0d0d0));
				for (int x = scale; x < width; x += scale)
				{
					g.drawLine(x, 0, x, height);
				}
				for (int y = scale; y < height; y += scale)
				{
					g.drawLine(0, y, width, y);
				}
			}

			if (uShowMajorGridlines)
			{
				g.setStroke(new BasicStroke(2));
				g.setColor(new Color(0xffc0c0c0));
				for (int x = scale + scale * 4; x < width; x += scale * 4)
				{
					g.drawLine(x, 0, x, height);
				}
				for (int y = scale + scale * 4; y < height; y += scale * 4)
				{
					g.drawLine(0, y, width, y);
				}
			}

			if (uShowPathNodes)
			{
				Queue<Node> Q = new ArrayBlockingQueue<Node>(floorPlan.rooms().size());
				Node v = floorPlan.pathRoot();
				Set<Node> m = new HashSet<Node>();
				Q.add(v);
				m.add(v);
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(2));
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				while (!Q.isEmpty())
				{
					Node t = Q.poll();

					int tx = (int) (t.x * scale) + scale;
					int ty = (int) (t.y * scale) + scale;

					g.drawOval(tx - 10, ty - 10, 20, 20);
					for (Node e : t.edges())
					{
						int ex = (int) (e.x * scale) + scale;
						int ey = (int) (e.y * scale) + scale;

						g.drawLine(tx, ty, ex, ey);
						if (!m.contains(e))
						{
							m.add(e);
							Q.add(e);
						}
					}
				}
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			}

			g.setStroke(new BasicStroke(1));
			g.setColor(Color.BLUE);
			g.drawLine(scale, 0, scale, height);
			g.setColor(Color.RED);
			g.drawLine(0, scale, width, scale);

			g.setStroke(new BasicStroke(2));
			g.setColor(Color.BLACK);
			for (int i = 0; i < floorPlan.roomBounds().size(); i++)
			{
				Rect r = floorPlan.roomBounds().get(i);
				int x = (int) (r.x * scale) + scale;
				int y = (int) (r.y * scale) + scale;
				int w = (int) (r.width * scale);
				int h = (int) (r.height * scale);
				int midX = (x + w) - (w >> 1);
				int midY = (y + h) - (h >> 1);

				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(2));
				g.drawRect(x, y, w, h);

				Font defaultFont = g.getFont();

				if (uShowRoomIDs)
				{
					g.setFont(defaultFont.deriveFont(20F));
					String areaLabel = "" + i;
					Rectangle2D strBounds = g.getFontMetrics().getStringBounds(areaLabel, g);
					int strw = strBounds.getBounds().width;
					int strh = strBounds.getBounds().height;

					g.setStroke(new BasicStroke(3));
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(new Color(0xEBB0B0));
					g.fillOval(midX - 20, midY - 20, 40, 40);
					g.setColor(new Color(0xCC9999));
					g.drawOval(midX - 20, midY - 20, 40, 40);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
					g.setColor(Color.BLACK);
					g.drawString(areaLabel, midX - (strw >> 1), midY + (strh / 2) - 5);
				}

				if (uShowRoomAreas)
				{
					g.setFont(defaultFont);
					String areaLabel = "" + floorPlan.areas().get(i);
					Rectangle2D strBounds = g.getFontMetrics().getStringBounds(areaLabel, g);
					int strw = strBounds.getBounds().width;
					int strh = strBounds.getBounds().height;

					g.setStroke(new BasicStroke(3));
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(new Color(0xB0B0EB));
					g.fillOval(midX - 13 + (uShowRoomIDs ? 22 : 0), midY - 13 + (uShowRoomIDs ? 22 : 0), 26, 26);
					g.setColor(new Color(0x9999CC));
					g.drawOval(midX - 13 + (uShowRoomIDs ? 22 : 0), midY - 13 + (uShowRoomIDs ? 22 : 0), 26, 26);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
					g.setColor(Color.BLACK);
					g.drawString(areaLabel, midX - (strw >> 1) + (uShowRoomIDs ? 22 : 0), midY + (strh / 2) - 3
							+ (uShowRoomIDs ? 22 : 0));
				}

				g.setFont(defaultFont);
			}

			if (uShowWallNormals)
			{
				g.setStroke(new BasicStroke(1));
				g.setColor(Color.BLACK);
				for (Wall l : floorPlan.walls())
				{
					Point p = l.midpoint();
					int px = p.x * scale + scale;
					int py = p.y * scale + scale;
					int qx = px + (int) (20 * Math.cos(Math.toRadians(l.orientation() - 90)));
					int qy = py + (int) (20 * Math.sin(Math.toRadians(l.orientation() - 90)));
					int ux = qx - (int) (5 * Math.cos(Math.toRadians(l.orientation() - 135)));
					int uy = qy - (int) (5 * Math.sin(Math.toRadians(l.orientation() - 135)));
					int vx = qx - (int) (5 * Math.cos(Math.toRadians(l.orientation() - 45)));
					int vy = qy - (int) (5 * Math.sin(Math.toRadians(l.orientation() - 45)));
					g.drawLine(px, py, qx, qy);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.drawLine(qx, qy, ux, uy);
					g.drawLine(qx, qy, vx, vy);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				}
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(2));
			}

			if (uShowPossibleDoorLocations)
			{
				g.setStroke(new BasicStroke(1));
				for (WallConnection w : floorPlan.wallConnections())
				{
					Line c = w.overlap();
					int x1 = c.p.x * scale + scale;
					int y1 = c.p.y * scale + scale;
					int x2 = c.q.x * scale + scale;
					int y2 = c.q.y * scale + scale;
					if (w instanceof DoorConnection)
					{
						if (c.isHorizontal())
						{
							Wall a = w.frontFacing();
							if (a.orientation() == Wall.NORTH)
							{
								g.setColor(new Color(0x550000ff, true));
								g.fillRect(x1, y1 - 5, x2 - x1 - 1, 5);
								g.setColor(new Color(0x44ff0000, true));
								g.fillRect(x1, y1, x2 - x1 - 1, 5);
							}
							else
							{
								g.setColor(new Color(0x550000ff, true));
								g.fillRect(x1, y1, x2 - x1 - 1, 5);
								g.setColor(new Color(0x55ff0000, true));
								g.fillRect(x1, y1 - 5, x2 - x1 - 1, 5);
							}
							g.setColor(Color.BLACK);
							g.drawRect(x1, y1 - 5, x2 - x1 - 1, 9);
						}
						if (c.isVertical())
						{
							Wall a = w.frontFacing();
							if (a.orientation() == Wall.EAST)
							{
								g.setColor(new Color(0x550000ff, true));
								g.fillRect(x1, y1, 5, y2 - y1 - 1);
								g.setColor(new Color(0x55ff0000, true));
								g.fillRect(x1 - 5, y1, 5, y2 - y1 - 1);
							}
							else
							{
								g.setColor(new Color(0x550000ff, true));
								g.fillRect(x1 - 5, y1, 5, y2 - y1 - 1);
								g.setColor(new Color(0x55ff0000, true));
								g.fillRect(x1, y1, 5, y2 - y1 - 1);
							}
							g.setColor(Color.BLACK);
							g.drawRect(x1 - 5, y1, 9, y2 - y1 - 1);
						}
					}
					else
					{
						g.setColor(new Color(0x66333333, true));
						if (c.isHorizontal())
						{
							g.fillRect(x1, y1 - 5, x2 - x1, 9);
						}
						if (c.isVertical())
						{
							g.fillRect(x1 - 5, y1, 9, y2 - y1);
						}
					}
				}
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.BLACK);
			}

			if (uShowDoorRadius)
			{
				for (WallConnection wc : floorPlan.wallConnections())
				{
					if (wc instanceof DoorConnection)
					{
						DoorConnection dw = (DoorConnection) wc;
						Wall w = dw.backFacing();
						Line dl = dw.doorPlacement();

						g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						Point pm = dl.midpoint();
						// g.setColor(new Color(116, 39, 158, 100));
						Arc2D.Double semiCircle;
						if (w.orientation() == Wall.NORTH || w.orientation() == Wall.SOUTH)
						{
							semiCircle = new Arc2D.Double(pm.x * scale - (int) (1.75 * scaleFactor) + 1.25 * scale,
									pm.y * scale - (int) (1.75 * scaleFactor), 5.5 * scale, 5.5 * scale,
									w.orientation(), Math.abs(w.orientation() - 90), Arc2D.PIE);
							g.setColor(Color.BLACK);
							g.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
							if (w.orientation() == Wall.NORTH)
							{
								g.drawLine(pm.x * scale + scale + (int) (1.25 * scaleFactor), pm.y * scale + scale,
										pm.x * scale + scale + (int) (1.25 * scaleFactor), pm.y * scale + scale
												- (int) (scaleFactor * 2.75));
							}
							else
							{
								g.drawLine(pm.x * scale + scale + (int) (1.25 * scaleFactor), pm.y * scale + scale,
										pm.x * scale + scale + (int) (1.25 * scaleFactor), pm.y * scale + scale
												+ (int) (scaleFactor * 2.75));
							}
						}
						else
						{
							semiCircle = new Arc2D.Double(pm.x * scale - (int) (1.75 * scaleFactor), pm.y * scale
									- (int) (1.75 * scaleFactor) + 1.25 * scale, 5.5 * scale, 5.5 * scale,
									90 - w.orientation(), Math.abs(90 - (w.orientation() - 90)), Arc2D.PIE);
							g.setColor(Color.BLACK);
							g.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
							if (w.orientation() == Wall.EAST)
							{
								g.drawLine(pm.x * scale + scale, pm.y * scale + scale + (int) (1.25 * scaleFactor),
										pm.x * scale + scale + (int) (scaleFactor * 2.75), pm.y * scale + scale
												+ (int) (1.25 * scaleFactor));
							}
							else
							{
								g.drawLine(pm.x * scale + scale, pm.y * scale + scale + (int) (1.25 * scaleFactor),
										pm.x * scale + scale - (int) (scaleFactor * 2.75), pm.y * scale + scale
												+ (int) (1.25 * scaleFactor));
							}
						}
						g.setColor(new Color(0x66333333, true));
						g.fill(semiCircle);
						g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
					}
				}
			}

			if (uShowRoomTypes)
			{
				g.setColor(Color.BLACK);
				for (Room r : floorPlan.rooms())
				{
					int x = (int) (r.x * scale) + scale;
					int y = (int) (r.y * scale) + scale;
					int w = (int) (r.width * scale);
					int h = (int) (r.height * scale);
					int midX = (x + w) - (w >> 1);
					int midY = (y + h) - (h >> 1);
					String roomName = r.type().toString();
					Rectangle2D strBounds = g.getFontMetrics().getStringBounds(roomName, g);
					int strw = strBounds.getBounds().width;
					int strh = strBounds.getBounds().height;

					g.drawString(roomName, midX - (strw >> 1), midY
							- ((uShowRoomAreas || uShowRoomIDs) ? 25 : (strh >> 1)));
				}
			}

			if (uShowTessellation)
			{
				for (Wall t : floorPlan.tessellation())
				{
					g.setStroke(new BasicStroke(1));
					g.setColor(Color.BLACK);
					int px = t.p.x * scale + scale;
					int py = t.p.y * scale + scale;
					int qx = t.q.x * scale + scale;
					int qy = t.q.y * scale + scale;
					int w = qx - px;
					int h = qy - py;

					switch (t.orientation())
					{
					case Wall.EAST:
						g.drawRect(px - 10, py, 10, h);
						break;
					case Wall.NORTH:
						g.drawRect(px, py - 10, w, 10);
						break;
					case Wall.SOUTH:
						g.drawRect(px, py, w, 10);
						break;
					case Wall.WEST:
						g.drawRect(px, py, 10, h);
						break;

					default:
						break;
					}
				}
			}

			if (uShowWelders)
			{
				for (Welder welder : floorPlan.welders())
				{
					double w = scaleFactor;
					double h = w;
					double x = welder.location().x * scale + scale - (w * 0.5);
					double y = welder.location().y * scale + scale - (h * 0.5);
					int start = 360 - welder.orientation();
					int extent = 90;

					Arc2D.Double shape = new Arc2D.Double(x, y, w, h, start, extent, Arc2D.PIE);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(Color.WHITE);
					g.fill(shape);
					g.setColor(Color.BLACK);
					g.setStroke(new BasicStroke(1));
					shape = new Arc2D.Double(x, y, w, h, start, extent, Arc2D.OPEN);
					g.draw(shape);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				}
			}

			for (WallConnection wc : floorPlan.wallConnections())
			{
				if (wc instanceof DoorConnection)
				{
					DoorConnection dw = (DoorConnection) wc;
					Line dl = dw.doorPlacement();

					g.setStroke(new BasicStroke(10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
					g.setColor(Color.BLACK);
					g.drawLine(dl.p.x * scale + scale, dl.p.y * scale + scale, dl.q.x * scale + scale, dl.q.y * scale
							+ scale);
					g.setStroke(new BasicStroke(1));
				}
			}

		}

	}

	class OptionsPanel extends JPanel
	{
		public OptionsPanel()
		{
			setBackground(Color.LIGHT_GRAY);
		}
	}

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new UIWindow();
			}
		});
	}
}