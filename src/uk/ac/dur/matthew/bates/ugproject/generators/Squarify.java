package uk.ac.dur.matthew.bates.ugproject.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.model.RectF;
import uk.ac.dur.matthew.bates.ugproject.util.ListUtils;

public class Squarify
{

	public double shortestEdge(RectF container)
	{
		return Math.min(container.height, container.width);
	}

	public List<Double> normalizeData(List<Double> data, Double area)
	{
		List<Double> normalizedData = new ArrayList<Double>();
		double sum = ListUtils.sum(data);
		double multiplier = area / sum;
	
		for (Double d : data)
		{
			normalizedData.add(d * multiplier);
		}
	
		return normalizedData;
	}

	public ArrayList<Double> getCoordinates(RectF r, List<Double> row)
	{
		ArrayList<Double> coordinates = new ArrayList<Double>();
		double subxoffset = r.x;
		double subyoffset = r.y;
		double areaWidth = ListUtils.sum(row) / r.height;
		double areaHeight = ListUtils.sum(row) / r.width;
	
		if (r.width >= r.height)
		{
			for (int i = 0; i < row.size(); i++)
			{
				coordinates.add(subxoffset);
				coordinates.add(subyoffset);
				coordinates.add(subxoffset + areaWidth);
				coordinates.add(subyoffset + row.get(i) / areaWidth);
				subyoffset = subyoffset + row.get(i) / areaWidth;
			}
		}
		else
		{
			for (int i = 0; i < row.size(); i++)
			{
				coordinates.add(subxoffset);
				coordinates.add(subyoffset);
				coordinates.add(subxoffset + row.get(i) / areaHeight);
				coordinates.add(subyoffset + areaHeight);
				subxoffset = subxoffset + row.get(i) / areaHeight;
			}
		}
		return coordinates;
	}

	public RectF cutArea(RectF r, double area)
	{
		RectF newContainer;
	
		if (r.width >= r.height)
		{
			double areaWidth = area / r.height;
			double newWidth = r.width - areaWidth;
			newContainer = new RectF(r.x + areaWidth, r.y, newWidth, r.height);
		}
		else
		{
			double areaHeight = area / r.width;
			double newHeight = r.height - areaHeight;
			newContainer = new RectF(r.x, r.y + areaHeight, r.width, newHeight);
		}
	
		return newContainer;
	}

	public ArrayList<ArrayList<Double>> squarify(List<Double> data, List<Double> currentRow,
			RectF container, ArrayList<ArrayList<Double>> stack)
	{
		double length;
		double nextDataPoint;
		RectF newContainer;
	
		if (data.size() == 0)
		{
			stack.add(getCoordinates(container, currentRow));
			return null;
		}
	
		length = shortestEdge(container);
		nextDataPoint = data.get(0);
	
		if (improvesRatio(currentRow, nextDataPoint, length))
		{
			currentRow.add(nextDataPoint);
			squarify(ListUtils.tail(data), currentRow, container, stack);
		}
		else
		{
			newContainer = cutArea(container, ListUtils.sum(currentRow));
			stack.add(getCoordinates(container, currentRow));
			squarify(data, new ArrayList<Double>(), newContainer, stack);
		}
		return stack;
	}

	public double calculateRatio(List<Double> R, double w)
	{
		double s = ListUtils.sum(R);
		double max = Collections.max(R);
		double min = Collections.min(R);
	
		return Math.max((w * w * max) / (s * s), (s * s) / (w * w * min));
	}

	public boolean improvesRatio(List<Double> currentRow, double nextNode, double length)
	{
		if (currentRow.size() == 0) return true;
	
		List<Double> newRow = new ArrayList<Double>(currentRow);
		newRow.add(nextNode);
	
		double currentRatio = calculateRatio(currentRow, length);
		double newRatio = calculateRatio(newRow, length);
	
		return currentRatio >= newRatio;
	}

}