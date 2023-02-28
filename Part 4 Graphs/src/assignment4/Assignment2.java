package assignment4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Assignment2 {
	private static int valueSource = 0;
	private static int valueEnd = 0;
	private static int valuePassing = 0;
	
	public static void main(String[] args) throws FileNotFoundException {
		//test();
		client();
	}
	
	private static File getFile() throws FileNotFoundException 	{
		return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\db.txt");
	}
	
	private static File getFile(String filename) throws FileNotFoundException 	{
		return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\" + filename);
	}
	
	//Assignment 1 och 2: Test
	private static void test() throws FileNotFoundException {
		In in = new In(getFile());
		Graph G = new Graph(in);
		int testSource = 0;
		int testEnd = 4;
		
		DepthFirstSearch dfs = new DepthFirstSearch(G, testSource);
		printSearchTestDfs(G, dfs);
		System.out.println();
		BreathFirstSearch bfs = new BreathFirstSearch(G, testSource, testEnd);
		//printSearchTestBfs(G, bfs);
	}
	
	private static void printSearchTestDfs(Graph G, DepthFirstSearch search) {
		for(int currentVertex=0; currentVertex<G.countVertices; currentVertex++) {
			if(search.isMarked(currentVertex)) {
				print(currentVertex + " ");
			}
		}
	}
	
//	private static void printSearchTestBfs(Graph G, BreathFirstSearch search) {
//		//for(int currentVertex = 0; currentVertex < G.countVertices; currentVertex++) {
//            if (search.hasPathTo(valueEnd)) {
//                for(int x : search.setPath()) {
//                    print(String.valueOf(x));
//                }
//            }
//        //}		
//	}
	
	private static void client() throws FileNotFoundException {
		SymbolGraph sg = new SymbolGraph(getFile(), " ");
		Graph G = sg.getGraph();		
		collectInput(sg);
		print("\n");

		//Assignment 1: Dfs() A-C
		DepthFirstSearch dfs = new DepthFirstSearch(G, valueSource);
		if(dfs.hasPathTo(valueEnd)) {
			print("Undirected dfs:\t\t\t");
			printSearch(sg, dfs);
		} else {
			print("Undirected dfs path dont exist");
		}
		print("\n");
		
		//Assignment 2: Bfs() A-C
		BreathFirstSearch bfs = new BreathFirstSearch(G, valueSource, valueEnd);
		if(bfs.hasPathToSource(valueEnd)) {
			print("Undirected dfs:\t\t\t");
			printSearch(sg, bfs);
		} else {
			print("Undirected bfs path dont exist");
		}
		print("\n");
		
		//Assignment 3: A-B-C
		BreathFirstSearch bfs1 = new BreathFirstSearch(G, valueSource, valuePassing);
		boolean hasFirstPath = bfs1.hasPathToSource(valuePassing);
		
		BreathFirstSearch bfs2 = new BreathFirstSearch(G, valuePassing, valueEnd);
		boolean hasSecondPath = bfs2.hasPathToSource(valuePassing);
		
		if(hasFirstPath == true && hasSecondPath == true) {
			print("Undirected bfs with stop:\t");
			printSearch(sg, bfs1);
			printSearch(sg, bfs2);
		} else {
			print("Undirected bfs path dont exists");
		}
		print("\n");
		
		//Assignment 4: A->B
		SymbolDigraph sdg = new SymbolDigraph(getFile(), " ");
		Digraph DG = sdg.getGraph();
		
		DepthFirstSearch ddfs = new DepthFirstSearch(DG, valueSource);
		if(ddfs.hasPathTo(valueEnd)) {
			print("Directed dfs:\t\t\t");
			printSearch(sg, ddfs);
		} else {
			print("Directed dfs path dont exist");
		}
		print("\n");
		
		//Assignment 5: A is part of cycle?
		In in = new In(getFile("testNotCycle.txt"));
		Digraph DGC = new Digraph(in);
		
		int cycleSource = 0;
		DirectedCycle finder = new DirectedCycle(DGC, cycleSource);
		
		if(finder.hasCycle()) {
			print("Directed cycle with source " + cycleSource + ":\t");
			for(int i : finder.cycle()) {
				print(i + " ");
			}
		} else {
			print("No cycle with source " + cycleSource);
		}
		print("\n");
		
		//Assignment 6
		In inNYC = new In(getFile("NYC.txt"));
		Digraph DGNY = new Digraph(inNYC);
		
		int valueStart = 9;
		int valuePassing = 11;
		int valueEnd = 10;
		
		BreathFirstSearch bfsD1 = new BreathFirstSearch(DGNY, valueStart, valuePassing);
		boolean hasFirstDirectedPath = bfsD1.hasPathToSource(valuePassing);
		
		BreathFirstSearch bfsD2 = new BreathFirstSearch(DGNY, valuePassing, valueEnd);
		boolean hasSecondDirectedPath = bfsD2.hasPathToSource(valueEnd);
		
		if(hasFirstDirectedPath == true && hasSecondDirectedPath == true) {
			print("Directed bfs with stop in NYC:\t");
			printSearch(bfsD1);
			printSearch(bfsD2);
		}
		else {
			print("Directed path with stop dont exists in NYC");
		}
	}
	
	private static void collectInput(SymbolGraph sg) {
		print("Enter start:\t\t");
		String nameSource = setState();
		valueSource = sg.st.getValue(nameSource);
		
		print("Enter destination:\t");
		String nameDestination = setState();
		valueEnd = sg.st.getValue(nameDestination);
		
		//Assignment 3
		print("Enter stop:\t\t");
		String namePassing = setState();
		valuePassing = sg.st.getValue(namePassing);
	}
	
	private static void printSearch(SymbolGraph sg, DepthFirstSearch search) {
		Stack<Integer> path = search.setPath(valueEnd);
		for(int i : path)
			print(sg.st.getKey(path.pop()) + " ");
	}
	
	private static void printSearch(SymbolGraph sg, BreathFirstSearch search) {
		Stack<Integer> path = search.setPath();
		for(int i : path)
			print(sg.st.getKey(path.pop()) + " ");
	}
	
	private static void printSearch(BreathFirstSearch search) {
		Stack<Integer> path = search.setPath();
		for(int i : path)
			print(path.pop() + " ");
	}
	
	private static String setState()
	{
		Scanner in = new Scanner(System.in);
		String inputName = in.nextLine();
		
		return inputName;
	}
	
	private static void print(String text)
	{
		System.out.print(text);
	}
	
	private static class DepthFirstSearch<Item>
	{
		private boolean[] marked;
		private int[] from;
		private int countVertexConnectedToSource;	//TODO check what its count of
		private final int source;
		
		public DepthFirstSearch(Graph G, int source)
		{
			marked = new boolean[G.countVertices];
			from = new int[G.countVertices];
			countVertexConnectedToSource = 0;
			this.source = source;
			
			search(G, source);
		}
		
		public DepthFirstSearch(Digraph G, int source)
		{
			marked = new boolean[G.countVertices];
			from = new int[G.countVertices];
			countVertexConnectedToSource = 0;
			this.source = source;
			
			search(G, source);
		}
		
		public void search(Graph G, int currentVertex)
		{
			countVertexConnectedToSource++;
			setMarked(currentVertex, true);
			
			for(int currentAdjacent : G.getAdjacents(currentVertex)) {
				if(!isMarked(currentAdjacent)) {
					setMarked(currentAdjacent, true);
					setFrom(currentAdjacent, currentVertex);
					search(G, currentAdjacent);
				}				
			}
		}
		
		public void search(Digraph G, int currentVertex)
		{
			countVertexConnectedToSource++;
			setMarked(currentVertex, true);
			
			for(int currentAdjacent : G.getAdjacents(currentVertex)) {
				if(!isMarked(currentAdjacent)) {
					setMarked(currentAdjacent, true);
					setFrom(currentAdjacent, currentVertex);
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
		
		public Stack<Integer> setPath(int endVertex)
		{
			if(!hasPathTo(endVertex)) return null;
			
			Stack<Integer> path = new Stack<Integer>();
			for(int i=endVertex; i != source; i=from[i]) {
				path.push(i);
			}
			path.push(source);
			
			return path;
		}
	}

	private static class BreathFirstSearch
	{
		private boolean[] marked;
		private int[] from;
		private final int source;
		private final int destination;
		private FIFOQueue q;
		
		public BreathFirstSearch(Graph G, int source, int destination)
		{
			marked = new boolean[G.countVertices];
			from = new int[G.countVertices];
			this.source = source;
			this.destination = destination;
			q = new FIFOQueue<Integer>();
			
			search(G);
		}
		
		public BreathFirstSearch(Digraph G, int source, int destination)
		{
			marked = new boolean[G.countVertices];
			from = new int[G.countVertices];
			this.source = source;
			this.destination = destination;
			q = new FIFOQueue<Integer>();
			
			search(G);
		}
		
		public void search(Graph G) {
			setMarked(source, true);
			q.enqueue(source);
			
			while(!q.isEmpty() && source != destination) {
				int currentVertex = (int)q.dequeue();
				
				for(int currentAdjacent : G.getAdjacents(currentVertex)) {
					if(!isMarked(currentAdjacent)) {
						from[currentAdjacent] = currentVertex;
						setMarked(currentAdjacent, true);
						q.enqueue(currentAdjacent);
					}
				}
			}
		}
		
		public void search(Digraph G) {
			setMarked(source, true);
			q.enqueue(source);
			int currentVertex = source;
			
			while(!q.isEmpty() && currentVertex != destination) {
				currentVertex = (int)q.dequeue();
				
				for(int currentAdjacent : G.getAdjacents(currentVertex)) {
					if(!isMarked(currentAdjacent)) {
						from[currentAdjacent] = currentVertex;
						setMarked(currentAdjacent, true);
						q.enqueue(currentAdjacent);
					}
				}
			}
		}
		
		private void setMarked(int currentVertex, boolean status) {
			marked[currentVertex] = status;
		}
		
		private void setFrom(int currentAdjacent, int currentPosition) {
			from[currentAdjacent] = currentPosition;
		}
		
		public boolean isMarked(int currentVertex) {
			return marked[currentVertex];
		}
		
		public boolean hasPathToSource(int currentVertex) {
			return isMarked(currentVertex);
		}
		
		public Stack<Integer> setPath() {
			if(!hasPathToSource(destination)) return null;
			
			Stack<Integer> path = new Stack<Integer>();
			
			for(int i=destination; i!=source; i=from[i]) {
				path.push(i);
			}
			path.push(source);
			
			return path;
		}
		
	}

	private static class FIFOQueue<Item>
	{
		private Node<Item> first;
		private Node<Item> last;
		private int count;
		
		private static class Node<Item>
		{
			Node<Item> next;
		    Item item;
		}
		
		public FIFOQueue()
		{
			first = null;
			last = null;
			count = 0;
		}

		public boolean isEmpty()
		{
			return first == null;
		}

		public int getSize()
		{
			return count;
		}
		
		public void enqueue(Item currentItem)
		{
			Node<Item> oldLast = last;
			last = new Node<Item>();
			last.item = currentItem;
			last.next = null;
			
			if(isEmpty()) first = last;
			else oldLast.next = last;
			count++;
		}
		
		public Item dequeue()
		{		
			if(isEmpty()) throw new NoSuchElementException("Queue is empty");
			Item currentItem = first.item;
			first = first.next;
			count--;
			if(isEmpty()) last = null;	//TODO understand
			
			return currentItem;
		}

	}

	private static class Stack<Item> implements Iterable<Item>
	{
		Node<Item> first;			
		private int	size;
		
		private class Node<Item>
		{
			Item item;
			Node<Item> next;
		}
		
		public Stack()
		{
			first = null;
			size = 0;
		}
		
		public void push(Item currentItem)
		{
			Node<Item> oldFirst = first;
			
			first = new Node<Item>();			
			first.item = currentItem;
			first.next = oldFirst;
			
			size++;
		}
		
		public Item pop()
		{
			Item currentItem = first.item;
			first = first.next;		
			size--;
			
			return currentItem;
		}
		
		public int getSize()
		{
			return size;
		}
		
	    public boolean isEmpty()
	    {
	        return first == null;
	    }
		
		public Iterator<Item> iterator()
		{
	        return new LinkedIterator(first);  
	    }
		
		
		/**
		 * 
		 * @author Olivia Denbu Wilhelmsson
		 *
		 */
		private class LinkedIterator implements Iterator<Item>
		{
	        private Node<Item> current;

	        public LinkedIterator(Node<Item> first)
	        {
	            current = first;
	        }

	        public boolean hasNext()	{ return current != null; }
	        public void remove()		{ throw new UnsupportedOperationException(); }

	        public Item next()
	        {
	            if (!hasNext()) throw new NoSuchElementException();
	            Item item = current.item;
	            current = current.next; 
	            
	            return item;
	        }
		 }
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
		
		public Graph(In input)
		{
			if (input == null) throw new IllegalArgumentException("argument is null");

			try {
				this.countVertices = input.readInt();
				int edges = input.readInt();
				
				adjacents = new Bag[countVertices];
				
				for(int v=0; v<countVertices; v++) {
					adjacents[v] = new Bag<Integer>();
				}
				
				for(int i=0; i<edges; i++) {
					int firstState = input.readInt();
					int secondState = input.readInt();
					
					addEdge(firstState, secondState);
				}
				
			} catch (NoSuchElementException e) { throw new IllegalArgumentException ("Illegal argument", e); }
		       
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

		public int getCountVertises()
		{
			return countVertices;
		}
		//TODO add getAdjacents(int v)
		public Iterable<Integer> getAdjacents(int currentVertex)
		{
			return adjacents[currentVertex];
		}

	}

	private static class Bag<Item> implements Iterable<Item>
	{
		Node<Item> first;			
		private int	size;
		
		private class Node<T>
		{
			T item;
			Node<T> next;
		}

		public Bag()
		{
			first = null;
			size = 0;
		}
		
		public boolean isEmpty()
		{
			return first == null;
		}
		
		public void add(Item currentItem)
		{
			Node<Item> oldFirst = first;
			
			first = new Node<Item>();			
			first.item = currentItem;
			first.next = oldFirst;
			
			size++;
		}

		public int getSize()
		{
			return size;
		}
		
		public Iterator<Item> iterator()
		{
	        return new LinkedIterator(first);  
	    }
		
		private class LinkedIterator implements Iterator<Item>
		{
	        private Node<Item> current;

	        public LinkedIterator(Node<Item> first)
	        {
	            current = first;
	        }

	        public boolean hasNext()	{ return current != null; }    
	        public void remove()		{ throw new UnsupportedOperationException(); }

	        public Item next()
	        {
	            if (!hasNext()) throw new NoSuchElementException();
	           
	            Item item = current.item;
	            current = current.next; 
	            
	            return item;
	        }
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

	private static class SymbolDigraph
	{
		public SymbolTable<String, Integer> st;			
		public String[] keys;									
		private Digraph graph;							
		
		public SymbolDigraph(File filename, String deliminater) throws FileNotFoundException
		{
			Scanner input = new Scanner(filename);
			setSymbolTable(input, filename, deliminater);		
			setKeys();  
			
			input = new Scanner(filename);
			buildDigraph(input, filename, deliminater);      
		}
		
		/**
		 * Builds the symbol table from a file with String keys (vertex names) and Integer values (indices)
		 * 
		 * @param input is input stream
		 * @param filename where input is read from
		 * @param deliminater to seperate data in the input stream
		 * @throws FileNotFoundException
		 */
		private void setSymbolTable(Scanner input, File filename, String deliminater) throws FileNotFoundException {
			st = new SymbolTable<String, Integer>();
			
			while(input.hasNext()) {
				String[] lineSplit = input.nextLine().split(deliminater);

				for(int i=0; i<lineSplit.length; i++) {
					if(!st.doesContain(lineSplit[i])) {
						st.put(lineSplit[i], st.getCountPairs());
					}
				}
			}
		}
		
		/**
		 * Sets the array {@code keys[]} with all vertex name associated with an integer index
		 */
		private void setKeys() {
			keys = new String[st.getCountPairs()];
			
	        for(int i=0; i<st.getCountPairs(); i++) {
	        	keys[i] = st.getKey(i);
	        }
		}
		
		private void buildDigraph(Scanner input, File filename, String deliminater) throws FileNotFoundException {
			 graph = new Digraph(st.getCountPairs());
		     setEdges(input, filename, deliminater);
		}
		
		//TODO break it down (?)
		private void setEdges(Scanner input, File filename, String deliminater) throws FileNotFoundException {		
			while(input.hasNext()) {
				String[] adjacentPair = input.nextLine().split(deliminater);
				
				String firstName = adjacentPair[0];
				String secondName = adjacentPair[1];
				
				int firstValue = st.getValue(firstName);
				int secondValue = st.getValue(secondName);
				
				graph.addEdge(firstValue, secondValue);
			}
			input.close();
		}

		public Digraph getGraph() {
			return graph;
		}
	}

	private static class Digraph
	{
		public final int countVertices;
		private int countEdges;
		public Bag<Integer>[] adjacents;
		
		public Digraph(int countVertices) {
			if(countVertices < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
			this.countVertices = countVertices;
			this.countEdges = 0;
			
			setAdjacents(countVertices);
		}
		
		public Digraph(In input)
		{	
			if (input == null) throw new IllegalArgumentException("argument is null");

			try {
				this.countVertices = input.readInt();
				int edges = input.readInt();
				
				adjacents = new Bag[countVertices];
				
				for(int v=0; v<countVertices; v++) {
					adjacents[v] = new Bag<Integer>();
				}
				
				for(int i=0; i<edges; i++) {
					int firstState = input.readInt();
					int secondState = input.readInt();
					
					addEdge(firstState, secondState);
				}
				
			} catch (NoSuchElementException e) { throw new IllegalArgumentException ("Illegal argument", e); }
		}

		public void addEdge(int first, int second) {
			adjacents[first].add(second);
			countEdges++;
		}
		
		public void printAdjecents(int currentIndex) {
			Iterator<Integer> iter = adjacents[currentIndex].iterator();
			while(iter.hasNext())
				System.out.println(iter.next());
		}

		private void setAdjacents(int countVertices) {
			adjacents = (Bag<Integer>[]) new Bag[countVertices];
			
			for(int i=0; i<countVertices; i++) {
				adjacents[i] = new Bag<Integer>();
			}
		}

		public Iterable<Integer> getAdjacents(int currentVertex) {
			return adjacents[currentVertex];
		}
		
		public int getCountVertices() {
			return countVertices;
		}
	}

	private static class DirectedCycle
	{
		 private boolean[] marked;
		 private int[] from;
		 private Stack<Integer> cycle = new Stack<Integer>(); 		// vertices on a cycle (if one exists)
		 private boolean[] onStack; 		// vertices on recursive call stack 
		 private int source;
		
		 public DirectedCycle(Digraph G, int source)
		 {
			this.source = source;
			onStack = new boolean[G.getCountVertices()];
			from = new int[G.getCountVertices()];
			marked = new boolean[G.getCountVertices()];
			
			dfs(G, source);
		 }
		 
		 public void dfs(Digraph G, int currentVertex)
		 {
			 //Slutgiltiga som är på stacken är det som ska vara min cykel
			 setOnStack(currentVertex, true);
			 setMarked(currentVertex, true);
		 
			 for (int currentAdjacent : G.getAdjacents(currentVertex)) {				
				 
				 //dfs tills vi är tillbaka till en som är marked av adjacent
				 if(!marked[currentAdjacent]) { 
					 setFrom(currentAdjacent, currentVertex);
					 dfs(G, currentAdjacent);
				 }
				 else if(onStack[currentAdjacent]) {
					 int counter = 0;
					 
					 //currentVertex = B och går nu bakåt från B tills att vi pushat det som ska bli vår cykel
					 for (int x = currentVertex; x != currentAdjacent; x = from[x]) {
						 cycle.push(x);
						 counter++;
					 }
					 
					 cycle.push(currentAdjacent);
					 counter++;

					 cycle.push(currentVertex);
					 counter++;
					 
					 int reader = 0;
					 boolean cycleHasWantedVertex = false;
					 
					 while (counter > 0) {
						 reader = cycle.pop();

						 if(source == reader) {
							 cycleHasWantedVertex = true;
						 }
						 counter--;
					 }
					 
					 if(cycleHasWantedVertex) {
						 cycle = new Stack<Integer>();
						 for (int x = currentVertex; x != currentAdjacent; x = from[x]) {
							 cycle.push(x);
						 }
						 
						 cycle.push(currentAdjacent);						 
						 cycle.push(currentVertex);
					 }
				 }
			  }
			 onStack[currentVertex] = false;
		 }
		 
		 public void setMarked(int currentVertex, boolean status) {
			 marked[currentVertex] = status;
		 }
		 
		 public void setOnStack(int currentVertex, boolean status) {
			 onStack[currentVertex] = status;
		 }

		 public void setFrom(int currentAdjacent, int currentVertex) {
			 from[currentAdjacent] = currentVertex;
		 }
		 
		 public boolean hasCycle()
		 { return !cycle.isEmpty(); }
		 
		 public Iterable<Integer> cycle()
		 { return cycle; } 
	}
}

