package assignment1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Program
{
	public static void main(String[] args) throws FileNotFoundException
	{		
		init();
	}
	
	private static void init() throws FileNotFoundException
	{		
		File file = getFile();
		SG symbolGraph = new SG(file, " ");
		Graph G = symbolGraph.getGraph();
		System.out.println(symbolGraph.symbolTable.getKey(0));
		int startPosition = 8;
		DepthFirstSearch dfs = new DepthFirstSearch(G, startPosition);
		
		int endPosition = 7;		
		//TODO print name of state in the path
		if(dfs.hasPathTo(endPosition)) {
			System.out.println(startPosition + " to " + endPosition + ":\t");
			Stack<Integer> reversedPath = dfs.setPathTo(endPosition);
			dfs.printPathTo(reversedPath);
		}
	}
	
	private static File getFile() throws FileNotFoundException
	{
		return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\routes.txt");
	}
}
