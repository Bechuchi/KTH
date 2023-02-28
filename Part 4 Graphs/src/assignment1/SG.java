package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SG
{
	public ST<String, Integer> symbolTable;			
	public String[] keys;									
	private Graph graph;							
	
	public SG(File filename, String deliminater) throws FileNotFoundException
	{
		Scanner input = new Scanner(filename);
		setSymbolTable(input, filename, deliminater);	
		setKeys();  
		
		input = new Scanner(filename);
        buildGraph(input, filename, deliminater);       
	}
	
	/**
	 * Builds the symbol table from a file with String keys (vertex names) and Integer values (indices)
	 * 
	 * @param input is input stream
	 * @param filename where input is read from
	 * @param deliminater to seperate data in the input stream
	 * @throws FileNotFoundException
	 */
	private void setSymbolTable(Scanner input, File filename, String deliminater) throws FileNotFoundException
	{
		symbolTable = new ST<String, Integer>();
		
		while(input.hasNext())
		{
			String[] lineSplit = input.nextLine().split(deliminater);

			for(int i=0; i<lineSplit.length; i++)
			{
				if(!symbolTable.doesContain(lineSplit[i])) {
					symbolTable.put(lineSplit[i], symbolTable.getCountPairs());
				}
			}
		}
	}
	
	/**
	 * Sets the array {@code keys[]} with all vertex name associated with an integer index
	 */
	private void setKeys()
	{
		keys = new String[symbolTable.getCountPairs()];
		
        for(int i=0; i<symbolTable.getCountPairs(); i++)
        {
        	keys[i] = symbolTable.getKey(i);
        }
	}
	
	private void buildGraph(Scanner input, File filename, String deliminater) throws FileNotFoundException
	{
		 graph = new Graph(symbolTable.getCountPairs());
	     setEdges(input, filename, deliminater);
	}
	
	//TODO break it down (?)
	private void setEdges(Scanner input, File filename, String deliminater) throws FileNotFoundException
	{		
		while(input.hasNext())
		{
			String[] adjacentPair = input.nextLine().split(deliminater);
			
			String firstName = adjacentPair[0];
			String secondName = adjacentPair[1];
			
			int firstValue = symbolTable.getValue(firstName);
			int secondValue = symbolTable.getValue(secondName);
			
			graph.addEdge(firstValue, secondValue);
		}

		input.close();
	}

	public Graph getGraph()
	{
		return graph;
	}
	
	public void printKeys()
	{
		for(int i=0; i<keys.length; i++)
			System.out.println(keys[i]);
	}
	
	public void printValue()
	{
		for(int i=0; i<keys.length; i++)
			System.out.println(symbolTable.getValue(keys[i]));
	}

	//TODO possibly creating contains, index and name here for UNIT TESTING 
}
