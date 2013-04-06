package uk.ac.dur.matthew.bates.ugproject.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.common.base.Splitter;

public class DataFrame
{
	private List<ArrayList<String>> data;
	private boolean includeColumnLabels;
	private boolean includeRowLabels;

	public DataFrame()
	{

	}

	public static DataFrame loadFromFile(File file, boolean includeColumnLabels,
			boolean includeRowLabels)
	{
		DataFrame df = new DataFrame();
		df.data = new ArrayList<ArrayList<String>>();
		df.includeColumnLabels = includeColumnLabels;
		df.includeRowLabels = includeRowLabels;
		Scanner sc = null;
		try
		{
			sc = new Scanner(file);
			while (sc.hasNextLine())
			{
				String line = sc.nextLine();
				Iterable<String> records = Splitter.on('\t').split(line);
				ArrayList<String> dataRow = new ArrayList<String>();
				for (String s : records)
					dataRow.add(s);
				df.data.add(dataRow);
			}
		}
		catch (IOException ex)
		{
			return null;
		}
		finally
		{
			if (sc != null) sc.close();
		}
		return df;
	}

	public int size()
	{
		return data.size();
	}

	public String get(int row, int column)
	{
		if (row < 0 || column < 0) return null;

		int nRow = includeRowLabels ? row + 1 : row;
		int nColumn = includeColumnLabels ? column + 1 : column;

		if (nRow > (data.size() - 1)) return null;
		if (nColumn > (data.get(nRow).size() - 1)) return null;

		return data.get(nRow).get(nColumn);
	}

	public String get(String rowLabel, String columnLabel)
	{
		if (!(includeColumnLabels && includeRowLabels)) return null;

		List<String> rowLabels = rowLabels();
		List<String> columnLabels = columnLabels();

		int row = rowLabels.indexOf(rowLabel);
		int column = columnLabel.indexOf(columnLabel);

		return get(row, column);
	}

	public List<String> columnLabels()
	{
		if (!includeColumnLabels) return null;
		return data.get(0).subList(1, data.size());
	}

	public List<String> rowLabels()
	{
		if (!includeRowLabels) return null;
		List<String> rowLabels = new ArrayList<String>();
		for (int i = 1; i < data.size(); i++)
		{
			rowLabels.add(data.get(i).get(0));
		}
		return rowLabels;
	}

	public List<String> column(int column)
	{
		if (column < 0) return null;
		if (column > data.get(0).size() - 1) return null;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < data.size() - 1; i++)
		{
			list.add(get(i, column));
		}
		return list;
	}

	public List<String> column(String label)
	{
		if (!includeColumnLabels) return null;
		if (!columnLabels().contains(label)) return null;
		int i = columnLabels().indexOf(label);
		return column(i);
	}

	public List<String> row(int row)
	{
		if (row < 0) return null;
		if (row > data.size() - 1) return null;
		int nRow = includeRowLabels ? row + 1 : row;
		return data.get(nRow).subList(1, data.get(nRow).size()-1);
	}

	public List<String> row(String label)
	{
		if (!includeRowLabels) return null;
		if (!rowLabels().contains(label)) return null;
		int i = rowLabels().indexOf(label);
		return row(i);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (size() > 0)
		{
			int largestColumnWidth = 0;
			List<String> firstRow = data.get(0);
			for (String s : firstRow)
			{
				if (s.length() > largestColumnWidth) largestColumnWidth = s.length();
			}
			for (List<String> row : data)
			{
				for (int i = 0; i < row.size(); i++)
				{
					String value = row.get(i);
					int valueWidth = value.length();
					if (includeRowLabels && i == 0)
					{
						// TODO find the largest row label
						sb.append(value);
						int padding = largestColumnWidth - valueWidth;
						for (int j = 0; j < padding; j++)
							sb.append(' ');
						sb.append(' ');
					}
					else
					{
						if (valueWidth > largestColumnWidth)
						{
							sb.append(' ');
							sb.append(value.substring(0, largestColumnWidth - 2));
							sb.append("... ");
						}
						else
						{
							int padding = (largestColumnWidth - valueWidth) / 2;
							sb.append(' ');
							for (int j = 0; j < padding; j++)
								sb.append(' ');
							sb.append(value);
							for (int j = 0; j < padding; j++)
								sb.append(' ');
							if (valueWidth % 2 == 0) sb.append(' ');
						}
					}
				}
				sb.append('\n');
			}
		}
		return sb.toString();
	}
}
