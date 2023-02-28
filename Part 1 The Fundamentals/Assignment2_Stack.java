package assignment2;

public class Stack {
	private class Node
	{
		char data;
		Node next;
	}
	
	Node head 		= null;			//head pointer
	int	size 		= 0;
	
	public void push(char currentCharacter)
	{
		Node oldFirst 	= head;
		
		head			= new Node();			//Allocate new memory
		head.data		= currentCharacter;
		head.next		= oldFirst;
		
		size++;
	}
	
	public char pop()
	{
		char valueToPop	= head.data;
		head 			= head.next;		
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
}
