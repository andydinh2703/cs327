import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import adjacencyListGraph.Edge;
import adjacencyListGraph.Graph;
import adjacencyListGraph.Vertex;

import java.util.ArrayList;
import java.util.Collections;

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
		// initializing hashmaps
		dist_ = new HashMap<>();
		prev_ = new HashMap<>();

		// Create a PriorityQueue and store all vertices in there
		pq_ = new PriorityQueue<>(new VertComparator());

		// Initialize dist_ and prev_ for every vertex
		for (Vertex v : graph_.vertices()){
			// add source vertex
			if (v.equals(s)) {
				dist_.put(v, 0.0);
			}
			else {
				dist_.put(v, Double.MAX_VALUE); // use max value to store infinity
			}
			prev_.put(v, null);
			pq_.add(v);
		}
		
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

		// Repeat until PQ is empty
		while (!pq_.isEmpty()) {

			// Extract the minimum vertex from the PQ
			Vertex current = pq_.poll();

			// If f is extracted, return 
			if (f != null && current.equals(f)) {
				return;
			}

			// Each edge incident to the vertex, get the neighbors
			Iterable<Edge> edges = graph_.incidentEdges(current);

			// For each neighbor
			for (Edge edge : edges){
				// get its neighbor
				Vertex neighbor = graph_.opposite(current, edge);

				// compute new distance
				double new_dist = w_.weight(edge) + dist_.get(current);
			
				// if new distance to that neighbor is smaller than its current value
				// update it
				if (new_dist < dist_.get(neighbor)){
					dist_.put(neighbor, new_dist);
					prev_.put(neighbor, edge);

					// Reorder the PQ
					decreaseKey(neighbor);
				}
			}

		}

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

		// Creating collection to store the vertices
		ArrayList<Vertex> vertices = new ArrayList<>();

		vertices.add(f);

		// walk backward from f to sourse vertex
		Vertex current = f;

		// repeat until reaching source vertex s | pre_ holds null value for distance.
		while (prev_.get(current) != null) {

			// Each vertex, get the edge then get the oppposite neighbor, then add it to the collection
			Edge edge = prev_.get(current);

			Vertex neighbor = graph_.opposite(current, edge);

			vertices.add(neighbor);

			current = neighbor;

		}

		// reverse the collections in place
		Collections.reverse(vertices);

		return vertices;

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
		// a collection to store edges
		ArrayList<Edge> edges = new ArrayList<>();

		Vertex current = f;

		while (prev_.get(current) != null) {
			// get the edge connecting to the vertex
			Edge edge = prev_.get(current);

			// get the neighbor
			Vertex neighbor = graph_.opposite(current, edge);

			// add edge to collection
			edges.add(edge);

			// update current vertex
			current = neighbor;
		}

		// reverse the collection in place
		Collections.reverse(edges);

		return edges;
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
		return dist_.get(f);
	}

	/**
	* Decreasing key helper function 
	* @param v : vertex to decrease key on.
	*/
	private void decreaseKey(Vertex v) {
		pq_.remove(v);
		pq_.add(v);
	}

}
