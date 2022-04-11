package aufgabe1;

import java.util.Iterator;

public class SortedArrayDictionary<K, V> implements Dictionary<K, V> 
{
	private static final int DEF_CAPACITY = 16;
	private int size;
	private Entry<K,V>[] data;
	
	@SuppressWarnings("unchecked")
	public SortedArrayDictionary() 
	{
		size = 0;
		data = new Entry[DEF_CAPACITY];
	}
	
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int newCapacity) 
	{	
		if (newCapacity < size)
			return;
		
		Entry<K, V>[] old = data;
		data = new Entry[newCapacity];
		System.arraycopy(old, 0, data, 0, size);
	}
	
	@Override
	public V insert(K key, V value) 
	{		
		Comparable<? super K> comparableKey = (Comparable<? super K>) key;
		
		int currentIndex = searchKey(comparableKey);
		
		// Vorhandener Eintrag wird überschrieben:
		if (currentIndex >= 0) 
			return data[currentIndex].setValue(value);
		
		// Neueintrag:
		if (data.length == size) 
			ensureCapacity(2*size);
		 
		int j = this.size - 1;
		while (j >= 0 && comparableKey.compareTo(data[j].getKey()) < 0) 
		{
			data[j+1] = data[j];
			j--;
		}
		
		data[j+1] = new Entry<K,V>(key,value);
		size++;
		
		return null;
	}

	@Override
	public V search(K key) 
	{
		Comparable<? super K> comparableKey = (Comparable<? super K>) key;
		
		int li = 0;
		int re = size - 1;
		
		while (re >= li)
		{
			int m = (li + re)/2;
		
			if (comparableKey.compareTo(data[m].getKey()) < 0)
				re = m - 1;
			else if (comparableKey.compareTo(data[m].getKey()) > 0)
				li= m + 1;
			else
				return data[m].getValue(); // key gefunden
		}
		return null; // key nicht gefunden
	}

	private int searchKey(Comparable<? super K> comparableKey) 
	{
		int li = 0;
		int re = size - 1;
		
		while (re >= li)
		{
			int m = (li + re)/2;
		
			if (comparableKey.compareTo(data[m].getKey()) < 0)
				re = m - 1;
			else if (comparableKey.compareTo(data[m].getKey()) > 0)
				li= m + 1;
			else
				return m; // key gefunden
		}
		return -1; // key nicht gefunden
	}
		
	@Override
	public V remove(K key) 
	{
		int currentIndex = searchKey((Comparable<? super K>) key);
		if(currentIndex < 0)
			return null;
		
		V returnValue = data[currentIndex].getValue();
		for (int i = currentIndex; i < this.size; i++)
			data[i] = data[i +1];
		
		data[size] = null;
		this.size--;
		return returnValue;
	}

	@Override
	public int size() 
	{
		System.out.println("Lol");
		return this.size;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() 
	{
		return new Iterator<Dictionary.Entry<K,V>>() 
		{
			int currentIndex = 0;
			
			@Override
			public boolean hasNext()
			{
				return currentIndex < size;
			}

			@Override
			public Entry<K, V> next() 
			{
				Entry<K, V> returnEntry = data[currentIndex++];
				return returnEntry;
			}
		};
	}
}
