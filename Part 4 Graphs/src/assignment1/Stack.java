package assignment1;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Olivia Denbu Wilhelmsson
 *
 * @param <Item>
 */
public class Stack<Item> implements Iterable<Item>
{
	Node<Item> first;			
	private int	size;
	
	private class Node<Item>
	{
		Item item;
		Node<Item> next;
	}
	
	public Stack()
	{
		first = null;
		size = 0;
	}
	
	public void push(Item currentItem)
	{
		Node<Item> oldFirst = first;
		
		first = new Node<Item>();			
		first.item = currentItem;
		first.next = oldFirst;
		
		size++;
	}
	
	public Item pop()
	{
		Item currentItem = first.item;
		first = first.next;		
		size--;
		
		return currentItem;
	}
	
	public int getSize()
	{
		return size;
	}
	
    public boolean isEmpty()
    {
        return first == null;
    }
	
	public Iterator<Item> iterator()
	{
        return new LinkedIterator(first);  
    }
	
	
	/**
	 * 
	 * @author Olivia Denbu Wilhelmsson
	 *
	 */
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
