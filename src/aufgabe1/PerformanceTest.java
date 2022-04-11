package aufgabe1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PerformanceTest
{
	static int WORDCOUNT = 16000;

	public static void main(String[] args) throws Exception
	{
		Dictionary<String, String> dict = new BinaryTreeDictionary<>();
		System.out.println(dict.getClass());
		List<String> wordlist = new ArrayList<>(WORDCOUNT);
		fillWordlist(wordlist, Language.GERMAN);
		measureInsertTime(wordlist, dict);
		measureSearchTime(wordlist, dict);
		measureNonFindSearchTime(wordlist, dict);
	}

	private static void fillWordlist(List<String> wordlist, Language lang) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader("./src/aufgabe1/dtengl.txt"));
		String line;
		int counter = 0;
		while ((line = reader.readLine()) != null)
		{
			if (lang == Language.GERMAN)
				wordlist.add(line.split(" ")[0]);
			else
				wordlist.add(line.split("")[1]);

			counter++;
		}
		reader.close();
	}

	private static void measureNonFindSearchTime(List<String> wordlist, Dictionary<String, String> dict)
			throws Exception
	{
		Iterator iter = wordlist.iterator();
		int counter = 0;

		long startTime = System.nanoTime();

		while (iter.hasNext() && counter < WORDCOUNT)
		{
			dict.search((String) iter.next());
			counter++;
		}

		long stopTime = System.nanoTime();
		System.out.println("NonFindTime: " + ((stopTime - startTime) / 1_000_000) + " ms");
	}

	private static void measureInsertTime(List<String> wordlist, Dictionary dict) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader("./src/aufgabe1/dtengl.txt"));
		String line;
		int counter = 0;

		long startTime = System.nanoTime();

		while ((line = reader.readLine()) != null && counter < WORDCOUNT)
		{
			String[] words = line.split(" ");
			dict.insert(words[0], words[1]);
			counter++;
		}

		long stopTime = System.nanoTime();
		System.out.println("InsertTime: " + ((stopTime - startTime) / 1_000_000) + " ms");

		reader.close();
	}

	private static void measureSearchTime(List<String> germanWordlist, Dictionary dict) throws Exception
	{
		Iterator iter = germanWordlist.iterator();
		int counter = 0;

		long startTime = System.nanoTime();

		while (iter.hasNext() && counter < WORDCOUNT)
		{
			dict.search(iter.next());
			counter++;
		}

		long stopTime = System.nanoTime();
		System.out.println("FindTime: " + ((stopTime - startTime) / 1_000_000) + " ms");
	}

	enum Language
	{
		GERMAN, ENGLISH
	}

}
