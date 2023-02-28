package assignment4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SymbolGraph
{
	public SymbolTable<String, Integer> st;			
	public String[] keys;									
	private Diagraph graph;							
	
	public SymbolGraph(File filename, String deliminater) throws FileNotFoundException
	{
		Scanner input = new Scanner(filename);
		//setSymbolTable(input, filename, deliminater);	
		setSymbolTableNY(input, filename, deliminater);	
		setKeys();  
		
		input = new Scanner(filename);
		buildDiagraph(input, filename, deliminater);      
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
		st = new SymbolTable<String, Integer>();
		
		while(input.hasNext())
		{
			String[] lineSplit = input.nextLine().split(deliminater);

			for(int i=0; i<lineSplit.length; i++)
			{
				if(!st.doesContain(lineSplit[i])) {
					st.put(lineSplit[i], st.getCountPairs());
				}
			}
		}
	}
	
	private void setSymbolTableNY(Scanner input, File filename, String deliminater) throws FileNotFoundException
	{
		st = new SymbolTable<String, Integer>();
		
		while(input.hasNext())
		{
			String[] lineSplit = input.nextLine().split(deliminater);

			for(int i=0; i<lineSplit.length; i++)
			{
				if(!st.doesContain(lineSplit[i])) {
					st.put(lineSplit[i], st.getCountPairs());
				}
			}
		}
	}
	
	/**
	 * Sets the array {@code keys[]} with all vertex name associated with an integer index
	 */
	private void setKeys()
	{
		keys = new String[st.getCountPairs()];
		
        for(int i=0; i<st.getCountPairs(); i++)
        {
        	keys[i] = st.getKey(i);
        }
	}
	
	private void buildDiagraph(Scanner input, File filename, String deliminater) throws FileNotFoundException
	{
		 graph = new Diagraph(st.getCountPairs());
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
			
			int firstValue = st.getValue(firstName);
			int secondValue = st.getValue(secondName);
			
			graph.addEdge(firstValue, secondValue);
		}

		input.close();
	}

	public Diagraph getGraph()
	{
		return graph;
	}
	//TODO possibly creating contains, index and name here for UNIT TESTING 

}
