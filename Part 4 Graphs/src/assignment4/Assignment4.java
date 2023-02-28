package assignment4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assignment4 {
	public static void main(String[] args) throws FileNotFoundException
	{	
		File file = getFile();
		String deliminater = " ";
		init(file, deliminater);
	}
	
	private static void init(File file, String deliminater) throws FileNotFoundException
	{		
		SymbolGraph sg = new SymbolGraph(file, deliminater);
		Diagraph G = sg.getGraph();
		
		while(true)
		{
			print("Enter start:\t\t");
			String nameStart = setState();
			print("Enter destination:\t");
			String nameEnd = setState();
			
			int valueStart = sg.st.getValue(nameStart);
			int valueEnd = sg.st.getValue(nameEnd);
			
			DepthFirstSearch dfs = new DepthFirstSearch(G, valueStart);
			printDepthFirstSearchPath(sg, dfs, valueStart, valueEnd);
			
			System.out.println();
		}
	}
	
	private static File getFile() throws FileNotFoundException
	{
		//return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\db.txt");
		return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\db.txt");
	}
	
	private static String setState()
	{
		Scanner in = new Scanner(System.in);
		String inputName = in.nextLine();
		
		return inputName;
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
			print("Path dont exist");
			System.out.println();
		}
	}
	
	private static void print(String text)
	{
		System.out.print(text);
	}

	private static class DepthFirstSearch<Item>
	{
		private boolean[] marked;
		private int[] from;
		private int count;	//TODO check what its count of
		private final int start;
		
		public DepthFirstSearch(Diagraph G, int start)
		{
			marked = new boolean[G.countVertices];
			from = new int[G.countVertices];
			count = 0;
			this.start = start;
			
			search(G, start);
		}
		
		public void search(Diagraph G, int currentPosition)
		{
			count++;
			setMarked(currentPosition, true);
			
			for(int currentAdjacent : G.getAdjacents(currentPosition))
			{
				if(!isMarked(currentAdjacent))
				{
					setMarked(currentAdjacent, true);
					setFrom(currentAdjacent, currentPosition);
					search(G, currentAdjacent);
				}				
			}
		}
		
		private void setMarked(int currentPosition, boolean status)
		{
			marked[currentPosition] = status;
		}
		
		private void setFrom(int currentAdjacent, int currentPosition)
		{
			from[currentAdjacent] = currentPosition;
		}
		
		public boolean isMarked(int currentPosition)
		{
			return marked[currentPosition];
		}
		
		public boolean hasPathTo(int currentVertex)
		{
			return isMarked(currentVertex);
		}

		public void getPathTo(Stack<Integer> reversedPath)
		{	
			while(!reversedPath.isEmpty())
			{
				if(reversedPath.getSize() != 1)
					System.out.print(reversedPath.pop() + "-");
				else
					System.out.print(reversedPath.pop());
			}
		}
		
		public Stack<Integer> setPath(int endVertex)
		{
			if(!hasPathTo(endVertex)) return null;
			
			Stack<Integer> path = new Stack<Integer>();
			
			for(int i=endVertex; i != start; i=from[i])
			{
				path.push(i);
			}
			
			path.push(start);
			
			return path;
		}
	}

}
