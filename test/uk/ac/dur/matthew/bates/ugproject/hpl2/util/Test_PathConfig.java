package uk.ac.dur.matthew.bates.ugproject.hpl2.util;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.dur.matthew.bates.ugproject.hpl2.Entity;

public class Test_PathConfig
{
	private static final double DELTA = 1E-3;
	
	@Test
	public void testBoundingBoxBath()
	{	
		Entity e = new Entity(PathConfig.BATHTUB);
		assertEquals(2.667f, e.getWidth(), DELTA);
		assertEquals(0.777f, e.getHeight(), DELTA);
		assertEquals(1.107f, e.getDepth(), DELTA);
	}
	
	@Test
	public void testBoundingBoxToilet()
	{	
		Entity e = new Entity(PathConfig.TOILET);
		assertEquals(0.502f, e.getWidth(), DELTA);
		assertEquals(0.933f, e.getHeight(), DELTA);
		assertEquals(0.801f, e.getDepth(), DELTA);
	}
	
	@Test
	public void testBoundingBoxToiletPaper()
	{	
		Entity e = new Entity(PathConfig.TOILET_PAPER);
		assertEquals(0.177f, e.getWidth(), DELTA);
		assertEquals(0.176f, e.getHeight(), DELTA);
		assertEquals(0.173f, e.getDepth(), DELTA);
	}
	
	@Test
	public void testBoundingBoxKitchenCooker()
	{	
		Entity e = new Entity(PathConfig.COOKER);
		assertEquals(2.014f, e.getWidth(), DELTA);
		assertEquals(2.320f, e.getHeight(), DELTA);
		assertEquals(1.069f, e.getDepth(), DELTA);
	}
	
	@Test
	public void testBoundingBoxBook01()
	{	
		Entity e = new Entity(PathConfig.BOOK01);
		assertEquals(0.2f, e.getWidth(), DELTA);
		assertEquals(0.25f, e.getHeight(), DELTA);
		assertEquals(0.04f, e.getDepth(), DELTA);
	}
	
	@Test
	public void testBoundingBoxClockDesk()
	{	
		Entity e = new Entity(PathConfig.CLOCK_DESK);
		assertEquals(0.804f, e.getWidth(), DELTA);
		assertEquals(0.509f, e.getHeight(), DELTA);
		assertEquals(0.158f, e.getDepth(), DELTA);
	}
	
	@Test
	public void testBoundingBoxBellows()
	{	
		Entity e = new Entity(PathConfig.BELLOWS02);
		assertEquals(0.264f, e.getWidth(), DELTA);
		assertEquals(0.157f, e.getHeight(), DELTA);
		assertEquals(0.754f, e.getDepth(), DELTA);
	}
	
	@Test
	public void testBoundingBoxDeskx()
	{	
		Entity e = new Entity(PathConfig.WORK_DESK);
		assertEquals(2.345f, e.getWidth(), DELTA);
		assertEquals(1.0f, e.getHeight(), DELTA);
		assertEquals(1.195f, e.getDepth(), DELTA);
	}
}
