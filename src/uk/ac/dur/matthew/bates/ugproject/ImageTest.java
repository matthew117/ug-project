package uk.ac.dur.matthew.bates.ugproject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageTest extends JPanel
{

	private static final long serialVersionUID = -3436039885124950224L;

	public ImageTest()
	{

	}

	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		split(g2d, 0, 0, g.getClipBounds().width - 1, g.getClipBounds().height - 1);
	}

	public void split(Graphics2D g, int x, int y, int width, int height)
	{
		if (width <= 40) return;
		if (height <= 40) return;
		g.drawRect(x, y, width, height);
		// split horizontally left
		if ((new Random()).nextBoolean())
		{
			split(g, x, y, width / 2, height);
		}
		// split horizontally right
		if ((new Random()).nextBoolean())
		{
			split(g, x + (width / 2), y, width / 2, height);
		}
		// split vertically down
		if ((new Random()).nextBoolean())
		{
			split(g, x, y + (height / 2), width, height / 2);
		}
		// split vertically up
		if ((new Random()).nextBoolean())
		{
			split(g, x, y - (height / 2), width, height / 2);
		}
	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("ImageTest");
		frame.add(new ImageTest());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
