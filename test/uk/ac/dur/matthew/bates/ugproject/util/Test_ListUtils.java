package uk.ac.dur.matthew.bates.ugproject.util;

import static org.junit.Assert.assertEquals;
import static uk.ac.dur.matthew.bates.ugproject.util.ListUtils.append;
import static uk.ac.dur.matthew.bates.ugproject.util.ListUtils.flatten;
import static uk.ac.dur.matthew.bates.ugproject.util.ListUtils.head;
import static uk.ac.dur.matthew.bates.ugproject.util.ListUtils.sumDouble;
import static uk.ac.dur.matthew.bates.ugproject.util.ListUtils.sumInt;
import static uk.ac.dur.matthew.bates.ugproject.util.ListUtils.tail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Test_ListUtils
{

	@Before
	public void setUp()
	{
	}

	@Test
	public void testFlatten()
	{		
		assertEquals(new ArrayList<Integer>(), flatten(new ArrayList<ArrayList<Integer>>()));
		
		List<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		list.add(new ArrayList<Integer>(Arrays.asList(1,2)));
		list.add(new ArrayList<Integer>(Arrays.asList(3)));
		assertEquals(Arrays.asList(1,2,3), flatten(list));
		
		list = new ArrayList<ArrayList<Integer>>();
		list.add(new ArrayList<Integer>(Arrays.asList(1,2)));
		list.add(new ArrayList<Integer>());
		assertEquals(Arrays.asList(1,2), flatten(list));
		
		list = new ArrayList<ArrayList<Integer>>();
		list.add(new ArrayList<Integer>());
		list.add(new ArrayList<Integer>(Arrays.asList(1,2)));
		assertEquals(Arrays.asList(1,2), flatten(list));
		
		list = new ArrayList<ArrayList<Integer>>();
		list.add(new ArrayList<Integer>(Arrays.asList(1,2,3)));
		list.add(new ArrayList<Integer>(Arrays.asList(4,5)));
		list.add(new ArrayList<Integer>(Arrays.asList(6,7)));
		assertEquals(Arrays.asList(1,2,3,4,5,6,7), flatten(list));
	}
	
	@Test
	public void testSumDouble()
	{
		assertEquals(0.0, sumDouble(null), 1E-12);
		assertEquals(0.0, sumDouble(new ArrayList<Double>()), 1E-12);
		assertEquals(1.0, sumDouble(Arrays.asList(0.5,0.5)), 1E-12);
		assertEquals(3.0, sumDouble(Arrays.asList(1.0,2.0)), 1E-12);
	}
	
	@Test
	public void testSumInt()
	{
		assertEquals(0, sumInt(null));
		assertEquals(0, sumInt(new ArrayList<Integer>()));
		assertEquals(0, sumInt(Arrays.asList(-5, 5)));
		assertEquals(6, sumInt(Arrays.asList(1,2,3)));
	}
	
	@Test
	public void testAppend()
	{
		assertEquals(Arrays.asList(1,2,3), append(Arrays.asList(1,2,3), null));
		assertEquals(Arrays.asList(1,2,3), append(null, Arrays.asList(1,2,3)));
		assertEquals(Arrays.asList(1,2,3), append(Arrays.asList(1,2,3), new ArrayList<Integer>()));
		assertEquals(Arrays.asList(1,2,3), append(new ArrayList<Integer>(), Arrays.asList(1,2,3)));
		assertEquals(Arrays.asList(1,2,3,4), append(Arrays.asList(1,2), Arrays.asList(3,4)));
		assertEquals(Arrays.asList(1,2,3,4), append(Arrays.asList(1,2,3), 4));
	}

	@Test
	public void testTail()
	{
		assertEquals(new ArrayList<Integer>(), tail(new ArrayList<Integer>()));
		assertEquals(new ArrayList<Integer>(), tail(Arrays.asList(1)));
		assertEquals(Arrays.asList(2,3), tail(Arrays.asList(1,2,3)));
	}
	
	@Test
	public void testHead()
	{
		assertEquals(new Integer(1), head(Arrays.asList(1)));
		assertEquals(new Integer(10), head(Arrays.asList(10,1,2,3)));
	}

}
