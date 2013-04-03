package uk.ac.dur.matthew.bates.ugproject.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils
{
	// prevent instantiation of a static utility class
	private ListUtils()
	{
	}

	public static List<Double> flatten(List<ArrayList<Double>> xs2)
	{
		List<Double> xs = new ArrayList<Double>();
		for (List<Double> ys : xs2)
		{
			for (Double d : ys)
				xs.add(d);
		}
		return xs;
	}

	public static double sum(List<Double> xs)
	{
		double s = 0;
		for (Double d : xs)
			s += d;
		return s;
	}

	public static List<Double> append(List<Double> xs, List<Double> ys)
	{
		List<Double> zs = new ArrayList<Double>();
		for (Double d : xs)
			zs.add(d);
		for (Double d : ys)
			zs.add(d);
		return zs;
	}

	public static List<Double> append(List<Double> xs, Double y)
	{
		List<Double> zs = new ArrayList<Double>();
		for (Double d : xs)
			zs.add(d);
		zs.add(y);
		return zs;
	}

	public static List<Double> tail(List<Double> xs)
	{
		if (xs.size() == 0) return new ArrayList<Double>();
		List<Double> x = new ArrayList<Double>(xs.subList(1, xs.size()));
		return x;
	}

	public static Double head(List<Double> xs)
	{
		return xs.get(0);
	}

}
