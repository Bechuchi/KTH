package assignment4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program
{
	public static void main(String[] args) throws FileNotFoundException
	{	
		File file = getFile();
		String deliminater = " ";
		init(file, deliminater);
	}
	
	private static File getFile() throws FileNotFoundException
	{
		//return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\db.txt");
		return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\tinyDG.txt");
	}
	
	private static void init(File file, String deliminater) throws FileNotFoundException
	{		
		SymbolGraph sg = new SymbolGraph(file, deliminater);
		Diagraph G = sg.getGraph();

		/*
		while(true)
		{
			String nameStart = setFirstState();
			String nameEnd = setSecondState();
			
			int valueStart = sg.st.getValue(nameStart);
			int valueEnd = sg.st.getValue(nameEnd);
			
			//Assignment 4
			/*
			DepthFirstSearch dfs = new DepthFirstSearch(G, valueStart);
			printDepthFirstSearchPath(sg, dfs, valueStart, valueEnd);
			*/
			
			//Assignment 6
			/*
			String namePassing = setPassingState();
			int valuePassing = sg.st.getValue(namePassing);
			
			BreathFirstSearch bfs1 = new BreathFirstSearch(G, valueStart, valuePassing);
			boolean hasFirstPath = bfs1.hasPathTo(valuePassing);
			
			BreathFirstSearch bfs2 = new BreathFirstSearch(G, valuePassing, valueEnd);
			boolean hasSecondPath = bfs2.hasPathTo(valueEnd);
			
			if(hasFirstPath == true && hasSecondPath == true)
			{
				printBreathFirstSearch(sg, bfs1, valueStart, valuePassing);
				printBreathFirstSearch(sg, bfs2, valuePassing, valueEnd);
			}
			else {
				System.out.println("Path dont exists");
			}
			
			System.out.println();
			System.out.println();
			
		}*/
		
		//Assignment 5
		
		DirectedCycle finder = new DirectedCycle(G);
		int source = 1;
		String s = sg.st.getKey(source);
		Integer x = (int)sg.st.getValue(s);
		
		finder.dfs(G, x);
		
		if(finder.hasCycle()) {
			System.out.println("Directed cycle");
			for(int i : finder.cycle())
			{
				System.out.println(sg.st.getValue(i + " "));
			}
		} else {
			System.out.println("No cycle");
		}
		
	}

	private static void printDepthFirstSearchPath(SymbolGraph sg, DepthFirstSearch dfs, int start, int end)
	{
		if(dfs.hasPathTo(end))
		{
			Stack<Integer> stack = dfs.setPath(end);
		
			while(!stack.isEmpty())
			{
				Integer currentVertex = stack.pop();

				if(stack.getSize() != 0)
					System.out.print(sg.st.getKey(currentVertex) + "-");
				else
					System.out.print(sg.st.getKey(currentVertex));
			}
		}
		else
		{
			Print("Path dont exist");
			System.out.println();
		}
	}
	
	private static void printBreathFirstSearch(SymbolGraph sg, BreathFirstSearch bfs, int start, int end)
	{
		if(bfs.hasPathTo(end))
		{
			Stack<Integer> path = bfs.setPath();
			while(!path.isEmpty())
			{
				Integer currentVertex = path.pop();
				
				if(path.getSize() != 0)
					System.out.print(sg.st.getKey(currentVertex) + "-");
				else
					System.out.print(sg.st.getKey(currentVertex));
			}
		}
		else
		{
			Print("Path dont exist");
		}
		
	}
	private static String setFirstState()
	{
		Print("Enter name of the first state:\t");
		Scanner in = new Scanner(System.in);
		String inputName = in.nextLine();
		
		return inputName;
	}
	
	private static String setSecondState()
	{
		Print("Enter name of the second state:\t");
		Scanner in = new Scanner(System.in);
		String inputName = in.nextLine();
		//in.close();
		
		return inputName;
	}

	private static String setPassingState()
	{
		Print("Enter name of the passing state:\t");
		Scanner in = new Scanner(System.in);
		String inputName = in.nextLine();
		//in.close();
		
		return inputName;
	}
	
 	private static void Print(String text)
	{
		System.out.print(text);
	}

}
