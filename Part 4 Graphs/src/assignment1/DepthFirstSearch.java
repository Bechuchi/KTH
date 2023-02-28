package assignment1;

public class DepthFirstSearch<T>
{
	private boolean[] marked;
	private int[] from;
	private int count = 0;	//TODO check what its count of
	private final int startVertex;
	
	public DepthFirstSearch(Graph G, int startPosition)
	{
		marked = new boolean[G.countVertices];
		from = new int[G.countVertices];
		startVertex = startPosition;
		search(G, startPosition);
	}
	
	public void search(Graph G, int currentPosition)
	{
		count++;
		setMarked(currentPosition, true);
		
		for(int currentAdjacent : G.getAdjacents(currentPosition))
		{
			//if(!isMarked(currentAdjacent) && !isMarked(endPosition))
			if(!isMarked(currentAdjacent))
			{
				setMarked(currentAdjacent, true);
				setFrom(currentAdjacent, currentPosition);
				search(G, currentAdjacent);
				//search(G, currentAdjacent, endPosition);
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

	public void printPathTo(Stack<Integer> reversedPath)
	{	
		while(!reversedPath.isEmpty())
		{
			if(reversedPath.getSize() != 1)
				System.out.print(reversedPath.pop() + "-");
			else
				System.out.print(reversedPath.pop());
		}
//		for(int i=0; i<count-1; i++)
//		{
//			//TODO fix smarter
//			if(i != count-2)
//				System.out.print(reversedPath.pop() + "-");
//			else
//				System.out.print(reversedPath.pop());
//		}
	}
	
	public Stack<Integer> setPathTo(int endVertex)
	{
		if(!hasPathTo(endVertex)) return null;
		
		Stack<Integer> path = new Stack<Integer>();
		
		for(int i=endVertex; i != startVertex; i=from[i])
		{
			path.push(i);
		}
		
		path.push(startVertex);
		
		return path;
	}
	
//	public Iterable<Integer> getPathTo(int currentVertex)
//	{
//		if(!hasPathTo(currentVertex)) return null;
//		
//		LinkedList<Integer> path = new LinkedList<Integer>();
//		
//		for(int i=currentVertex; i != startVertex; i=from[i])
//		{
//			path.add(i);
//		}
//		
//		path.add(startVertex);
//		return path;
//	}
}
