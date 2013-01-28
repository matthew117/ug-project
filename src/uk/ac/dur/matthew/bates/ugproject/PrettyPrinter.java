package uk.ac.dur.matthew.bates.ugproject;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PrettyPrinter extends DefaultHandler
{
	private int depth = 0;

	public PrettyPrinter()
	{
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		for (int i = 0; i < depth; i++)
			System.out.print("  "); // print indentation

		System.out.print("<" + qName);
		for (int i = 0; i < attributes.getLength(); i++)
		{
			System.out.print(" " + attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\"");
		}
		System.out.println(">");
		depth++;
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		depth--;
		for (int i = 0; i < depth; i++)
			System.out.print("  "); // print indentation
		
		System.out.println("</" + qName + ">");
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		//System.out.print(new String(ch, start, length));
	}
}
