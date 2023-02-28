package assignment4;

public class DepthFirstSearch<Item>
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
