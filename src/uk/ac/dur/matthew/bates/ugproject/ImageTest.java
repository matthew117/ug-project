package uk.ac.dur.matthew.bates.ugproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.ac.dur.matthew.bates.ugproject.generators.Squarify;
import uk.ac.dur.matthew.bates.ugproject.model.Rect;
import uk.ac.dur.matthew.bates.ugproject.model.RectF;

public class ImageTest extends JPanel
{

	private static final long serialVersionUID = -3436039885124950224L;

	public ImageTest()
	{

	}

	public boolean isColliding(Rectangle box1, Rectangle box2)
	{
		// if (!((box1.x + box1.width) >= box2.x)) { return false; }
		// if (!(box1.x <= (box2.x + box2.width))) { return false; }
		// if (!((box1.y - box1.height) <= box2.y)) { return false; }
		// if (!(box1.y >= (box2.y - box2.height))) { return false; }

		int left1 = box1.x;
		int right1 = box1.x + box1.width;
		int top1 = box1.y;
		int bottom1 = box1.y + box1.height;

		int left2 = box2.x;
		int right2 = box2.x + box2.width;
		int top2 = box2.y;
		int bottom2 = box2.y + box2.height;

		int x1A = box1.x;
		int y1A = box1.y;
		int x2A = box1.x + box1.width;
		int y2A = box1.y + box1.height;

		int x1B = box2.x;
		int y1B = box2.y;
		int x2B = box2.x + box2.width;
		int y2B = box2.y + box2.height;

		int sum = 0;

		if (x1A == x1B && y1A == y1B) sum++;
		if (x1A == x1B && y2A == y2B) sum++;
		if (x2A == x2B && y1A == y1B) sum++;
		if (x2A == x2B && y2A == y2B) sum++;

		if (x2A == x1B && y2A == y1B) sum++;
		if (x1A == x2B && y1A == y2B) sum++;

		if (x1A == x2B && y2A == y1B) sum++;
		if (x2A == x1B && y1A == y2B) sum++;

		if (sum == 1) return false;

		return left1 <= right2 && left2 <= right1 && top1 <= bottom2 && top2 <= bottom1;

		// return true;
	}

