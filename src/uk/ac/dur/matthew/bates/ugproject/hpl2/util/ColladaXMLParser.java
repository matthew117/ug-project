package uk.ac.dur.matthew.bates.ugproject.hpl2.util;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ColladaXMLParser extends DefaultHandler
{
	private boolean mesh = false;
	private boolean source = false;
	private boolean float_array = false;
	
	private List<float[]> vertexList;
	
	public ColladaXMLParser(List<float[]> vertexList)
	{
		this.vertexList = vertexList;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		if (qName.equalsIgnoreCase("mesh"))
		{
			mesh = true;
		}
		else if (mesh && qName.equalsIgnoreCase("source") && attributes.getValue("name").equalsIgnoreCase("position"))
		{
			source = true;
		}
		else if (qName.equalsIgnoreCase("float_array"))
		{
			float_array = true;
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (qName.equalsIgnoreCase("mesh"))
		{
			mesh = false;
		}
		else if (qName.equalsIgnoreCase("source"))
		{
			source = false;
		}
		else if (qName.equalsIgnoreCase("float_array"))
		{
			float_array = false;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		if (mesh && source && float_array)
		{
			String s = new String(ch, start, length);
			String [] xs = s.split(" ");
			float[] fs = new float[3];
			for (int i = 0; i < xs.length; i++)
			{
				fs[i % 3] = Float.parseFloat(xs[i]);
				if (i % 3 == 2)
				{
					vertexList.add(fs);
					fs = new float[3];
				}
			}
		}
	}
}
