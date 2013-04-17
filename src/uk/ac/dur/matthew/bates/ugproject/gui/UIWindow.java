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
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.dur.matthew.bates.ugproject.model.FloorPlan;
import uk.ac.dur.matthew.bates.ugproject.model.Line;
import uk.ac.dur.matthew.bates.ugproject.model.Point;
import uk.ac.dur.matthew.bates.ugproject.model.Rect;
import uk.ac.dur.matthew.bates.ugproject.model.Wall;

import com.google.common.base.Splitter;

public class UIWindow extends JFrame
{
	private FloorPlanViewer fpViewer;
	private OptionsPanel optPanel;

	private FloorPlan floorPlan;

	private int uFloorPlanWidth = 70;
	private int uFloorPlanHeight = 50;
	private List<Double> uDefinedRoomAreas;
	private boolean uShowMinorGridlines = true;
	private boolean uShowMajorGridlines = true;
	private boolean uShowRoomIDs = true;
	private boolean uShowRoomAreas = true;
	private boolean uShowPossibleDoorLocations = true;
	private boolean uShowWallNormals = true;

	private JTextField txtFloorPlanWidth;
	private JTextField txtFloorPlanHeight;
	private JTextField txtFloorPlanAreas;
	private JCheckBox ckbShowMinorGridlines;
	private JCheckBox ckbShowMajorGridlines;
	private JCheckBox ckbShowRoomIDs;
	private JCheckBox ckbShowRoomAreas;
	private JCheckBox ckbShowPossibleDoorLocations;
	private JCheckBox ckbShowWallNormals;

	private JTextField txtRoomAreas;

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
		txtFloorPlanWidth = new JTextField("70", 5);
		txtFloorPlanWidth.setMaximumSize(new Dimension(txtFloorPlanWidth.getPreferredSize().width,
				txtFloorPlanWidth.getPreferredSize().height));
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
		txtFloorPlanHeight.setMaximumSize(new Dimension(
				txtFloorPlanHeight.getPreferredSize().width,
				txtFloorPlanHeight.getPreferredSize().height));
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
		txtFloorPlanAreas.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtFloorPlanAreas
				.getPreferredSize().height));
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
		ckbShowMinorGridlines.setSelected(true);
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
		ckbShowMajorGridlines.setSelected(true);
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
		ckbShowRoomIDs.setSelected(true);
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
		ckbShowRoomAreas.setSelected(true);
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
		ckbShowPossibleDoorLocations.setSelected(true);
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
		ckbShowWallNormals.setSelected(true);
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

		contentPane.add(fpViewer, BorderLayout.CENTER);
		contentPane.add(optPanel, BorderLayout.EAST);

		setVisible(true);
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
		System.out.println("Regerating Floor Plan");

		uFloorPlanWidth = Integer.parseInt(txtFloorPlanWidth.getText());
		uFloorPlanHeight = Integer.parseInt(txtFloorPlanHeight.getText());
		uDefinedRoomAreas = new ArrayList<Double>();
		String areaString = txtFloorPlanAreas.getText();
		for (String s : Splitter.on(' ').split(areaString))
		{
			uDefinedRoomAreas.add(Double.parseDouble(s));
		}

		floorPlan = new FloorPlan(uFloorPlanWidth, uFloorPlanHeight,
				(ArrayList<Double>) uDefinedRoomAreas);

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

			double widthRatio = (double) (width - (width / floorplanWidth))
					/ (double) floorplanWidth;
			double heightRatio = (double) (height - (height / floorplanHeight))
					/ (double) floorplanHeight;
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
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(new Color(0xEBB0B0));
					g.fillOval(midX - 20, midY - 20, 40, 40);
					g.setColor(new Color(0xCC9999));
					g.drawOval(midX - 20, midY - 20, 40, 40);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_OFF);
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
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(new Color(0xB0B0EB));
					g.fillOval(midX - 13 + (uShowRoomIDs ? 22 : 0), midY - 13
							+ (uShowRoomIDs ? 22 : 0), 26, 26);
					g.setColor(new Color(0x9999CC));
					g.drawOval(midX - 13 + (uShowRoomIDs ? 22 : 0), midY - 13
							+ (uShowRoomIDs ? 22 : 0), 26, 26);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_OFF);
					g.setColor(Color.BLACK);
					g.drawString(areaLabel, midX - (strw >> 1) + (uShowRoomIDs ? 22 : 0), midY
							+ (strh / 2) - 3 + (uShowRoomIDs ? 22 : 0));
				}

				if (uShowPossibleDoorLocations)
				{
					g.setStroke(new BasicStroke(1));
					for (Line l : floorPlan.possibleDoorLocations())
					{
						int x1 = l.p.x * scale + scale;
						int y1 = l.p.y * scale + scale;
						int x2 = l.q.x * scale + scale;
						int y2 = l.q.y * scale + scale;
						if (l.isHorizontal())
						{
							g.drawRect(x1, y1 - 5, x2 - x1 - 1, 9);
						}
						if (l.isVertical())
						{
							g.drawRect(x1 - 5, y1, 9, y2 - y1 - 1);
						}
					}
					g.setStroke(new BasicStroke(2));
				}

				if (uShowWallNormals)
				{
					g.setStroke(new BasicStroke(1));
					g.setColor(new Color(0x00AAFF));
					for (Wall l : floorPlan.walls())
					{
						Point p = l.midpoint();
						int px = p.x * scale + scale;
						int py = p.y * scale + scale;
						g.drawLine(px, py,
								px + (int) (20 * Math.cos(Math.toRadians(l.angle() - 90))), py
										+ (int) (20 * Math.sin(Math.toRadians(l.angle() - 90))));
					}
					g.setColor(Color.BLACK);
					g.setStroke(new BasicStroke(2));
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