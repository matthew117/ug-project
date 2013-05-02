package uk.ac.dur.matthew.bates.ugproject;

import java.lang.reflect.Field;

import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PathConfig;


public class Main
{
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		Class<PathConfig> p = PathConfig.class;
		for (Field f : p.getFields())
		{
			System.out.println(f.get(""));
		}		
	}
}
