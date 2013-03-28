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
public class RectTest
{
	private Rect rect1;
	private Rect rect2;
	private Rect rect3;
	private Rect rect4;
	private Rect rect5;
	
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
