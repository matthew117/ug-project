package uk.ac.dur.matthew.bates.ugproject.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ListUtils
{
	// prevent instantiation of a static utility class
	private ListUtils()
	{
	}

	public static <T> List<T> flatten(List<ArrayList<T>> xss)
	{
		if (xss == null) throw new NullPointerException();
		List<T> ys = new ArrayList<T>();
		for (List<T> xs : xss)
		{
			for (T e : xs)
				ys.add(e);
		}
		return ys;
	}

	public static double sumDouble(List<Double> xs)
	{	
		if (xs == null || xs.isEmpty()) return 0.0;
		double sum = 0;
		for (Double n : xs)
			sum += n;
		return sum;
	}
	
	public static int sumInt(List<Integer> xs)
	{
		if (xs == null || xs.isEmpty()) return 0;
		int sum = 0;
		for (Integer n : xs)
			sum += n;
		return sum;
	}

	public static <T> List<T> append(List<T> xs, List<T> ys)
	{
		if (xs == null && ys == null) throw new NullPointerException();
		if (xs == null || xs.isEmpty()) return new ArrayList<T>(ys);
		if (ys == null || ys.isEmpty()) return new ArrayList<T>(xs);
		List<T> zs = new ArrayList<T>();
		for (T e : xs)
			zs.add(e);
		for (T e : ys)
			zs.add(e);
		return zs;
	}

	public static <T> List<T> append(List<T> xs, T x)
	{
		List<T> ys = new ArrayList<T>();
		for (T e : xs)
			ys.add(e);
		ys.add(x);
		return ys;
	}

	public static <T> List<T> tail(List<T> xs)
	{
		if (xs == null) throw new NullPointerException();
		if (xs.isEmpty()) return new ArrayList<T>();
		List<T> x = new ArrayList<T>(xs.subList(1, xs.size()));
		return x;
	}

	public static <T> T head(List<T> xs)
	{
		if (xs == null) throw new NullPointerException();
		if (xs.isEmpty()) throw new IllegalArgumentException("The empty list has no 'head'.");
		return xs.get(0);
	}
	
	public static <T> T random(List<T> xs)
	{
		if (xs == null) throw new NullPointerException();
		if (xs.isEmpty()) return null;
		Random r = new Random();
		int i = r.nextInt(xs.size());
		return xs.get(i);
	}

}
