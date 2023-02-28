package assignment4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Assignment3 {

	public static void main(String[] args) throws FileNotFoundException {
		File file = getFile();
		String deliminater = " ";
		init(file, deliminater);
	}
	
	private static File getFile() throws FileNotFoundException 	{
		return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\db.txt");
	}
	
	public static void init(File file, String deliminater) throws FileNotFoundException {
		SymbolGraph sg = new SymbolGraph(file, deliminater);
		Graph G = sg.getGraph();
		
		print("Enter start:\t\t");
		String nameStart = setState();
		int valueStart = sg.st.getValue(nameStart);
		
		print("Enter destination:\t");
		String nameEnd = setState();
		int valueEnd = sg.st.getValue(nameEnd);
		
		print("Enter state to pass:\t");
		String namePassing = setState();
		int valuePassing = sg.st.getValue(namePassing);
		
		BreathFirstSearch bfs1 = new BreathFirstSearch(G, valueStart, valuePassing);
		boolean hasFirstPath = bfs1.hasPathTo(valuePassing);
		
		BreathFirstSearch bfs2 = new BreathFirstSearch(G, valuePassing, valueEnd);
		boolean hasSecondPath = bfs2.hasPathTo(valuePassing);
		
		if(hasFirstPath == true && hasSecondPath == true)
		{
			printBreathFirstSearch(sg, bfs1, valueStart, valuePassing);
			printBreathFirstSearch(sg, bfs2, valuePassing, valueEnd);
		} else {
			print("Path dont exists");
		}
	}
	
	private static String setState() {
		Scanner in = new Scanner(System.in);
		String inputName = in.nextLine();
		
		return inputName;
	}
	
	private static void print(String text) {
		System.out.print(text);
	}
	
	private static void printBreathFirstSearch(SymbolGraph sg, BreathFirstSearch bfs, int start, int end) {
		if(bfs.hasPathTo(end)) {
			Stack<Integer> path = bfs.setPath();
			
			while(!path.isEmpty()) {
				Integer currentVertex = path.pop();
				
				if(path.getSize() != 0)
					print(sg.st.getKey(currentVertex) + "-");
				else
					print(sg.st.getKey(currentVertex));
			}
		}
		else {
			print("Path dont exist");
		}
		
	}
	
	private static class SymbolGraph
	{
		public SymbolTable<String, Integer> st;			
		public String[] keys;									
		private Graph graph;							
		
		public SymbolGraph(File filename, String deliminater) throws FileNotFoundException
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
		
		private void buildGraph(Scanner input, File filename, String deliminater) throws FileNotFoundException
		{
			 graph = new Graph(st.getCountPairs());
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
				System.out.println(st.getValue(keys[i]));
		}

		//TODO possibly creating contains, index and name here for UNIT TESTING 

	}

	private static class Graph
	{
		public final int countVertices;
		private int countEdges;
		public Bag<Integer>[] adjacents;	//TODO change ADT to Bag<Integer>
		
		public Graph(int countVertices)
		{
			if(countVertices < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
			this.countVertices = countVertices;
			this.countEdges = 0;
			
			setAdjacents(countVertices);
		}

		public void addEdge(int first, int second)
		{
			adjacents[first].add(second);
			adjacents[second].add(first);
			countEdges++;
		}
		
		public void printAdjecents(int currentIndex)
		{
			Iterator<Integer> iter = adjacents[currentIndex].iterator();
			while(iter.hasNext())
				System.out.println(iter.next());
		}

		private void setAdjacents(int countVertices)
		{
			//TODO understand the difference
			adjacents = (Bag<Integer>[]) new Bag[countVertices];
			//adjacents = new LinkedList<Integer>[countVertices];
			
			for(int i=0; i<countVertices; i++)
			{
				adjacents[i] = new Bag<Integer>();
			}
		}

		//TODO add getAdjacents(int v)
		public Iterable<Integer> getAdjacents(int currentVertex)
		{
			return adjacents[currentVertex];
		}

	}

	private static class BreathFirstSearch
	{
		private boolean[] marked;
		private int[] from;
		//private int count = 0;	//TODO check what its count of
		private final int start;
		private final int end;
		private FIFOQueue q;
		
		public BreathFirstSearch(Graph G, int startPosition, int endPosition)
		{
			marked = new boolean[G.countVertices];
			from = new int[G.countVertices];
			start = startPosition;
			end = endPosition;
			q = new FIFOQueue<Integer>();
			search(G);
		}
		
		public void search(Graph G)
		{
			//count++;
			setMarked(start, true);
			q.enqueue(start);
			
			while(!q.isEmpty() && start != end)
			{
				//TODO cast
				int current = (int)q.dequeue();
				for(int currentAdjacent : G.getAdjacents(current))
				{
					if(!isMarked(currentAdjacent))
					{
						from[currentAdjacent] = current;
						setMarked(currentAdjacent, true);
						q.enqueue(currentAdjacent);
					}
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

//		public void printPath(Stack<Integer> reversedPath)
//		{	
//			while(!reversedPath.isEmpty())
//			{
//				if(reversedPath.getSize() != 1)
//					System.out.print(reversedPath.pop() + "-");
//				else
//					System.out.print(reversedPath.pop());
//			}
//		}
		
		public Stack<Integer> setPath()
		{
			if(!hasPathTo(end)) return null;
			
			Stack<Integer> path = new Stack<Integer>();
			
			for(int i=end; i != start; i=from[i])
			{
				path.push(i);
			}
			
			path.push(start);
			
			return path;
		}
		
	}


}