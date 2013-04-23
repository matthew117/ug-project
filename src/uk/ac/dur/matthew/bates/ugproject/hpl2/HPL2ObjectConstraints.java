package uk.ac.dur.matthew.bates.ugproject.hpl2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HPL2ObjectConstraints
{
	private String name;
	private int hearts;
	private int magic;
	
	@Override
	public String toString()
	{
		return String.format("<%s hearts=\"%d\" magic=\"%d\" />" , name, hearts, magic);
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		Reader r = new InputStreamReader(new FileInputStream(new File("res/example.json")));
		
		Gson gson = new GsonBuilder().create();
		HPL2ObjectConstraints c = gson.fromJson(r, HPL2ObjectConstraints.class);
		System.out.println(c);
		
		
	}
}
