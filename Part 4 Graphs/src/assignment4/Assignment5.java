package assignment4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Assignment5 {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		init();
	}

	private static File getFile() throws FileNotFoundException {
		return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\testIsCycle.txt");
	}
	
	public static void init() throws FileNotFoundException
	{
		In in = new In(getFile());
		Digraph G = new Digraph(in);
		
		int source = 0;
		DirectedCycle finder = new DirectedCycle(G, source);
		
		if(finder.hasCycle()) {
			System.out.println("Directed cycle");
			for(int i : finder.cycle())
			{
				System.out.println(i + " ");
			}
		} else {
			System.out.println("No cycle");
		}
	}
	
	private static class Digraph
	{	
		public final int countVertices;
		private int countEdges;
		public Bag<Integer>[] adjacents;	//TODO change ADT to Bag<Integer>
		
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

		public void addEdge(int first, int second)
		{
			adjacents[first].add(second);
			countEdges++;
		}
		
		public void printAdjecents(int currentIndex)
		{
			Iterator<Integer> iter = adjacents[currentIndex].iterator();
			while(iter.hasNext())
				System.out.println(iter.next());
		}

		private void setAdjacents(int countVertices) {
			adjacents = (Bag<Integer>[]) new Bag[countVertices];
			
			for(int i=0; i<countVertices; i++)
			{
				adjacents[i] = new Bag<Integer>();
			}
		}

		public Iterable<Integer> getAdjacents(int currentVertex)
		{
			return adjacents[currentVertex];
		}
		
		public int getCountVertices()
		{
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
		 
		 public void dfs(Digraph G, int v)
		 {
			 onStack[v] = true;
			 marked[v] = true;
		 
			 for (int w : G.getAdjacents(v))
			 {				
				 if(!marked[w])
				 { 
					 from[w] = v;
					 dfs(G, w);
				 }
				 else if(onStack[w])
				 {
					 int counter = 0;
					 
					 for (int x = v; x != w; x = from[x])
					 {
						 cycle.push(x);
						 counter++;
					 }
					 
					 cycle.push(w);
					 counter++;
					 
					 cycle.push(v);
					 counter++;
					 
					 int reader = 0;
					 boolean hasVertex = false;
					 
					 //SKa poppa stacken
					 while (counter > 0) {
						 reader = cycle.pop();
						 if(source == reader) {
							 hasVertex = true;
						 }
						 counter--;
					 }
					 
					 if (hasVertex)
					 {
						 cycle = new Stack<Integer>();
						 for (int x = v; x != w; x = from[x])
						 {
							 cycle.push(x);
						 }
						 
						 cycle.push(w);						 
						 cycle.push(v);
					 }
				 }
			  }
			 onStack[v] = false;
		 }

		 public boolean hasCycle()
		 { return !cycle.isEmpty(); }
		 
		 public Iterable<Integer> cycle()
		 { return cycle; } 
		 
		 public static void test()
		 {
			 int v = 6;
			 
			 Diagraph G = new Diagraph(v);
			 G.addEdge(0, 5);
			 G.addEdge(5, 4);
			 G.addEdge(4, 3);
			 G.addEdge(3, 5);
			
//			 DirectedCycle finder = new DirectedCycle(G);
//			 finder.dfs(G, 5);
//				
//				if(finder.hasCycle()) {
//					System.out.println("Directed cycle");
//					for(int i : finder.cycle())
//					{
//						System.out.println(i);
//					}
//				} else {
//					System.out.println("No cycle");
//				}
		 }
	}

	/**
	 * 
	 * @author Olivia Denbu Wilhelmsson
	 *
	 * @param <Item>
	 */
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

}
