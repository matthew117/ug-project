package uk.ac.dur.matthew.bates.ugproject.data;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Test_DataFrame
{
	private DataFrame tf;

	@Before
	public void setUp()
	{
		tf = DataFrame.loadFromFile(new File("room_connections.tsv"), true, true);
	}

	@Test
	public void testLoadFromFile()
	{
		assertNotNull("TestFrame is null.", tf);
		assertEquals(12, tf.size());
		System.out.println(tf.toString());
	}
	
	@Test
	public void testSize()
	{
		assertEquals(12, tf.size());
	}

	@Test
	public void testGetInt()
	{
		assertEquals("x", tf.get(0, 1));
		assertEquals("", tf.get(0, 0));
	}

	@Test
	public void testGetString()
	{
		assertEquals("x", tf.get("Living", "Kitchen"));
	}

	@Test
	public void testColumnLabels()
	{
		List<String> labels = Arrays.asList("Outside", "Kitchen", "Pantry", "Laundry", "Living",
				"Dining", "Toilet", "Bedroom", "Master", "Bathroom", "Secondary");
		assertEquals(labels, tf.columnLabels());
	}

	@Test
	public void testRowLabels()
	{
		List<String> labels = Arrays.asList("Outside", "Kitchen", "Pantry", "Laundry", "Living",
				"Dining", "Toilet", "Bedroom", "Master", "Bathroom", "Secondary");
		assertEquals(labels, tf.columnLabels());
	}

	@Test
	public void testColumnInt()
	{
		assertEquals(Arrays.asList("","x","","","x","","","","","",""), tf.column(0));
		assertEquals(Arrays.asList("x","","x","x","x","x","","","","",""), tf.column(1));
	}

	@Test
	public void testColumnString()
	{
		assertEquals(Arrays.asList("","x","","","x","","","","","",""), tf.column("Outside"));
		assertEquals(Arrays.asList("x","","x","x","x","x","","","","",""), tf.column("Kitchen"));
	}

	@Test
	public void testRowInt()
	{
		assertEquals(Arrays.asList("","x","","","x","","","","",""), tf.row(0));
		assertEquals(Arrays.asList("x","","x","x","x","x","","","",""), tf.row(1));
	}

	@Test
	public void testRowString()
	{
		assertEquals(Arrays.asList("","x","","","x","","","","",""), tf.row("Outside"));
		assertEquals(Arrays.asList("x","","x","x","x","x","","","",""), tf.row("Kitchen"));
	}

}
