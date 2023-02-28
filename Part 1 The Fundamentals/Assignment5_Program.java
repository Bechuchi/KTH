package assignment5;

import java.util.Scanner;

public class Program {

	public static void main(String[] args)
	{
		GeneralizedQueue<Integer> list = new GeneralizedQueue<Integer>();
		
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
		System.out.println("2. Remove k:th element");
		System.out.println("3. Show content of list");
		System.out.println();
	}
	
	public static void init(GeneralizedQueue<Integer> list)
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
					list.addNewFront(inputData);
					System.out.println();
					break;
				case 2:
					inputData = getKthIndex();
					list.removeKthElement(inputData);
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
	
	public static Integer getKthIndex()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Index of element to delete:\t");
		Integer index = Integer.parseInt(sc.next());
		
		if(index == 0)
		{
			System.out.println("Head cannot be removed");
			System.exit(0);
		}
		return index;
	}
	
	public static int getOperation()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Wanted operation:\t\t");
		int operation = Integer.parseInt(sc.next());
		
		return operation;
	}
}