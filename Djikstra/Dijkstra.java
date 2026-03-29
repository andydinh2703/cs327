import java.util.Comparator;

import adjencyListGraph.Edge;
import adjencyListGraph.Graph;
import adjencyListGraph.Vertex;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Dijkstra's algorithm.
 */
public class Dijkstra {

	private HashMap<Vertex, Double> dist_;
	private HashMap<Vertex, Edge> prev_;
	private PriorityQueue<Vertex> pq_;

	private Graph graph_; // the graph
	private EdgeWeight w_; // accessor for edge weights

	/**
	 * Functional interface for edge weights.
	 */
	public interface EdgeWeight {

		/**
		 * Get the weight for edge e.
		 * 
		 * @param e
		 *          edge to get the weight for
		 * @return weight for edge e
		 */
		double weight ( Edge e );
	}

	/**
	 * Ordering of vertices within the priority queue.
	 */
	private class VertComparator implements Comparator<Vertex> {
		@Override
		public int compare ( Vertex u, Vertex v ) {
			// if u > v -> 1
			if(dist_.get(u) > dist_.get(v)){
				return 1;
			}
			// if u < v -> -1
			else if (dist_.get(u) < dist_.get(v)){
				return -1;
			}

			// if u = v -> 0
			else {
				return 0;
			}
		}
	}

	/**
	 * Create an instance of Dijkstra's algorithm to compute shortest paths from s
	 * to all other vertices. Assumes that the weight for edge e is
	 * (Double)e.getObject().
	 * 
	 * @param g
	 *          the graph
	 */
	public Dijkstra ( Graph g ) {
		this(g,e -> ((Double) e.getObject()).doubleValue());
	}

	/**
	 * Create an instance of Dijkstra's algorithm to compute shortest paths from s
	 * to all other vertices.
	 * 
	 * @param g
	 *          the graph
	 * @param w
	 *          function taking an Edge and returning the weight for the edge (as
	 *          a double)
	 */
	public Dijkstra ( Graph g, EdgeWeight w ) {
		graph_ = g;
		w_ = w;
	}

	/**
	 * Initialize the algorithm.
	 * 
	 * @param s
	 *          starting vertex
	 */

	public void init ( Vertex s ) {
		// Create a PriorityQueue and store all vertices in there
		pq_ = new PriorityQueue<>(new VertComparator());

		// Initialize dist_ and prev_ for every vertex
		for (Vertex v : graph_.vertices()){
			dist_.put(v, Double.MAX_VALUE); // use max value to store infinity
			prev_.put(v, null);

			pq_.add(v);
		}

		// Set distance of source vertex
		dist_.put(s, 0.0);
		
	}

	/**
	 * Compute shortest paths from s to all vertices. Requires init(s) to have
	 * been called first.
	 */
	public void run () {
		run(null);
	}

	/**
	 * Compute shortest path from vertex s to vertex f. Requires init(s) to have
	 * been called first.
	 * 
	 * @param f
	 *          finish vertex; if null, compute the shortest path to all vertices
	 */
	public void run ( Vertex f ) {
		throw new UnsupportedOperationException("not yet implemented!");
	}

	/**
	 * Get the vertices on the shortest path from vertex s to vertex f. Requires
	 * run() or run(f) to have been called first.
	 * 
	 * @param f
	 *          finish vertex
	 * @return the vertices on the shortest path from vertex s to vertex f
	 */
	public Iterable<Vertex> getPathVertices ( Vertex f ) {
		throw new UnsupportedOperationException("not yet implemented!");
	}

	/**
	 * Get the edges on the shortest path from vertex s to vertex f. Requires
	 * run() or run(f) to have been called first.
	 * 
	 * @param f
	 *          finish vertex
	 * @return the edges on the shortest path from vertex s to vertex f
	 */
	public Iterable<Edge> getPathEdges ( Vertex f ) {
		throw new UnsupportedOperationException("not yet implemented!");
	}

	/**
	 * Get the length of the shortest path from vertex s to vertex f. Requires
	 * run() or run(f) to have been called first.
	 * 
	 * @param f
	 *          finish vertex
	 * @return the length of the shortest path from vertex s to vertex f
	 */
	public double getDist ( Vertex f ) {
		throw new UnsupportedOperationException("not yet implemented!");
	}

}
