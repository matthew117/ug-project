package uk.ac.dur.matthew.bates.ugproject.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.isDirectlyAbove;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.isDirectlyBelow;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.isDirectlyLeftOf;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.isDirectlyRightOf;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.isOnBottomLeftCorner;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.isOnBottomRightCorner;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.isOnTopLeftCorner;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.isOnTopRightCorner;
import static uk.ac.dur.matthew.bates.ugproject.model.FeatureDetection.listBottomMostRectangles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Test_FeatureDetection
{

	@Before
	public void setUp()
	{
	}

	@Test
	public void testIsDirectlyAbove()
	{
		assertTrue(isDirectlyAbove(new Rect(0, 0, 2, 2), new Rect(0, 2, 2, 2)));
		assertTrue(isDirectlyAbove(new Rect(1, 0, 1, 1), new Rect(0, 1, 3, 2)));
		assertTrue(isDirectlyAbove(new Rect(0, 0, 3, 2), new Rect(1, 2, 1, 1)));
		assertTrue(!isDirectlyAbove(new Rect(0, 0, 2, 2), new Rect(2, 2, 2, 2)));
		assertTrue(!isDirectlyAbove(new Rect(0, 0, 2, 2), new Rect(0, 3, 2, 2)));
	}

	@Test
	public void testIsDirectlyBelow()
	{
		assertTrue(isDirectlyBelow(new Rect(0, 2, 2, 2), new Rect(0, 0, 2, 2)));
		assertTrue(isDirectlyBelow(new Rect(0, 1, 3, 2), new Rect(1, 0, 1, 1)));
		assertTrue(isDirectlyBelow(new Rect(1, 2, 1, 1), new Rect(0, 0, 3, 2)));
		assertTrue(!isDirectlyBelow(new Rect(2, 2, 2, 2), new Rect(0, 0, 2, 2)));
		assertTrue(!isDirectlyBelow(new Rect(0, 3, 2, 2), new Rect(0, 0, 2, 2)));
	}

	@Test
	public void testIsDirectlyLeftOf()
	{
		assertTrue(isDirectlyLeftOf(new Rect(0, 0, 2, 2), new Rect(2, 0, 2, 2)));
		assertTrue(isDirectlyLeftOf(new Rect(0, 1, 1, 1), new Rect(1, 0, 2, 3)));
		assertTrue(isDirectlyLeftOf(new Rect(0, 0, 2, 3), new Rect(2, 1, 1, 1)));
		assertTrue(!isDirectlyLeftOf(new Rect(0, 0, 2, 2), new Rect(3, 0, 2, 2)));
		assertTrue(!isDirectlyLeftOf(new Rect(0, 2, 2, 2), new Rect(2, 0, 2, 2)));
		assertTrue(!isDirectlyLeftOf(new Rect(0, 0, 2, 2), new Rect(2, 2, 2, 2)));
	}

	@Test
	public void testIsDirectlyRightOf()
	{
		assertTrue(isDirectlyRightOf(new Rect(2, 0, 2, 2), new Rect(0, 0, 2, 2)));
		assertTrue(isDirectlyRightOf(new Rect(1, 0, 2, 3), new Rect(0, 1, 1, 1)));
		assertTrue(isDirectlyRightOf(new Rect(2, 1, 1, 1), new Rect(0, 0, 2, 3)));
		assertTrue(!isDirectlyRightOf(new Rect(3, 0, 2, 2), new Rect(0, 0, 2, 2)));
		assertTrue(!isDirectlyRightOf(new Rect(2, 0, 2, 2), new Rect(0, 2, 2, 2)));
		assertTrue(!isDirectlyRightOf(new Rect(2, 2, 2, 2), new Rect(0, 0, 2, 2)));
	}

	@Test
	public void testIsOnTopLeftCorner()
	{
		assertTrue(isOnTopLeftCorner(new Rect(0, 0, 2, 2), new Rect(2, 2, 2, 2)));
		assertTrue(!isOnTopLeftCorner(new Rect(0, 0, 2, 2), new Rect(2, 1, 2, 2)));
		assertTrue(!isOnTopLeftCorner(new Rect(0, 0, 2, 2), new Rect(1, 2, 2, 2)));
		assertTrue(!isOnTopLeftCorner(new Rect(0, 0, 2, 2), new Rect(3, 3, 2, 2)));
	}

	@Test
	public void testIsOnTopRightCorner()
	{
		assertTrue(isOnTopRightCorner(new Rect(2, 0, 2, 2), new Rect(0, 2, 2, 2)));
		assertTrue(!isOnTopRightCorner(new Rect(3, 0, 2, 2), new Rect(0, 2, 2, 2)));
	}

	@Test
	public void testIsOnBottomLeftCorner()
	{
		assertTrue(isOnBottomLeftCorner(new Rect(0, 2, 2, 2), new Rect(2, 0, 2, 2)));
		assertTrue(!isOnBottomLeftCorner(new Rect(0, 2, 2, 2), new Rect(3, 0, 2, 2)));
	}

	@Test
	public void testIsOnBottomRightCorner()
	{
		assertTrue(isOnBottomRightCorner(new Rect(2, 2, 2, 2), new Rect(0, 0, 2, 2)));
		assertTrue(!isOnBottomRightCorner(new Rect(2, 1, 2, 2), new Rect(0, 0, 2, 2)));
		assertTrue(!isOnBottomRightCorner(new Rect(1, 2, 2, 2), new Rect(0, 0, 2, 2)));
		assertTrue(!isOnBottomRightCorner(new Rect(3, 3, 2, 2), new Rect(0, 0, 2, 2)));
	}

	@Test
	public void testListBottomMostRectangles()
	{
		List<Rect> list = new ArrayList<Rect>();
		list.add(new Rect(0, 0, 2, 2));
		list.add(new Rect(2, 0, 2, 2));
		list.add(new Rect(4, 0, 2, 2));
		list.add(new Rect(0, 2, 3, 2));
		list.add(new Rect(3, 2, 3, 2));

		assertEquals(
				new ArrayList<Rect>(Arrays.asList(new Rect(0, 2, 3, 2), new Rect(3, 2, 3, 2))),
				listBottomMostRectangles(list));

		assertEquals(new ArrayList<Rect>(Arrays.asList(new Rect(0, 2, 2, 2))),
				listBottomMostRectangles(Arrays.asList(new Rect(0, 0, 2, 2), new Rect(0, 2, 2, 2))));

		assertEquals(new ArrayList<Rect>(Arrays.asList(new Rect(2, 0, 3, 3))),
				listBottomMostRectangles(Arrays.asList(new Rect(0, 0, 2, 2), new Rect(2, 0, 3, 3),
						new Rect(5, 0, 2, 2))));
	}

}
