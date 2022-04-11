package aufgabe1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HashDictionary<K, V> implements Dictionary<K, V>
{
	private LinkedList<Entry<K, V>> tab[];
	private int elements;

	@SuppressWarnings("unchecked")
	public HashDictionary(int i) throws Exception
	{
		if (isPrime(i))
			this.tab = new LinkedList[i];
		else
			throw new Exception("Not a prime number");
	}

	@Override
	public V insert(K key, V value)
	{
		int hashCode = getHashCode(key);

		if (search(key) == null)
		{
			if (checkLoadFactor())
			{
				provideCapacity();
				hashCode = getHashCode(key);
			}

			if (tab[hashCode] == null)
				tab[hashCode] = new LinkedList<Entry<K, V>>();

			tab[hashCode].add(new Entry<K, V>(key, value));
			elements++;
		} else
		{
			for (int i = 0; i < tab[hashCode].size(); i++)
			{
				if (tab[hashCode].get(i).getKey().equals(key))
				{
					V returnValue = tab[hashCode].get(i).getValue();
					tab[hashCode].get(i).setValue(value);
					return returnValue;
				}
			}
		}
		return null;
	}

	@Override
	public V search(K key)
	{
		int hashCode = getHashCode(key);

		if (tab[hashCode] != null)
		{
			for (var entry : tab[hashCode])
			{
				if (entry.getKey().equals(key))
					return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public V remove(K key)
	{
		if (search(key) != null)
		{
			int hashCode = getHashCode(key);

			for (int i = 0; i < tab[hashCode].size(); i++)
			{
				if (tab[hashCode].get(i).getKey().equals(key))
				{
					V returnValue = tab[hashCode].get(i).getValue();
					tab[hashCode].remove(i);
					elements--;
					return returnValue;
				}
			}
		}
		return null;
	}

	@Override
	public int size()
	{
		return this.elements;
	}

	private int getHashCode(K key)
	{
		int hashCode = key.hashCode();

		if (hashCode < 0)
			hashCode = -hashCode;

		return hashCode % tab.length;
	}

	private boolean isPrime(int num)
	{
		for (int i = 2; i < num; ++i)
		{
			if (num % i == 0)
				return false;
		}
		return true;
	}

	private int calculateNewPrime(int oldPrime)
	{
		int newPrime = oldPrime * 2;

		while (!isPrime(newPrime))
			newPrime++;

		return newPrime;
	}

	private boolean checkLoadFactor()
	{
		return (this.elements / this.tab.length > 2) ? true : false;
	}

	private void provideCapacity()
	{
		List<Entry<K, V>> values = new ArrayList<>(this.elements);

		for (var v : this)
			values.add(v);

		this.tab = new LinkedList[calculateNewPrime(this.tab.length)];

		for (var v : values)
			this.insert(v.getKey(), v.getValue());
	}

	@Override
	public Iterator<Entry<K, V>> iterator()
	{
		return new Iterator<Dictionary.Entry<K, V>>()
		{
			int tabIndex = 0;
			int listIndex = 0;

			@Override
			public boolean hasNext()
			{
				if (tabIndex < tab.length)
				{
					if (tab[tabIndex] == null)
					{
						tabIndex++;
						return this.hasNext();
					}

					if (listIndex < tab[tabIndex].size())
						return true;
				}
				return false;
			}

			@Override
			public Entry<K, V> next()
			{
				Entry<K, V> entry = tab[tabIndex].get(listIndex++);

				if (tab[tabIndex].size() == listIndex)
				{
					tabIndex++;
					listIndex = 0;
				}

				return entry;
			}
		};
	}
}
