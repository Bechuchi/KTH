package assignment1;

//public class Queue<> implements Iterable
public class Queue<Item>
{
	Node first;
	Node last;
	int count;
	
	private class Node
	{
		Item item;
		Node next;
	}
	
	/** Returns the number of items in the queue
	 * 
	 * @return the number of items in the queue
	 */
	public int getSize()
	{
		return count;
	}
	
	/**
	 * Returns true if the queue is empty
	 * 
	 * @return {@code true} if this queue is empty
	 * 		   {@code false} otherwise
	 */
	public boolean isEmpty()
	{
		return getSize() == 0;
	}

	/**
	 * Adds new item at the end of the list
	 * 
	 * @param currentItem the item
	 */
	public void enqueue(Item currentItem)
	{
		Node oldLast = last;
		last = new Node();
		last.item = currentItem;
		last.next = null;
		
		if(isEmpty()) {
			first = last;
		} else {
			oldLast.next = last;
		}
		
		count++;
	}

}