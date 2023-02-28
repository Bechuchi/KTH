package assignment2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item>
{
	Node<Item> first;			
	private int	size;
	
	private class Node<T>
	{
		T item;
		Node<T> next;
	}

	public Bag()
	{
		first = null;
		size = 0;
	}
	
	public boolean isEmpty()
	{
		return first == null;
	}
	
	public void add(Item currentItem)
	{
		Node<Item> oldFirst = first;
		
		first = new Node<Item>();			
		first.item = currentItem;
		first.next = oldFirst;
		
		size++;
	}

	public int getSize()
	{
		return size;
	}
	
	public Iterator<Item> iterator()
	{
        return new LinkedIterator(first);  
    }
	
	private class LinkedIterator implements Iterator<Item>
	{
        private Node<Item> current;

        public LinkedIterator(Node<Item> first)
        {
            current = first;
        }

        public boolean hasNext()	{ return current != null; }    
        public void remove()		{ throw new UnsupportedOperationException(); }

        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
           
            Item item = current.item;
            current = current.next; 
            
            return item;
        }
	 }

}
