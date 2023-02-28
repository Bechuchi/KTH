package assignment4;
import java.util.NoSuchElementException;

public class SymbolTable<Key extends Comparable, Value>
{
	private static final int INIT_CAPACITY = 2;
	private Key[] keys;
	private Value[] values;
	private int countPairs = 0;
	
	/**
	 * Initializes an empty symbol table
	 */
	public SymbolTable()
	{
		this(INIT_CAPACITY);
	}
	
	/**
	 * Initializes an empty symbol table with the specified capacity.
	 * @param currentCapacity the maximum capacity
	 */
	public SymbolTable(int currentCapacity)
	{
		keys = (Key[]) new Comparable[currentCapacity];
		values = (Value[]) new Object[currentCapacity];
	}
	
	/**
	 * Resize the underlying arrays
	 * @param currentCapacity the new capacity
	 */
	public void resize(int currentCapacity)
	{
		Key[] tempKeys = (Key[]) new Comparable[currentCapacity];
		Value[] tempValues = (Value[]) new Object[currentCapacity];
		
		for(int i=0; i<countPairs; i++)
		{
			tempKeys[i] = keys[i];
			tempValues[i] = values[i];
		}
		
		keys = tempKeys;
		values = tempValues;
	}
	
	/**
	 * Returns the number of key-value pairs in the symbol table
	 * @return the number of key-value pairs in the symbol table
	 */
	public int getCountPairs()
	{
		return countPairs;
	}
	
	/**
	 * Returns true if the symbol table is empty
	 * 
	 * @return {@code true} if this symbol table is empty
	 * 		   {@code false} otherwise
	 */
	public boolean isEmpty()
	{
		return getCountPairs() == 0;
	}
		
	/**
	 * Does this symbol table contain the given key?
	 * 
	 * @param currentKey the key
	 * @return {@code true} if this symbol table contains {@code currentKey}
	 * 		   {@code false} otherwise
	 */
	public boolean doesContain(Key currentKey) {
        if (currentKey == null) throw new IllegalArgumentException("argument to contains() is null");

        for(int i=0; i<countPairs; i++)
        {
        	if(currentKey.compareTo(keys[i]) == 0)
        		return true;
        }
        
        return false;
    }
	
	/**
	 * Returns the associated value with the given key in this symbol table
	 * 
	 * @param currentKey the key
	 * @return the value associated with the key if it is in the symbol table 
	 * 		   	and {@code null} if the key is not in the symbol table
	 * @throws IllegalArgumentException if {@code currentKey} is {@code null}
	 */
	public Value getValue(Key currentKey)
	{
		if(currentKey == null) throw new IllegalArgumentException("Argument to getValue() is null");
		if(isEmpty()) return null;
		
		int index = rank(currentKey);
		//Value index = rank(currentKey);
		
//		Key cpmKey = getKey(index);
//		if(index < countPairs && getKey(index).compareTo(currentKey) == 0)
//		{
//			return values[index];
//		}
		
		return values[index];
		//return null;
	}
	
	public Key getKey(int currentValue)
	{
		if(isEmpty()) throw new NoSuchElementException("called getKey() with empty symbol table");
		
		for(int i=0; i<countPairs; i++)
		{
			if((int)values[i] == currentValue) //TODO Check 
			{
				int index = i;
				return keys[index];
			}
		}
		
		return null;
	}
	
	
	/**
	 * Returns the number of keys in this symbol table strictly less than {@code currentKey}
	 * 
	 * @param currentKey the key
	 * @return the number of keys in this symbol table strictly less than {@code currentKey}
	 * @throws IllegalArgumentException if {@code currentKey} is {@code null}
	 */
	public int rank(Key currentKey)
	{
		if(currentKey == null) throw new IllegalArgumentException("Argument to rank() is null");
		
		int low = 0, high = getCountPairs()-1;
		
		while(low <= high)
		{
			int middle = low + (high-low)/2;
			int compare = currentKey.compareTo(keys[middle]);
			
			if(compare < 0)
				high = middle - 1;
			else if (compare > 0)
				low = middle + 1;
			else
				return middle;
		}
		
		return low;
	}
	
	/**
	 * Put key-value pair into the table (remove key from table if value is null)
	 * 
	 * @param currentKey
	 * @param currentValue
	 */
	public void put(Key currentKey, Value currentValue)
	{
		if(currentKey == null) {
			throw new IllegalArgumentException("Argument to put() is null");
		}
		
		int index = rank(currentKey);
		
		if(index < countPairs && keys[index].compareTo(currentKey) == 0) {
			values[index] = currentValue;
			return;
		}
		
		if(countPairs == keys.length) {
			resize(2 * keys.length);
		}
		
        for(int i=countPairs; i>index; i--) {
        	keys[i] = keys[i-1];
        	values[i] = values[i-1];
        }
        
        keys[index] = currentKey;
        values[index] = currentValue;
        countPairs++;
	}
}
