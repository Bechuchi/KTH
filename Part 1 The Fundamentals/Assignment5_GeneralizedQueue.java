package assignment5;

public class GeneralizedQueue<T>
{
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
	
	public GeneralizedQueue()
	{
		count = 0;
		head.next = head;
		head.previous = head;
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
	
	public int getSize()
	{
		return count;
	}

	public void removeKthElement(int index)
	{
		Node current = head;
		
		if(count == 1)
		{
			head.next = head;
			head.previous = head;
			count--;
		}
		else if(count == 0)
		{
			System.out.println("Head cannot be removed");
			print();
			return;
		}
		else
		{
			for(int i=0; i<count; i++)
			{
				if(index == i)
				{
					current.previous.next = current.next;
					current.next.previous = current.previous;
					break;
				}
				
				current = current.previous;
			}
			count--;
		}
		print();
	}
}
