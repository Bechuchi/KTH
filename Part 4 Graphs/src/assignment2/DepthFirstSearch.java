package assignment2;

public class DepthFirstSearch<Item>
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
	
	public Stack<Integer> setStack(int endVertex)
	{
		if(!hasPathTo(endVertex)) return null;
		
		Stack<Integer> path = new Stack<Integer>();
		
		for(int i=endVertex; i != source; i=from[i])
		{
			path.push(i);
		}
		
		path.push(source);
		
		return path;
	}
}
