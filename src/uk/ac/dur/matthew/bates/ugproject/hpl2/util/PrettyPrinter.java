package uk.ac.dur.matthew.bates.ugproject.hpl2.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
		System.out.print(new String(ch, start, length));
	}
	
	public static void printDocument(String src) throws IOException, TransformerException {
		
		StreamSource xmlInput = new StreamSource(new StringReader(src));
		StreamResult xmlOutput = new StreamResult(new StringWriter());
		
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

	    transformer.transform(xmlInput, xmlOutput);
	    
	    System.out.println(xmlOutput.getWriter().toString());
	}
}
