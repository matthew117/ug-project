/**
 * 
 */
package uk.ac.dur.matthew.bates.ugproject.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Matthew Bates
 *
 */
public class Test_Rect
{
	private Rect rect1;
	private Rect rect2;
	private Rect rect3;
	private Rect rect4;
	
	/**
	 * Creates some common test Rectangles
	 */
	@Before
	public void setUp()
	{
		rect1 = new Rect(0, 0, 50, 50);
		rect2 = new Rect(0, 0, 100, 50);
		rect3 = new Rect(0, 0, 11, 11);
		rect4 = new Rect(10, 10, 20, 20);
	}

	/*
	 * Runs after the tests to close any lasting connections etc.
	 */
	@After
	public void tearDown()
	{
	}
	
	@Test
	public void testLeft()
	{
		assertEquals(new Line(0, 0, 0, 50), rect1.left());
		assertEquals(new Line(10, 10, 10, 30), rect4.left());
	}
	
	@Test
	public void testRight()
	{
		assertEquals(new Line(50, 0, 50, 50), rect1.right());
		assertEquals(new Line(30, 10, 30, 30), rect4.right());
	}
	
	@Test
	public void testTop()
	{
		assertEquals(new Line(0, 0, 50, 0), rect1.top());
		assertEquals(new Line(10, 10, 30, 10), rect4.top());
	}
	
	@Test
	public void testBottom()
	{
		assertEquals(new Line(0, 50, 50, 50), rect1.bottom());
		assertEquals(new Line(10, 30, 30, 30), rect4.bottom());
	}

	@Test
	public void testCenterX()
	{
		assertEquals(25, rect1.centerX());
		assertEquals(50, rect2.centerX());
		assertEquals(5, rect3.centerX());
		assertEquals(20, rect4.centerX());
	}

	@Test
	public void testCenterY()
	{
		assertEquals(25, rect1.centerY());
		assertEquals(25, rect2.centerY());
		assertEquals(5, rect3.centerY());
		assertEquals(20, rect4.centerY());
	}

	@Test
	public void testContains()
	{
		assertEquals(true, (new Rect(0, 0, 20, 20)).contains(10, 10));
		assertEquals(true, (new Rect(0, 0, 10, 20)).contains(8, 10));
		assertEquals(true, (new Rect(0, 0, 10, 10)).contains(10, 10));
		assertEquals(true, (new Rect(0, 0, 20, 20)).contains(0, 0));
		assertEquals(false, (new Rect(0, 0, 20, 20)).contains(21, 20));
		assertEquals(false, (new Rect(0, 0, 20, 20)).contains(-1, -1));
		assertEquals(false, (new Rect(0, 0, 20, 20)).contains(50, 0));
	}

}
