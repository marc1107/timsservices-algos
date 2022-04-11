// O. Bittel;
// 22.02.2017

package aufgabe2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * 
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V>
{
	private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	// ...

	/**
	 * Führt eine topologische Sortierung für g durch.
	 * 
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g)
	{
		topSort(g);
	}

	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück, die
	 * topologisch sortiert ist.
	 * 
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList()
	{
		return Collections.unmodifiableList(ts);
	}

	private List<V> topSort(DirectedGraph<V> g)
	{
		Queue<V> q = new LinkedList<>();
		Map<V, Integer> deg = new TreeMap<>();

		for (V v : g.getVertexSet())
		{
			deg.put(v, g.getInDegree(v));
			if (deg.get(v) == 0)
				q.add(v);
		}

		while (!q.isEmpty())
		{
			V v = q.remove();
			ts.add(v);
			for (V w : g.getSuccessorVertexSet(v))
			{
				deg.put(w, deg.get(w) - 1);
				if (deg.get(w) == 0)
					q.add(w);
			}
		}

		if (ts.size() != g.getNumberOfVertexes())
			return null;
		else
			return ts;

	}

	public static void main(String[] args)
	{
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		System.out.println(g);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);

		if (ts.topologicalSortedList() != null)
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]

		DirectedGraph<String> gg = new AdjacencyListDirectedGraph<>();
		gg.addEdge("Unterhose", "Hose");
		gg.addEdge("Socken", "Schuhe");
		gg.addEdge("Unterhemd", "Hemd");
		gg.addEdge("Hose", "Schuhe");
		gg.addEdge("Hose", "Guertel");
		gg.addEdge("Hemd", "Pulli");
		gg.addEdge("Pulli", "Mantel");
		gg.addEdge("Guertel", "Mantel");
		gg.addEdge("Schuhe", "Handschuhe");
		gg.addEdge("Mantel", "Schal");
		gg.addEdge("Schal", "Handschuhe");
		gg.addEdge("Muetze", "Handschuhe");

		TopologicalSort<String> ts2 = new TopologicalSort<>(gg);

		if (ts2.topologicalSortedList() != null)
			System.out.println(ts2.topologicalSortedList());

		gg.addEdge("Schal", "Hose");

		TopologicalSort<String> ts3 = new TopologicalSort<>(gg);

		if (ts3.topologicalSortedList() != null)
			System.out.println(ts3.topologicalSortedList());
	}
}
