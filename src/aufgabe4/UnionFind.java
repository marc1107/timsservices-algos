package aufgabe4;

import java.util.Arrays;

public class UnionFind
{
	private int list[];
	private int size;

	public UnionFind(int n)
	{
		this.list = new int[n];
		for (int i = 0; i < this.list.length; i++)
			this.list[i] = -1;
		this.size = n;
	}

	public int find(int e)
	{
		while (this.list[e] >= 0) // e istkeineWurzel
			e = this.list[e];
		return e;
	}

	public void union(int s1, int s2)
	{
		if (this.list[s1] >= 0 || this.list[s2] >= 0)
			return;
		if (s1 == s2)
			return;
		if (-this.list[s1] < -this.list[s2]) // Höhe von s1 < Höhe von s2
			this.list[s1] = s2;
		else
		{
			if (-this.list[s1] == -this.list[s2])
				this.list[s1]--; // Höhe von s1 erhöht sich um 1
			this.list[s2] = s1;

		}
		this.size--;
	}

	public int size()
	{
		return this.size;
	}

	public static void main(String[] args)
	{
		int size = 20;
		UnionFind uf = new UnionFind(size);
		uf.union(2, 5);
		uf.union(2, 6);
		uf.union(2, 7);
		System.out.println("5 is contained in " + uf.find(5));
		System.out.println("6 is contained in " + uf.find(6));
		System.out.println("7 is contained in " + uf.find(7));
		System.out.println("Number of partitions: " + uf.size()); // size = 17
		System.out.println(Arrays.toString(uf.list));
		System.out.println();

		uf.union(18, 8);
		uf.union(18, 9);
		uf.union(18, 10);
		uf.union(18, 11);
		System.out.println("8 is contained in " + uf.find(8));
		System.out.println("9 is contained in " + uf.find(9));
		System.out.println("10 is contained in " + uf.find(10));
		System.out.println("11 is contained in " + uf.find(11));
		System.out.println("Number of partitions: " + uf.size()); // size = 13
		System.out.println(Arrays.toString(uf.list));
		System.out.println();

		uf.union(2, 18);
		System.out.println("10 is contained in " + uf.find(10)); // should be in 0
		System.out.println("7 is contained in " + uf.find(7)); // should be in 0
		System.out.println("Number of partitions: " + uf.size()); // size = 12
		System.out.println(Arrays.toString(uf.list));
	}
}
