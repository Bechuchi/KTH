package assignment4;

public class BreathFirstSearch
{
	private boolean[] marked;
	private int[] from;
	//private int count = 0;	//TODO check what its count of
	private final int start;
	private final int end;
	private FIFOQueue q;
	
	public BreathFirstSearch(Diagraph G, int startPosition, int endPosition)
	{
		marked = new boolean[G.countVertices];
		from = new int[G.countVertices];
		start = startPosition;
		end = endPosition;
		q = new FIFOQueue<Integer>();
		search(G);
	}
	
	public void search(Diagraph G)
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
