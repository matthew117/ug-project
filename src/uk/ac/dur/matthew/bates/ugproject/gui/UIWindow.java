package uk.ac.dur.matthew.bates.ugproject.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UIWindow extends JFrame
{
	private FloorPlanViewer fpViewer;
	private OptionsPanel optPanel;

	private Color sColor = Color.BLACK;;

	private JCheckBox showWallOrientations;

	public UIWindow()
	{
		super("Procedural Generation of Buildings");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 800);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		fpViewer = new FloorPlanViewer();
		optPanel = new OptionsPanel();

		optPanel.setLayout(new BoxLayout(optPanel, BoxLayout.Y_AXIS));

		showWallOrientations = new JCheckBox("Show Wall Orientation Information");
		showWallOrientations.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					sColor = Color.RED;
				}
				else
				{
					sColor = Color.BLUE;
				}
				fpViewer.repaint();
			}
		});
		optPanel.add(showWallOrientations);

		contentPane.add(fpViewer, BorderLayout.CENTER);
		contentPane.add(optPanel, BorderLayout.EAST);

		setVisible(true);
	}

	class FloorPlanViewer extends JPanel
	{
		@Override
		public void paint(Graphics context)
		{
			Graphics2D g = (Graphics2D) context;

			int width = getWidth();
			int height = getHeight();

			int centerX = width >> 1;
			int centerY = height >> 1;

			g.setStroke(new BasicStroke(1.0f));
			g.setColor(Color.LIGHT_GRAY);
			for (int x = 10; x < width; x += 10)
			{
				g.drawLine(x, 0, x, height);
			}
			for (int y = 10; y < height; y += 10)
			{
				g.drawLine(0, y, width, y);
			}
			
			g.setStroke(new BasicStroke(2.0f));
			g.setColor(Color.LIGHT_GRAY);
			for (int x = 50; x < width; x += 50)
			{
				g.drawLine(x, 0, x, height);
			}
			for (int y = 50; y < height; y += 50)
			{
				g.drawLine(0, y, width, y);
			}

			g.setColor(sColor);
			g.fillRect(centerX - centerX / 2, centerY - centerY / 2, centerX, centerY);
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
