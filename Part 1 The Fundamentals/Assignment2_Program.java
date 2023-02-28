package assignment2;
import java.util.Scanner;

public class Program
{
	private static Stack stack = new Stack();
	
	public static void main(String[] args)
	{		
		System.out.print("Input:\t\t\t");
		
		Scanner sc			= new Scanner(System.in);
		String input		= sc.next();
		sc.close();							//Prevents memory leak
		
		char[] inputARRAY 	= input.toCharArray();
		int counter			= 0;
		
		recursive(inputARRAY, counter);
		System.out.print("\n");
		iterative(inputARRAY);
	}	
	
	public static void iterative(char[] inputARRAY)
	{		
		//For each iteration the current character will be pushed onto the stack
		for(int i=0; i<inputARRAY.length; i++)
		{
			stack.push(inputARRAY[i]);
		}
		
		System.out.print("Reversed by iteration:\t");
		
		//To print out the element and empty the stack we pop it
		for(int j=inputARRAY.length; j>0; j--)
		{
			System.out.print(stack.pop());
		}	
	}
	
	public static void recursive(char[] inputARRAY, int counter)
	{	
		if(counter == inputARRAY.length)
		{
			System.out.print("Reversed by recursion:\t");
		}
		else
		{		
			char currentChar = inputARRAY[counter];
			stack.push(currentChar);
			
			counter++;
			
			recursive(inputARRAY, counter);
			System.out.print(stack.pop());
		}
	}

}

