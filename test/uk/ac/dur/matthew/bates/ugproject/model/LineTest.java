package uk.ac.dur.matthew.bates.ugproject.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LineTest
{
	private static final double DELTA = 1E-12;

	private Line line1;
	private Line line2;
	private Line line3;
	private Line line4;
	private Line line5;

	/**
	 * Creates some common test Lines
	 */
	@Before
	public void setUp()
	{
		line1 = new Line(0, 0, 5, 0);
		line2 = new Line(5, 0, 0, 0);
		line3 = new Line(0, 0, 0, 5);
		line4 = new Line(1, 1, 3, 3);
		line5 = new Line(0, 3, 5, 0);
	}

	@Test
	public void testFlip()
	{
		assertEquals(line2, line1.flip());
		assertEquals(line4, line4.flip().flip());
	}

	@Test
	public void testGradient()
	{
		assertTrue(Double.isInfinite(line3.gradient()));
		assertEquals(0.0, line1.gradient(), DELTA);
		assertEquals(1.0, (new Line(1, 1, 2, 2)).gradient(), DELTA);
		assertEquals(0.6, line5.gradient(), DELTA);
	}

	@Test
	public void testIsHorizontal()
	{
		assertTrue(line1.isHorizontal());
		assertTrue(line2.isHorizontal());
		assertFalse(line3.isHorizontal());
		assertFalse(line4.isHorizontal());
	}

	@Test
	public void testIsVertical()
	{
		assertTrue(line3.isVertical());
		assertFalse(line1.isVertical());
		assertFalse(line5.isVertical());
	}

	@Test
	public void testLength()
	{
		assertEquals(5.0, line1.length(), DELTA);
		assertEquals(5.0, line2.length(), DELTA);
		assertEquals(5.0, line3.length(), DELTA);
		assertEquals(2.82, line4.length(), 1E+2);
		assertEquals(5.83, line5.length(), 1E+2);
	}

	@Test
	public void testIsPositiveDirection()
	{
		assertTrue(line1.toString(), line1.isPositiveDirection());
		assertFalse(line2.toString(), line2.isPositiveDirection());
		assertTrue(line3.toString(), line3.isPositiveDirection());
		assertTrue(line4.toString(), line4.isPositiveDirection());
		assertTrue(line5.toString(), line5.isPositiveDirection());
	}

	@Test
	public void testOverlap()
	{
		assertEquals(new Line(0, 4, 0, 6),
				Line.overlap(new Line(0, 0, 0, 6), new Line(0, 4, 0, 10)));
		assertEquals(new Line(0, 4, 0, 6),
				Line.overlap(new Line(0, 4, 0, 10), new Line(0, 0, 0, 6)));
		assertEquals(null, Line.overlap(new Line(0, 0, 0, 6), new Line(1, 4, 1, 10)));
	}

}
