package assignment3;
import java.util.Scanner;

public class Program
{
	public static void main(String[] args)
	{		
		DoublyLinkedCircularList<Integer> list = new DoublyLinkedCircularList<Integer>();
		printMenu();
		init(list);	
	}
	
	public static void printMenu()
	{
		System.out.println("*******************");
		System.out.println("Menu of operations");
		System.out.println("*******************");

		System.out.println("0. Exit program");
		System.out.println("1. Add new element");
		System.out.println("2. Remove element (FIFO)");
		System.out.println("3. Show content of list");
		System.out.println();
	}
	
	public static void init(DoublyLinkedCircularList<Integer> list)
	{
		int cmd = 0;
		Integer inputData = null;
		
		do {
			cmd = getOperation();
			
			switch(cmd)
			{
				case 0:
					System.out.println();
					System.out.println("*******************");
					System.out.println("Program ended");
					System.out.println("*******************");
					return;
				case 1:
					inputData = getInputData();
					list.addNewNode(inputData);
					System.out.println();
					break;
				case 2:
					list.removeFIFO();
					System.out.println();
					break;
				case 3:
					list.print();
					System.out.println();
					break;
				default:
					System.out.println("Invalid input");
					return;
			}	
		} while (true);
	}
	
	public static Integer getInputData()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Data to insert:\t\t\t");
		Integer input = Integer.parseInt(sc.next());
		
		return input;
	}
	
	public static int getOperation()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Wanted operation:\t\t");
		int operation = Integer.parseInt(sc.next());
		
		return operation;
	}
}