	public void paint(Graphics gContext)
	{
		Graphics2D g = (Graphics2D) gContext;

		Random r = new Random(System.currentTimeMillis());
		// Random r = new Random(2L);
		List<Double> roomAreas = Arrays.asList((double) (r.nextInt(6) + 2),
				(double) (r.nextInt(6) + 2), (double) (r.nextInt(6) + 2),
				(double) (r.nextInt(6) + 2), (double) (r.nextInt(6) + 2),
				(double) (r.nextInt(6) + 2), (double) (r.nextInt(6) + 2),
				(double) (r.nextInt(6) + 2), (double) (r.nextInt(6) + 2),
				(double) (r.nextInt(6) + 2), (double) (r.nextInt(6) + 2),
				(double) (r.nextInt(6) + 2), (double) (r.nextInt(6) + 2));
		roomAreas = Arrays.asList(21.0, 10.0, 9.0, 8.0, 3.0, 2.3, 14.0, 7.0, 10.0);
		
		List<Rect> rooms = Squarify.squarify(roomAreas, new RectF(0, 0, getWidth(), getHeight()));

		int roomNum = 0;
		
		for (Rect room : rooms)
		{
			int x1 = room.x;
			int y1 = room.y;
			int x2 = room.x + room.width;
			int y2 = room.y + room.height;

			int width = room.width;
			int height = room.height;

			g.setColor(new Color(0x00cccccc));

			double xf1 = x1;
			double yf1 = y1;
			double xf2 = x2;
			double yf2 = y2;

			double widthf = xf2 - xf1;
			double heightf = yf2 - yf1;

			if (Math.max(widthf, heightf) / Math.min(widthf, heightf) > 2)
				g.setColor(new Color(0x00AAAAAA));

			g.fillRect(x1, y1, width, height);
			g.setColor(new Color(0x00000000));
			g.drawRect(x1, y1, width, height);

			// ------------------------------------------------------------------------------------
			// DRAW COORDINATE STRINGS
			// ------------------------------------------------------------------------------------

			String coordStr = String.format("(%d,%d)", x1, y1);
			Rectangle2D strBounds = g.getFontMetrics().getStringBounds(coordStr, g);
			int stringWidth = (int) strBounds.getWidth();
			int stringHeight = (int) strBounds.getHeight();
			g.drawString(coordStr, x1 + 5, y1 + stringHeight);

			coordStr = String.format("(%d,%d)", x1, y2);
			strBounds = g.getFontMetrics().getStringBounds(coordStr, g);
			stringWidth = (int) strBounds.getWidth();
			stringHeight = (int) strBounds.getHeight();
			g.drawString(coordStr, x1 + 5, y2 - 5);

			coordStr = String.format("(%d,%d)", x2, y1);
			strBounds = g.getFontMetrics().getStringBounds(coordStr, g);
			stringWidth = (int) strBounds.getWidth();
			stringHeight = (int) strBounds.getHeight();
			g.drawString(coordStr, x2 - stringWidth - 5, y1 + stringHeight);
			//
			coordStr = String.format("(%d,%d)", x2, y2);
			strBounds = g.getFontMetrics().getStringBounds(coordStr, g);
			stringWidth = (int) strBounds.getWidth();
			stringHeight = (int) strBounds.getHeight();
			g.drawString(coordStr, x2 - stringWidth - 5, y2 - 5);

			String label = "" + roomAreas.get(roomNum++).intValue();
			strBounds = g.getFontMetrics().getStringBounds(label, g);
			stringWidth = (int) strBounds.getWidth();
			stringHeight = (int) strBounds.getHeight();
			g.drawString(label, (x1 + width / 2) - (stringWidth / 2), (y1 + height / 2)
					+ (stringHeight / 2.5f));

			// ====================================================================================
		}

//		for (int i = 0; i < xs.size(); i += 4)
//		{
//			for (int j = i + 4; j < xs.size(); j += 4)
//			{
//				int x1A = xs.get(i).intValue();
//				int y1A = xs.get(i + 1).intValue();
//				int x2A = xs.get(i + 2).intValue();
//				int y2A = xs.get(i + 3).intValue();
//
//				int widthA = x2A - x1A;
//				int heightA = y2A - y1A;
//
//				Rectangle roomA = new Rectangle(x1A, y1A, widthA, heightA);
//
//				int x1B = xs.get(j).intValue();
//				int y1B = xs.get(j + 1).intValue();
//				int x2B = xs.get(j + 2).intValue();
//				int y2B = xs.get(j + 3).intValue();
//
//				int widthB = x2B - x1B;
//				int heightB = y2B - y1B;
//
//				Rectangle roomB = new Rectangle(x1B, y1B, widthB, heightB);
//
//				int roomNumA = i / 4;
//				int roomNumB = j / 4;
//
//				if (isColliding(roomA, roomB))
//				{
//					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//							RenderingHints.VALUE_ANTIALIAS_ON);
//					g.setColor(Color.BLACK);
//					g.drawOval((x1A + widthA / 2) - 10, (y2A - heightA / 2) - 10, 20, 20);
//					g.drawOval((x1B + widthB / 2) - 10, (y2B - heightB / 2) - 10, 20, 20);
//					g.setColor(new Color(0x00888888));
//					g.drawLine(x1A + widthA / 2, y2A - heightA / 2, x1B + widthB / 2, y2B - heightB
//							/ 2);
//				}
//			}
//		}
//
//		for (int i = 0; i < walls.size(); i++)
//		{
//			for (int j = i + 1; j < walls.size(); j++)
//			{
//				Line2D.Double lineA;
//				Line2D.Double lineB;
//				Line2D.Double lineC = walls.get(i);
//				Line2D.Double lineD = walls.get(j);
//
//				Line lnA = new Line((int) lineC.x1, (int) lineC.y1, (int) lineC.x2, (int) lineC.y2);
//				Line lnB = new Line((int) lineD.x1, (int) lineD.y1, (int) lineD.x2, (int) lineD.y2);
//				Line lnO = Line.overlap(lnA, lnB);
//
//				if (Line2D.linesIntersect(lineC.getX1(), lineC.getY1(), lineC.getX2(),
//						lineC.getY2(), lineD.getX1(), lineD.getY1(), lineD.getX2(), lineD.y2))
//				{
//
//					if (lnO != null && lnO.length() > 50)
//					{
//						System.out.println(lnA);
//						System.out.println(lnB);
//						System.out.println(lnO);
//						System.out.println();
//
//						g.setColor(new Color(0x55FF0000, true));
//						g.setStroke(new BasicStroke(5.0f));
//						double x1 = lnA.p.x;
//						double y1 = lnA.p.y;
//						double x2 = lnA.q.x;
//						double y2 = lnA.q.y;
//
//						g.drawLine(lnO.p.x, lnO.p.y, lnO.q.x, lnO.q.y);
//
//						// double midpointXA = (x1 + x2) / 2.0;
//						// double midpointYA = (y1 + y2) / 2.0;
//						// g.setColor(Color.MAGENTA);
//						// g.setStroke(new BasicStroke());
//						// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//						// RenderingHints.VALUE_ANTIALIAS_ON);
//						// g.fillOval(lnO.midpoint().x - 5, lnO.midpoint().y - 5, 10, 10);
//
//						g.setColor(new Color(0x550000FF, true));
//						g.setStroke(new BasicStroke(5.0f));
//						g.drawLine(lnO.p.x, lnO.p.y, lnO.q.x, lnO.q.y);
//						x1 = lnB.p.x;
//						y1 = lnB.p.y;
//						x2 = lnB.q.x;
//						y2 = lnB.q.y;
//						// g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
//
//						double midpointXB = (lnO.p.x + lnO.q.x) / 2.0;
//						double midpointYB = (lnO.p.y + lnO.q.y) / 2.0;
//						g.setColor(Color.GRAY);
//						g.setStroke(new BasicStroke());
//						g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//								RenderingHints.VALUE_ANTIALIAS_ON);
//
//						if (lnO.isHorizontal()) g.fillRect((int) midpointXB - 15,
//								(int) midpointYB - 5, 30, 10);
//						else g.fillRect((int) midpointXB - 5, (int) midpointYB - 15, 10, 30);
//
//						// g.drawLine((int) midpointXA, (int) midpointYA, (int) midpointXB, (int)
//						// midpointYB);
//
//					}
//
//					double dx = (lineC.x2 - lineC.x1);
//					double dy = (lineC.y2 - lineC.y1);
//
//					double lengthOfLineC = Math.sqrt(dx * dx + dy * dy);
//
//					dx = (lineD.x2 - lineD.x1);
//					dy = (lineD.y2 - lineD.y1);
//					double lengthOfLineD = Math.sqrt(dx * dx + dy * dy);
//
//					if (lengthOfLineC >= lengthOfLineD)
//					{
//						lineA = walls.get(i);
//						lineB = walls.get(j);
//					}
//					else
//					{
//						lineA = walls.get(j);
//						lineB = walls.get(i);
//					}
//
//					double gradientA = Math.abs(lineA.getY2() - lineA.getY1())
//							/ Math.abs(lineA.getX2() - lineA.getX1());
//					double gradientB = Math.abs(lineB.getY2() - lineB.getY1())
//							/ Math.abs(lineB.getX2() - lineB.getX1());
//
//					// check that gradients are the same vertical case horizontal case
//					// if (gradientA == gradientB
//					// && ((Double.isInfinite(gradientA) && lineA.x1 == lineB.x1
//					// && lineB.getY1() >= lineA.getY1() && lineB.getY2() <= lineA.getY2()) ||
//					// (gradientA == 0)
//					// && lineA.y1 == lineB.y1
//					// && lineB.getX1() >= lineA.getX1()
//					// && lineB.getX2() <= lineA.getX2()))
//					// if (Line.overlap(lnA, lnA) != null)
//					// {
//					// g.setColor(new Color(0x55FF0000, true));
//					// g.setStroke(new BasicStroke(5.0f));
//					// double x1 = lineA.getX1();
//					// double y1 = lineA.getY1();
//					// double x2 = lineA.getX2();
//					// double y2 = lineA.getY2();
//					// g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
//					//
//					// double midpointXA = (x1 + x2) / 2.0;
//					// double midpointYA = (y1 + y2) / 2.0;
//					// g.setColor(Color.GREEN);
//					// g.setStroke(new BasicStroke());
//					// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//					// RenderingHints.VALUE_ANTIALIAS_ON);
//					// // g2d.fillOval((int) midpointXA - 5, (int) midpointYA - 5, 10, 10);
//					//
//					// g.setColor(new Color(0x550000FF, true));
//					// g.setStroke(new BasicStroke(5.0f));
//					// x1 = lineB.getX1();
//					// y1 = lineB.getY1();
//					// x2 = lineB.getX2();
//					// y2 = lineB.getY2();
//					// g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
//					//
//					// double midpointXB = (x1 + x2) / 2.0;
//					// double midpointYB = (y1 + y2) / 2.0;
//					// g.setColor(Color.GRAY);
//					// g.setStroke(new BasicStroke());
//					// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//					// RenderingHints.VALUE_ANTIALIAS_ON);
//					//
//					// // if (gradientA == 0) g.fillRect((int) midpointXB - 15, (int) midpointYB -
//					// 5,
//					// // 30, 10);
//					// // else g.fillRect((int) midpointXB - 5, (int) midpointYB - 15, 10, 30);
//					//
//					// // g2d.drawLine((int) midpointXA, (int) midpointYA, (int) midpointXB, (int)
//					// // midpointYB);
//					//
//					// }
//				}
//			}
//		}
	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Procedural Generation of Buildings using HPL2");
		frame.add(new ImageTest());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 620);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
