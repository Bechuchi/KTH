package assignment1;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T>
{ 
	private class Node<T>
	{
		T data;
		Node<T> next;
	}
	
	Node<T> first = null;			
	private int	size = 0;
	
	public void add(T currentData)
	{
		Node<T> oldFirst = first;
		
		first = new Node<T>();			
		first.data = currentData;
		first.next = oldFirst;
		
		size++;
	}

	public T pop()
	{
		T valueToPop	= first.data;
		first 			= first.next;		
		size--;
		
		return valueToPop;
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		if (size == 0)
			return true;
		else
			return false;
	}

	public Iterator<T> iterator()
	{
        return new LinkedIterator(first);  
    }
	
	private class LinkedIterator implements Iterator<T>
	{
        private Node<T> current;

        public LinkedIterator(Node<T> first)
        {
            current = first;
        }

        public boolean hasNext()  
        { 
        	return current != null;
        }
        
        public void remove()
        { 
        	throw new UnsupportedOperationException();
        }

        public T next()
        {
            if (!hasNext()) throw new NoSuchElementException();
           
            T item = current.data;
            current = current.next; 
            
            return item;
        }
	 }
}


