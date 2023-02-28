package assignment1;
import java.util.Iterator;

public class Graph
{	
	public final int countVertices;
	private int countEdges;
	public LinkedList<Integer>[] adjacents;	//TODO change ADT to Bag<Integer>
	
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
		adjacents = (LinkedList<Integer>[]) new LinkedList[countVertices];
		//adjacents = new LinkedList<Integer>[countVertices];
		
		for(int i=0; i<countVertices; i++)
		{
			adjacents[i] = new LinkedList<Integer>();
		}
	}

	//TODO add getAdjacents(int v)
	public Iterable<Integer> getAdjacents(int currentVertex)
	{
		return adjacents[currentVertex];
	}
//    public Iterable<Integer> adj(int v) {
//        validateVertex(v);
//        return adj[v];
//	  }
}
