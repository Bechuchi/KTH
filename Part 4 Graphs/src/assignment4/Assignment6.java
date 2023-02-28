package assignment4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Assignment6 {

	public static void main(String[] args) throws FileNotFoundException { 
		File file = getFile();
		init(file);
	}
	
	private static File getFile() throws FileNotFoundException {
		return new File("C:\\Users\\Admin\\Desktop\\Algoritmer och datastrukturer\\LAB4_Graphs\\NYC.txt");
	}
	
	private static void init(File file) throws FileNotFoundException
	{		
		In in = new In(file);
		Digraph G = new Digraph(in);
		
		int valueStart = 9;
		int valuePassing = 11;
		int valueEnd = 10;
		
		BreathFirstSearch bfs1 = new BreathFirstSearch(G, valueStart, valuePassing);
		boolean hasFirstPath = bfs1.hasPathTo(valuePassing);
		
		BreathFirstSearch bfs2 = new BreathFirstSearch(G, valuePassing, valueEnd);
		boolean hasSecondPath = bfs2.hasPathTo(valueEnd);
		
		if(hasFirstPath == true && hasSecondPath == true) {
			printBreathFirstSearch(bfs1);
			printBreathFirstSearch(bfs2);
		}
		else {
			print("Path dont exists");
		}
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
	
	private static void printBreathFirstSearch(BreathFirstSearch bfs)
	{
		Stack<Integer> path = bfs.setPath();
		
		while(!path.isEmpty()) {
			Integer currentVertex = path.pop();
			if(path.getSize() != 0)
				print(currentVertex + "-");
			else {
				print(currentVertex.toString());
			}
		}		
	}

	private static class Digraph
	{	
		public final int countVertices;
		private int countEdges;
		public Bag<Integer>[] adjacents;	//TODO change ADT to Bag<Integer>
		
		//public Digraph(File filename) throws FileNotFoundException
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
					int ignore = input.readInt();
					
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

	private static class BreathFirstSearch
	{
		private boolean[] marked;
		private int[] from;
		//private int count = 0;	//TODO check what its count of
		private final int start;
		private final int end;
		private FIFOQueue q;
		
		public BreathFirstSearch(Digraph G, int startPosition, int endPosition)
		{
			marked = new boolean[G.countVertices];
			from = new int[G.countVertices];
			start = startPosition;
			end = endPosition;
			q = new FIFOQueue<Integer>();
			search(G);
		}
		
		public void search(Digraph G)
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
		
		public Stack<Integer> setPath()
		{
			if(!hasPathTo(end)) return null;
			
			Stack<Integer> path = new Stack<Integer>();
			
			for(int i=end; i != start; i=from[i]) {
				path.push(i);
			}
			
			path.push(start);
			
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

}