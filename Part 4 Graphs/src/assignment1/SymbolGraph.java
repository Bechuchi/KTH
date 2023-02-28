package assignment1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SymbolGraph
{
	private SymbolTable symbolTable;			
	private String[] keys;									
	private Graph graph;									
	
	public SymbolGraph(File filename, String deliminater) throws FileNotFoundException
	{
		symbolTable = new SymbolTable();
		
		Scanner input = new Scanner(filename);	
			
		while(input.hasNext())
		{
			String currentLine = input.nextLine();
			String[] lineSplit = currentLine.split(deliminater);

			for(int i=0; i<lineSplit.length; i++)
			{
				if(!symbolTable.doesContain(lineSplit[i])) {
					symbolTable.add(lineSplit[i], symbolTable.countPairs);
				}
			}
		}
		
		input.close();
        
		// inverted index to get string keys in an array
        keys = new String[symbolTable.countPairs];
        
        for (String state : symbolTable.getKeys())
        {
        	int index = symbolTable.getIndex(state);
        	keys[index] = state;
        }
        
        graph = new Graph(symbolTable.countPairs);
		
        input = new Scanner(filename);
		
		while(input.hasNext())
		{
			String currentLine = input.nextLine();
			String[] lineSplit = currentLine.split(deliminater);
			int firstState = symbolTable.getIndex(lineSplit[0]);
			int secondState = symbolTable.getIndex(lineSplit[1]);
			
			graph.addEdge(firstState, secondState);
		}

		input.close();
	
	}
	
	public boolean contains(String state)
	{ 
		return symbolTable.doesContain(state);
	}
	
	public int index(String state)
	{ 
		return symbolTable.getIndex(state);
	}
	
	public String getName(int index)
	{
		return keys[index];
	}
	
	public Graph getGraph()
	{
		return graph;
	} 
}
