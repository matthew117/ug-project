package uk.ac.dur.matthew.bates.ugproject.hpl2.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.Splitter;

public class ColladaXMLParser extends DefaultHandler
{
	private boolean mesh = false;
	private boolean source = false;
	private boolean float_array = false;
	private boolean scale = false;
	@SuppressWarnings("unused")
	private boolean rotateX = false;
	@SuppressWarnings("unused")
	private boolean rotateY = false;
	@SuppressWarnings("unused")
	private boolean rotateZ = false;
	
	private float[] scaleFactor = {1.0f, 1.0f, 1.0f};
	private boolean doScale = false;
	
	private List<float[]> vertexList;
	
	public ColladaXMLParser(List<float[]> vertexList)
	{
		this.vertexList = vertexList;
	}
	
	public ColladaXMLParser(List<float[]> vertexList, float[] scaleFactor)
	{
		this.vertexList = vertexList;
		this.scaleFactor = scaleFactor;
		this.doScale = true;
		System.out.println("Scaling by " + Arrays.toString(scaleFactor));
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		if (qName.equalsIgnoreCase("mesh"))
		{
			mesh = true;
		}
		else if (mesh && qName.equalsIgnoreCase("source") && (attributes.getValue("name") != null && attributes.getValue("name").equalsIgnoreCase("position") || (attributes.getValue("id") != null && attributes.getValue("id").contains("positions")) || (attributes.getValue("id") != null && attributes.getValue("id").contains("Position"))))
		{
			source = true;
		}
		else if (qName.equalsIgnoreCase("float_array"))
		{
			float_array = true;
		}
		else if (qName.equalsIgnoreCase("scale") && (attributes.getValue("sid") != null && attributes.getValue("sid").equalsIgnoreCase("scale")))
		{
			scale = true;
		}
		else if (qName.equalsIgnoreCase("rotate") && (attributes.getValue("sid") != null && attributes.getValue("sid").equalsIgnoreCase("rotateX")))
		{
			rotateX = true;
		}
		else if (qName.equalsIgnoreCase("rotate") && (attributes.getValue("sid") != null && attributes.getValue("sid").equalsIgnoreCase("rotateY")))
		{
			rotateY = true;
		}
		else if (qName.equalsIgnoreCase("rotate") && (attributes.getValue("sid") != null && attributes.getValue("sid").equalsIgnoreCase("rotateZ")))
		{
			rotateZ = true;
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
		else if (qName.equalsIgnoreCase("scale"))
		{
			scale = false;
		}
		else if (qName.equalsIgnoreCase("rotate"))
		{
			rotateX = true;
		}
		else if (qName.equalsIgnoreCase("rotate"))
		{
			rotateY = true;
		}
		else if (qName.equalsIgnoreCase("rotate"))
		{
			rotateZ = true;
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		if (mesh && source && float_array)
		{
			String s = new String(ch, start, length);
			Iterable<String> xs = Splitter.on(Pattern.compile("\\s")).omitEmptyStrings().split(s);
			float[] fs = new float[3];
			int j = 0;
			for (String i : xs)
			{
				fs[j % 3] = Float.parseFloat(i);
				if (j % 3 == 2)
				{
					vertexList.add(fs);
					fs = new float[3];
				}
				j++;
			}
		}
		if (scale)
		{
			String s = new String(ch, start, length);
			String [] xs = s.split(" ");
			for (float[] fs : vertexList)
			{
				if (doScale)
				{
					fs[0] *= (Float.parseFloat(xs[0]) * scaleFactor[0]);
					fs[1] *= (Float.parseFloat(xs[1]) * scaleFactor[1]);
					fs[2] *= (Float.parseFloat(xs[2]) * scaleFactor[2]);
				}
				else
				{
					fs[0] *= Float.parseFloat(xs[0]);
					fs[1] *= Float.parseFloat(xs[1]);
					fs[2] *= Float.parseFloat(xs[2]);
				}
			}
		}
	}
}
