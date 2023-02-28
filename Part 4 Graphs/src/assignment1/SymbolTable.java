package assignment1;

public class SymbolTable<Key, Value>
{
	public String[] keys = new String[60];
	public int[] values = new int[60];
	public int countPairs = 0;

	public void add(String currentKey, int currentValue)
	{		
		int index = rank(currentKey);
		
		//key is already in table and we increase its value by one
		if(!isFirstElement()  && !insertingAtEndOfST(index) && keys[index].compareTo(currentKey) == 0) {
            //values[index] = values[index] + 1;
			values[index] = currentValue;
            return;
        }
        
        //Shift all elements at higher index than the new element
        for(int i=countPairs; i>index; i--) {
        	keys[i] = keys[i-1];
        	values[i] = values[i-1];
        }
        
        //Set new values
        keys[index] = currentKey;
        //values[index] = 1;
        values[index] = currentValue;
        countPairs++;
	}
	
	private boolean insertingAtEndOfST(int index)
	{
		if(index == countPairs)
			return true;
		else
			return false;
	}
	
	private boolean isFirstElement()
	{
		if(getSize() == 0)
			return true;
		else
			return false;
		
	}
	
	public int getSize()
	{
		return countPairs;
	}
	
	private int rank(String currentKey)
	{
		int lowIndex = 0;
		int highIndex = countPairs-1;
		
		while(lowIndex <= highIndex)
		{
			int middle = lowIndex + (highIndex-lowIndex)/2;
			int compare = compare(currentKey, keys[middle]);
			
			if(compare < 0)
				highIndex = middle - 1;
			else if (compare > 0)
				lowIndex = middle + 1;
			else
				return middle;
		}
		
		return lowIndex;
	}
	
	private int compare(String newElement, String middle)
	{	
		if(newElement.compareTo(middle) < 0)
			return -1;
		else if(newElement.compareTo(middle) == 0)
			return 0;
		else
			return 1;
	}
	
	private boolean isSmallerThan(String newElement, String middle)
	{
		if(newElement.compareTo(middle) == -1)
			return true;
		return false;
	}	
	
    public boolean doesContain(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");

        for(int i=0; i<countPairs; i++)
        {
        	if(keys[i].equals(key))
        		return true;
        }
        
        return false;
    }
    
    public int getIndex(String key)
    {
    	for(int i=0; i<countPairs; i++)
    	{
    		if(keys[i] == key)
    			return i;
    	}
    	
    	//TODO different index error
    	return 0;
    }
    
//    public Key[] getKeys()
//    {    	
//    	Key[] keys = (Key[]) new Comparable[countPairs];
//    	for(int i=0; i<countPairs; i++)
//    	{
//    		keys[i] = this.keys[i];
//    	}
//    	
//    	return keys;
//    }
    
    public String[] getKeys()
    {    	
    	String[] keys = new String[countPairs];
    	for(int i=0; i<countPairs; i++)
    	{
    		keys[i] = this.keys[i];
    	}
    	
    	return keys;
    }
}

