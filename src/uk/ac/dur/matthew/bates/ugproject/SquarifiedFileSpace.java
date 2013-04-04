package uk.ac.dur.matthew.bates.ugproject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.ac.dur.matthew.bates.ugproject.generators.Squarify;
import uk.ac.dur.matthew.bates.ugproject.model.Rect;
import uk.ac.dur.matthew.bates.ugproject.model.RectF;

public class SquarifiedFileSpace extends JPanel
{
	private File directory;
	private List<String> nameList;
	private List<Long> sizeList;
	
	public SquarifiedFileSpace(File directory)
	{
		this.directory = directory;
		createFileSizeMap();
	}
	
	@Override
	public void paint(Graphics context)
	{
		Graphics2D g = (Graphics2D)context;
		
		List<Double> areas = new ArrayList<Double>();
		for (Long l: sizeList) areas.add((double)l);
		List<Rect> boxes = Squarify.squarify(areas, new RectF(0,0,getWidth(),getHeight()));
		
		for (int i = 0; i < boxes.size(); i++)
		{
			Rect r = boxes.get(i);
			String fileName = nameList.get(i);
			
			g.drawRect(r.x, r.y, r.width, r.height);
			
			Rectangle2D strBounds = g.getFontMetrics().getStringBounds(fileName, g);
			int stringWidth = (int) strBounds.getWidth();
			int stringHeight = (int) strBounds.getHeight();			
			g.drawString(fileName, (int)(r.x + r.width/2.0 - stringWidth/2.0), (int)(r.y + r.height/2.0 + stringHeight /2.0));
		}
	}
	
	public void createFileSizeMap()
	{
		nameList = new ArrayList<String>();
		sizeList = new ArrayList<Long>();
		File[] fileList = directory.listFiles();
		for (File file : fileList)
		{
			if(file.getName().contains(".DS_Store")) continue;
			
			nameList.add(file.getName());
			sizeList.add(file.length());
		}
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("File TreeMap");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SquarifiedFileSpace(new File("/Users/matthewbates/Desktop/FTB/direwolf20/minecraft/texturepacks/Sphax/armor")));
		frame.setSize(600, 620);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
