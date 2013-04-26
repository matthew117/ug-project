package uk.ac.dur.matthew.bates.ugproject.util;

import java.util.Random;

public class RandomUtils
{
	private static Random r = new Random();
	
	private RandomUtils(){}
	
	public static boolean getRandomBoolean()
	{
		return r.nextBoolean();
	}
}
