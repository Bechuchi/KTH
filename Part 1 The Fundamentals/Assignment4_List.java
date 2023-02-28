package assignment4;

public class List<T> {
	private class Node
	{
		Node next;
		Node previous;
	    T data;
	    
		Node(T data)
		{
			this.data = data;
		}
	}
	
	private Node head = new Node(null);
	private int count;
	
	public List()
	{
		count = 0;
		head.next = head;
		head.previous = head;
	}
	
	public void addNewBack(T data)
	{
		Node newNode = new Node(data);
		if(count == 0)
		{
			head.next = newNode;
			head.previous = newNode;
			
			newNode.next = head;
			newNode.previous = head;
		}
		else
		{
			newNode.next = head.next;
			newNode.previous = head;
			
			newNode.next.previous = newNode;
			head.next = newNode;
		}
		count++;
		print();
	}

	public void addNewFront(T data)
	{
		Node newNode = new Node(data);
		if(count == 0)
		{
			head.next = newNode;
			head.previous = newNode;
			
			newNode.next = head;
			newNode.previous = head;
		}
		else
		{
			newNode.previous = head.previous;
			newNode.next = head;
			
			head.previous.next = newNode;
			head.previous = newNode;
		}
		count++;
		print();
	}
	
	public void removeLast()
	{	
		if(count == 1)
		{
			head.next = head;
			head.previous = head;
		}
		else if(count == 0)
		{
			System.out.println("Head cannot be removed");
			print();
			return;
		}
		else
		{
			head.next = head.next.next;
			head.next.previous = head;
		}
		
		count--;
		print();
	}

	public void removeFirst()
	{		
		if(count == 1)
		{
			head.next = head;
			head.previous = head;
		}
		else if(count == 0)
		{
			System.out.println("Head cannot be removed");
			print();
			return;
		}
		else
		{
			head.previous = head.previous.previous;
			head.previous.next = head;
		}
		
		count--;
		print();
	}
	
	public void print()
	{
		Node current = head;

		System.out.print("Current content of list:\t");
		
		for(int i=0; i<=count; i++)
		{
			System.out.print("[" + current.data + "], ");
			current = current.previous;
		}

		System.out.println();
	}
}
