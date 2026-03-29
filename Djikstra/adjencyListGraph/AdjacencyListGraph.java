/*
* Author : Andy Dinh 
*/

package adjencyListGraph;

import java.util.ArrayList;

/**
 * Adjacency list implementation of Graph.
 */

public class AdjacencyListGraph extends AbstractGraph {

	public AdjacencyListGraph () {
		super();
	}

	protected class AdjacencyListEdge extends AbstractEdge {
		// 3 references for direct access
		private ListNode edge_global;
		private ListNode edge_in_v;
		private ListNode edge_in_w;

		public AdjacencyListEdge(Object obj, Vertex v, Vertex w) {
			super(obj, v, w);
		}
		

	}

	protected class AdjacencyListVertex extends AbstractVertex {
		private DoublyLinkedList incidentEdges_;
		private ListNode vertex_global;

		public AdjacencyListVertex(Object obj) {
			super(obj);
			incidentEdges_ = new DoublyLinkedList();
		}

		protected DoublyLinkedList getIncidentEdges(){
			return incidentEdges_;
		}
	}

	public Edge insertEdge ( Vertex v, Vertex w, Object obj ) {
		// precondition: v and e are not null
		if (v == null || w == null) {
			throw new IllegalArgumentException("One of the vertices is null.");
		}

		// casting
		AdjacencyListEdge new_edge = new AdjacencyListEdge(obj, v, w);

		// creating node
		ListNode edge_node = new ListNode(new_edge);

		// set global reference
		new_edge.edge_global = edge_node;

		// add incident edges for v
		// set references of v
		AdjacencyListVertex v_adj = (AdjacencyListVertex) v;
		ListNode edge_node_v = new ListNode(new_edge);
		v_adj.incidentEdges_.addNode(edge_node_v);
		new_edge.edge_in_v =  edge_node_v;

		// add incident edges for w
		// set references of w
		AdjacencyListVertex w_adj = (AdjacencyListVertex) w;
		ListNode edge_node_w = new ListNode(new_edge);
		w_adj.incidentEdges_.addNode(edge_node_w);
		new_edge.edge_in_w = edge_node_w;

		// add node to global edges
		addEdge(edge_node);

		return new_edge;
	}

	public Vertex insertVertex ( Object obj ) {
		AdjacencyListVertex new_vertex = new AdjacencyListVertex(obj);
		ListNode vertex_node = new ListNode(new_vertex);
		new_vertex.vertex_global = vertex_node;
		addVertex(vertex_node);
		return new_vertex;
	}

	public Iterable<Edge> incidentEdges ( Vertex v ) {
		// precondition: 
		if (v == null) {
			throw new IllegalArgumentException("v must not be null.");
		}

		AdjacencyListVertex adj_v = (AdjacencyListVertex) v;
		ArrayList<Edge> incidentEdges = new ArrayList<>();
		for (Object inci_edge : adj_v.incidentEdges_.iterator()) {
			incidentEdges.add((Edge) inci_edge);
		}
		return incidentEdges;
	}

	public Iterable<Vertex> adjacentVertices ( Vertex v ) {
		// precondition
		if (v == null) {
			throw new IllegalArgumentException("v must not be null.");
		}

		// casting to Adjacency List Vertex 
		AdjacencyListVertex v_adj = (AdjacencyListVertex) v;
		
		ArrayList<Vertex> adjacentVertices = new ArrayList<>();
		for (Edge edge : incidentEdges(v_adj)) {
			Vertex neighbor = opposite(v_adj, edge);
			adjacentVertices.add(neighbor);
		}

		return adjacentVertices;
		
	}

	public int degree ( Vertex v ) {
		// precondition
		if (v == null) {
			throw new IllegalArgumentException("v must not be null.");
		}

		AdjacencyListVertex v_adj = (AdjacencyListVertex) v;

		return v_adj.incidentEdges_.getSize();
	}

	public void removeEdge ( Edge e ) {
		// precondition
		if (e == null) {
			throw new IllegalArgumentException("e must not be null.");
		}


		// casting to AdjacencyListEdge
		AdjacencyListEdge edge = (AdjacencyListEdge) e;

		// remove from global edgesq
		removeEdgeList(edge.edge_global);

		// remove the edge from the global edge reference
		edge.edge_global = null;
		
		// get the end points of the edge
		AbstractEdge e_abs = (AbstractEdge) e;

		Vertex w_endpoint = e_abs.getW();
		Vertex v_endpoint = e_abs.getV();

		// get incident edges of each point
			// remove the edge from each endpoint
		AdjacencyListVertex w_adj = (AdjacencyListVertex) w_endpoint;
		w_adj.getIncidentEdges().removeNode(edge.edge_in_w);
		edge.edge_in_w = null;

		AdjacencyListVertex v_adj = (AdjacencyListVertex) v_endpoint;
		v_adj.getIncidentEdges().removeNode(edge.edge_in_v);
		edge.edge_in_v = null;

		
	}

	public void removeVertex ( Vertex v ) {
		// precondition
		if (v == null) {
			throw new IllegalArgumentException("v must not be null.");
		}

		AdjacencyListVertex v_adj = (AdjacencyListVertex) v;
		// casting an iterator to get the snapshot to iterate through edges
		ArrayList<Edge> incidentEdges = new ArrayList<>();
		for(Object edge : v_adj.incidentEdges_.iterator()) {
			incidentEdges.add((Edge) edge);
		}
		// Iterate through incident edges
		for( Edge edge : incidentEdges){
			// Remove the actual edge
			removeEdge(edge);
		}
		// Delete that vertex from global verticies
		removeVertexList(v_adj.vertex_global);
	}
	


}
