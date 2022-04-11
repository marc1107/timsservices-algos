package aufgabe4;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class TelNet
{
	int lbg;
	List<TelVerbindung> minimalSpanningTree;
	HashMap<TelKnoten, Integer> knotenMap;
	int knotenCounter = 0;

	public TelNet(int lbg)
	{
		this.lbg = lbg;
		this.minimalSpanningTree = new LinkedList<>();
		this.knotenMap = new HashMap<>();
	}

	public boolean addTelKnoten(int x, int y)
	{
		TelKnoten knoten = new TelKnoten(x, y);

		if (this.knotenMap.containsKey(knoten))
			return false;

		this.knotenMap.put(knoten, knotenCounter++);
		return true;
	}

	public boolean computeOptTelNet()
	{
		UnionFind forest = new UnionFind(knotenCounter);
		PriorityQueue<TelVerbindung> edges = new PriorityQueue<>(knotenCounter, Comparator.comparing(x -> x.c));
		List<TelVerbindung> minSpanTree = new LinkedList<>();

		fillPriorityQueue(edges);

		while (forest.size() != 1 && !edges.isEmpty())
		{
			TelVerbindung currentVerbindung = edges.poll();
			int t1 = forest.find(knotenMap.get(currentVerbindung.anfang));
			int t2 = forest.find(knotenMap.get(currentVerbindung.ende));

			if (t1 != t2)
			{
				forest.union(t1, t2);
				minSpanTree.add(currentVerbindung);
			}
		}
		if (edges.isEmpty() && forest.size() != 1)
			return false;
		else
			this.minimalSpanningTree = minSpanTree;

		return true;
	}

	private void fillPriorityQueue(PriorityQueue<TelVerbindung> edges)
	{
		for (var v : knotenMap.entrySet())
		{
			for (var w : knotenMap.entrySet())
			{
				if (v.equals(w))
					continue;

				int cost = calcCost(v.getKey(), w.getKey());
				if (cost <= this.lbg)
					edges.add(new TelVerbindung(v.getKey(), w.getKey(), cost));
			}
		}
	}

	private int calcCost(TelKnoten v, TelKnoten w)
	{
		return Math.abs(v.x - w.x) + Math.abs(v.y - w.y);
	}

	public void drawOptTelNet(int xMax, int yMax)
	{
		StdDraw.setCanvasSize(512, 512);
		StdDraw.setXscale(0, xMax + 1);
		StdDraw.setYscale(0, yMax + 1);

		for (var e : this.minimalSpanningTree)
		{
			StdDraw.setPenColor(Color.RED);
			StdDraw.line(e.anfang.x, e.anfang.y, e.ende.x, e.anfang.y);
			StdDraw.line(e.ende.x, e.ende.y, e.ende.x, e.anfang.y);

			StdDraw.setPenColor(Color.BLUE);
			StdDraw.filledSquare(e.anfang.x, e.anfang.y, 0.5);
			StdDraw.filledSquare(e.ende.x, e.ende.y, 0.5);

		}
		StdDraw.show(0);
	}

	public void generateRandomTelNet(int n, int xMax, int yMax)
	{
		Random rand = new Random();

		int i = 0;
		while (i < n)
		{
			if (addTelKnoten(rand.nextInt(xMax + 1), rand.nextInt(yMax + 1)))
				i++;
		}
	}

	public List<TelVerbindung> getOptTelNet()
	{
		return this.minimalSpanningTree;
	}

	public int getOptTelNetKosten()
	{
		int totalCost = 0;
		for (var e : this.minimalSpanningTree)
		{
			totalCost += e.c;
		}
		return totalCost;
	}

	public static void main(String[] args)
	{
		// test1();
		test2();
	}

	private static void test1()
	{
		TelNet telNet = new TelNet(7);
		telNet.addTelKnoten(1, 1);
		telNet.addTelKnoten(3, 1);
		telNet.addTelKnoten(4, 2);
		telNet.addTelKnoten(3, 4);
		telNet.addTelKnoten(7, 5);
		telNet.addTelKnoten(2, 6);
		telNet.addTelKnoten(4, 7);

		telNet.computeOptTelNet();
		System.out.println("Kosten: " + telNet.getOptTelNetKosten());

		telNet.drawOptTelNet(7, 7);
	}

	private static void test2()
	{
		TelNet telNet = new TelNet(100);
		telNet.generateRandomTelNet(1000, 1000, 1000);

		telNet.computeOptTelNet();
		System.out.println("Kosten: " + telNet.getOptTelNetKosten());
		telNet.drawOptTelNet(1000, 1000);

	}
}
