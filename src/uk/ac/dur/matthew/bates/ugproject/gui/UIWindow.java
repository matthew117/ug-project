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
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.dur.matthew.bates.ugproject.model.FloorPlan;
import uk.ac.dur.matthew.bates.ugproject.model.Rect;

public class UIWindow extends JFrame
{
	private FloorPlanViewer fpViewer;
	private OptionsPanel optPanel;

	private FloorPlan floorPlan;
	private int userFloorPlanWidth = 70;
	private int userFloorPlanHeight = 50;
	
	private boolean showRoomIDs;
	private boolean showRoomAreas;

	private JCheckBox ckbShowRoomIDs;
	private JCheckBox ckbShowRoomAreas;
	private JTextField txtFloorPlanWidth;
	private JTextField txtFloorPlanHeight;
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

		floorPlan = new FloorPlan(userFloorPlanWidth, userFloorPlanHeight, new ArrayList<Double>(
				Arrays.asList(5.0, 10.0, 4.0, 2.0)));

		fpViewer = new FloorPlanViewer();
		optPanel = new OptionsPanel();

		optPanel.setLayout(new BoxLayout(optPanel, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("FloorPlan Width");
		label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
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

		ckbShowRoomIDs = new JCheckBox("Show Room IDs");
		ckbShowRoomIDs.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				showRoomIDs = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowRoomIDs);
		
		ckbShowRoomAreas = new JCheckBox("Show Room Areas");
		ckbShowRoomAreas.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				showRoomAreas = e.getStateChange() == ItemEvent.SELECTED;
				fpViewer.repaint();
			}
		});
		optPanel.add(ckbShowRoomAreas);

		contentPane.add(fpViewer, BorderLayout.CENTER);
		contentPane.add(optPanel, BorderLayout.EAST);

		setVisible(true);
	}

	protected void reloadFloorPlan()
	{
		System.out.println("Regerating Floor Plan");

		userFloorPlanWidth = Integer.parseInt(txtFloorPlanWidth.getText());
		userFloorPlanHeight = Integer.parseInt(txtFloorPlanHeight.getText());

		floorPlan = new FloorPlan(userFloorPlanWidth, userFloorPlanHeight, new ArrayList<Double>(
				Arrays.asList(1.0, 1.0 , 1.0, 1.0)));

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

			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(0xffcccccc));
			for (int x = scale + scale * 4; x < width; x += scale * 4)
			{
				g.drawLine(x, 0, x, height);
			}
			for (int y = scale + scale * 4; y < height; y += scale * 4)
			{
				g.drawLine(0, y, width, y);
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
				
				if (showRoomIDs)
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
				
				if (showRoomAreas)
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
					g.fillOval(midX + 13, midY + 13, 26, 26);
					g.setColor(new Color(0x9999CC));
					g.drawOval(midX + 13, midY + 13, 26, 26);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_OFF);
					g.setColor(Color.BLACK);
					g.drawString(areaLabel, midX - (strw >> 1) + 26, midY + (strh / 2) - 3 + 26);
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
