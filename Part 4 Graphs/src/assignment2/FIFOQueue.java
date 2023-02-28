package assignment2;

import java.util.NoSuchElementException;

public class FIFOQueue<Item>
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
