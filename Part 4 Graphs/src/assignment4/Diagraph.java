package assignment4;
import java.util.Iterator;

public class Diagraph
{
	public final int countVertices;
	private int countEdges;
	public Bag<Integer>[] adjacents;	//TODO change ADT to Bag<Integer>
	
	public Diagraph(int countVertices)
	{
		if(countVertices < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
		this.countVertices = countVertices;
		this.countEdges = 0;
		
		setAdjacents(countVertices);
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

	private void setAdjacents(int countVertices)
	{
		adjacents = (Bag<Integer>[]) new Bag[countVertices];
		
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
	
	public int getCountVertices()
	{
		return countVertices;
	}
}
