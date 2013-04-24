package uk.ac.dur.matthew.bates.ugproject.hpl2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.ac.dur.matthew.bates.ugproject.hpl2.util.ColladaXMLParser;
import uk.ac.dur.matthew.bates.ugproject.hpl2.util.PathConfig;

public class Entity extends StaticObject
{
	private boolean active = true;
	private boolean staticPhysics = true;
	private boolean isAffectedByDecal = false;
	private String callbackFunc;
	@SuppressWarnings("unused")
	private List<Entity> connectedProps;
	private String connectionStateChangedCallback;
	private boolean fullGameSave = false;
	private String playerLookAtCallback;
	private boolean playerLookAtCallbackAutoRemove = false;
	private String playerInteractCallback;
	private boolean playerInteractCallbackAutoRemove = false;

	public Entity(String filePath)
	{
		super(filePath);
	}

	protected String userVariablesDelegate()
	{
		String s = "";
		s += "                    <Var Name=\"CastShadows\" Value=\"" + doesCastShadows() + "\" />\n";
		s += "                    <Var Name=\"StaticPhysics\" Value=\"" + staticPhysics + "\" />\n";
		s += "                    <Var Name=\"IsAffectedByDecal\" Value=\"" + isAffectedByDecal + "\" />\n";
		s += "                    <Var Name=\"CallbackFunc\" Value=\"" + (callbackFunc != null ? callbackFunc : "")
				+ "\" />\n";
		// TODO implement this properly by enumerating the list once a format is decided upon
		s += "                    <Var Name=\"ConnectedProps\" Value=\"" + "" + "\" />\n";
		s += "                    <Var Name=\"ConnectionStateChangedCallback\" Value=\""
				+ (connectionStateChangedCallback != null ? connectionStateChangedCallback : "") + "\" />\n";
		s += "                    <Var Name=\"FullGameSave\" Value=\"" + fullGameSave + "\" />\n";
		s += "                    <Var Name=\"PlayerLookAtCallback\" Value=\""
				+ (playerLookAtCallback != null ? playerLookAtCallback : "") + "\" />\n";
		s += "                    <Var Name=\"PlayerLookAtCallbackAutoRemove\" Value=\""
				+ playerLookAtCallbackAutoRemove + "\" />\n";
		s += "                    <Var Name=\"PlayerInteractCallback\" Value=\""
				+ (playerInteractCallback != null ? playerInteractCallback : "") + "\" />\n";
		s += "                    <Var Name=\"PlayerInteractCallbackAutoRemove\" Value=\""
				+ playerInteractCallbackAutoRemove + "\" />\n";
		return s;
	}

	@Override
	protected void loadVertexList(String filePath)
	{
		if (vertexList == null)
		{
			System.out.println("Loading Entity data from: " + filePath);
			vertexList = new ArrayList<float[]>();
			try
			{
				File file = new File(findDAEFile(filePath));
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(file, new ColladaXMLParser(vertexList, getENTScale(filePath)));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private float[] getENTScale(String fileName)
	{
		float[] s = { 1.0f, 1.0f, 1.0f };
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(new File(fileName));

			dom.getDocumentElement().normalize();

			Element root = dom.getDocumentElement();

			NodeList meshNodeList = root.getElementsByTagName("SubMesh");

			for (int i = 0; i < meshNodeList.getLength(); i++)
			{
				Node n = meshNodeList.item(i);

				if (n.getNodeType() == Node.ELEMENT_NODE)
				{
					Element e = (Element) n;
					if (e.hasAttribute("Scale"))
					{
						String[] xyz = e.getAttribute("Scale").split(" ");
						float xs = Float.parseFloat(xyz[0]);
						float ys = Float.parseFloat(xyz[1]);
						float zs = Float.parseFloat(xyz[2]);
						s[0] = xs;
						s[1] = ys;
						s[2] = zs;
						System.out.println(String.format("{x:%f, y:%f, z:%f}",  xs,ys,zs));
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return s;
	}

	private String findDAEFile(String fileName)
	{
		String s = fileName.substring(0, fileName.length() - 4) + ".dae";
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(new File(fileName));

			dom.getDocumentElement().normalize();

			Element root = dom.getDocumentElement();

			NodeList meshNodeList = root.getElementsByTagName("Mesh");

			for (int i = 0; i < meshNodeList.getLength(); i++)
			{
				Node n = meshNodeList.item(i);

				if (n.getNodeType() == Node.ELEMENT_NODE)
				{
					Element e = (Element) n;
					String daeFileGuess = PathConfig.AMNESIA_RESOURCES_DIR + e.getAttribute("Filename");
					File guessFile = new File(daeFileGuess);
					if (guessFile.exists())
					{
						s = daeFileGuess;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		System.out.println("Redirecting to mesh file: " + s);
		return s;
	}

	public String toString()
	{
		String s = "<Entity ";
		s += "Active=\"" + active + "\" ";
		s += "FileIndex=\"" + getFileIndex() + "\" ";
		s += "Group=\"" + getGroup() + "\" ";
		s += "ID=\"" + getId() + "\" ";
		s += "Name=\"" + (getName() != null ? getName() : "") + "\" ";
		s += "Rotation=\"" + Math.toRadians(getRotation()[0]) + " " + Math.toRadians(getRotation()[1]) + " "
				+ Math.toRadians(getRotation()[2]) + "\" ";
		s += "Scale=\"" + getScale()[0] + " " + getScale()[1] + " " + getScale()[2] + "\" ";
		s += "Tag=\"" + (getTag() != null ? getTag() : "") + "\" ";
		s += "WorldPos=\"" + getWorldPos()[0] + " " + getWorldPos()[1] + " " + getWorldPos()[2] + "\">\n";
		s += "                <UserVariables>\n";
		s += userVariablesDelegate();
		s += "                </UserVariables>\n";
		s += "            </Entity>";
		return s;
	}

}
