package aufgabe1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class UserInterface
{
	private static Dictionary<String, String> dictionary;

	public static void main(String[] args) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		do
		{
			System.out.print(">> ");
			String rawInput = scanner.nextLine();
			if (rawInput.isEmpty())
				return;
			parseCommand(rawInput);

		} while (true);
	}

	private static void parseCommand(String rawCommand) throws Exception
	{
		String args[] = rawCommand.split(" ");

		switch (args[0])
		{
		case "create":
			execCreate(Arrays.copyOfRange(args, 1, args.length));
			break;
		case "read":
			if (isDictionaryInitialised())
				execRead(Arrays.copyOfRange(args, 1, args.length));
			else
				System.out.println("No dictionary initialised! Use 'create'");
			break;
		case "p":
			if (isDictionaryInitialised())
				execPrint();
			else
				System.out.println("No dictionary initialised! Use 'create'");
			break;
		case "s":
			if (isDictionaryInitialised())
				execSearch(Arrays.copyOfRange(args, 1, args.length));
			else
				System.out.println("No dictionary initialised! Use 'create'");
			break;
		case "i":
			if (isDictionaryInitialised())
				execInsert(Arrays.copyOfRange(args, 1, args.length));
			else
				System.out.println("No dictionary initialised! Use 'create'");
			break;
		case "r":
			if (isDictionaryInitialised())
				execRemove(Arrays.copyOfRange(args, 1, args.length));
			else
				System.out.println("No dictionary initialised! Use 'create'");
			break;
		case "exit":
			execExit();
			break;
		default:
			printHelp();
			break;
		}
	}

	private static void execExit()
	{
		System.exit(0);
	}

	private static void execRemove(String[] args)
	{
		if (args.length != 1)
		{
			printHelp();
			return;
		}

		dictionary.remove(args[0]);
	}

	private static void execInsert(String[] args)
	{
		if (args.length != 2)
		{
			printHelp();
			return;
		}

		dictionary.insert(args[0], args[1]);
	}

	private static void execSearch(String[] args)
	{
		if (args.length != 1)
		{
			printHelp();
			return;
		}

		System.out.println(dictionary.search(args[0]));
	}

	private static void execPrint()
	{
		for (var element : dictionary)
			System.out.println(element.getKey() + " - " + element.getValue());
	}

	private static void execRead(String[] args) throws Exception
	{
		if (args.length != 1 && args.length != 2)
		{
			printHelp();
			return;
		}

		BufferedReader reader = null;
		String line;

		if (args.length == 1)
		{
			reader = new BufferedReader(new FileReader(new File(args[0])));

			while ((line = reader.readLine()) != null)
			{
				String[] words = line.split(" ");
				dictionary.insert(words[0], words[1]);
			}
		}
		else if (args.length == 2)
		{
			reader = new BufferedReader(new FileReader(new File(args[1])));

			int counter = 0;
			while ((line = reader.readLine()) != null && counter < Integer.parseInt(args[0]))
			{
				String[] words = line.split(" ");
				dictionary.insert(words[0], words[1]);
				counter++;
			}
		}

		reader.close();
	}

	private static void execCreate(String[] args) throws Exception
	{
		if (args.length != 1)
		{
			printHelp();
			return;
		}

		if (args[0].contains("Tree"))
			dictionary = new BinaryTreeDictionary<>();
		else if (args[0].contains("Hash"))
			dictionary = new HashDictionary<>(7);
		else
			dictionary = new SortedArrayDictionary<>();
	}

	private static boolean isDictionaryInitialised()
	{
		return dictionary == null ? false : true;
	}

	private static void printHelp()
	{
		System.out.println("Unknown command!");
	}
}
