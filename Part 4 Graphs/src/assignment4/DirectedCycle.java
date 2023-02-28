package assignment4;

import java.io.File;

public class DirectedCycle
{
	 private boolean[] marked;
	 private int[] from;
	 private Stack<Integer> cycle; 		// vertices on a cycle (if one exists)
	 private boolean[] onStack; 		// vertices on recursive call stack 
	
	 public DirectedCycle(Diagraph G)
	 {
		onStack = new boolean[G.getCountVertices()];
		from = new int[G.getCountVertices()];
		marked = new boolean[G.getCountVertices()];
	 }
	 
	 public void dfs(Diagraph G, int v)
	 {
		 onStack[v] = true;
		 marked[v] = true;
	 
		 for (int w : G.getAdjacents(v))
		 {
			 if (this.hasCycle()) return;
			
			 else if(!marked[w])
			 { 
				 from[w] = v;
				 dfs(G, w);
			 }
			 else if(onStack[w])
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
		 onStack[v] = false;
	 }

	 public boolean hasCycle()
	 { return cycle != null; }
	 
	 public Iterable<Integer> cycle()
	 { return cycle; } 
	 
	 public static void main(String[] args) {
//		 In in = new In(args[0]);
//		 File file = getFile();
//			String deliminater = " ";
//		 SymbolGraph sg = new SymbolGraph(file, args[1]);
		 int v = 6;
		 
		 Diagraph G = new Diagraph(v);
		 G.addEdge(0, 5);
		 G.addEdge(5, 4);
		 G.addEdge(4, 3);
		 G.addEdge(3, 5);
		
		 DirectedCycle finder = new DirectedCycle(G);
		 finder.dfs(G, 5);
			
			if(finder.hasCycle()) {
				System.out.println("Directed cycle");
				for(int i : finder.cycle())
				{
					System.out.println(i);
				}
			} else {
				System.out.println("No cycle");
			}
	 }
}
