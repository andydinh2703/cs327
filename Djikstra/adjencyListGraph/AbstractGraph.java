/*
* Author : Andy Dinh 
*/

package adjencyListGraph;

import java.util.ArrayList;

/**
 * This class provides a skeletal implementation of Graph to reduce the effort
 * needed for a concrete implementation.
 */
public abstract class AbstractGraph implements Graph {
	private DoublyLinkedList vertices_;
	private DoublyLinkedList edges_;

	// Constructor
	public AbstractGraph() {
		vertices_ = new DoublyLinkedList();
		edges_ = new DoublyLinkedList();
	}
	

	/**
	 * This class provides a skeletal implementation of Edge to reduce the effort
	 * needed for a concrete implementation.
	 */
	protected class AbstractEdge implements Edge {
		private Object obj_;
		private Vertex v_;
		private Vertex w_;

		public AbstractEdge(Object obj, Vertex v, Vertex w) {
			obj_ = obj;
			v_ = v;
			w_ = w;
		}

		@Override
		public Object getObject () {
			return obj_;
		}

		protected Vertex getW(){
			return w_;
		}

		protected Vertex getV() {
			return v_;
		}

	}

	/**
	 * This class provides a skeletal implementation of Vertex to reduce the
	 * effort needed for a concrete implementation.
	 */
	protected class AbstractVertex implements Vertex {
		private Object obj_;

		public AbstractVertex(Object obj) {
			obj_ = obj;
		}

		@Override
		public Object getObject () {
			return obj_;
		}

	}

	protected class ListNode {
		private Object value_;
		private ListNode prev_;
		private ListNode next_;

		public ListNode(Object value) {
			value_ = value;
			prev_ = null;
			next_ = null;
		}

		public ListNode getPrevious(){
			return prev_;
		}

		public ListNode getNext(){
			return next_;
		}


		public void setNext(ListNode node) {
			next_ = node;
		}

		public void setPrevious(ListNode node) {
			prev_ = node;
		}

		
	}

	protected class DoublyLinkedList{
		private ListNode head_;
		private ListNode tail_;
		private int size_;

		public DoublyLinkedList() {
			head_ = null;
			tail_ = null;
			size_ = 0;

		}

		public int getSize(){
			return size_;
		}

		// customed iterator for incident edges
		public Iterable<Object> iterator(){
			ListNode node = head_;
			ArrayList<Object> node_values = new ArrayList<>();

			while (node != null) {
				node_values.add((Object) node.value_);
				node = node.getNext();
			}

			return node_values;
		}

		public void addNode(ListNode node){
			if (node == null) {
				throw new IllegalArgumentException("Can't insert a non-existent node!");
			}
			// empty list
			if (head_ == null) {
				head_ = node;
				tail_ = node;
			}

			// non-empty list
			else {
				node.setNext(head_);
				head_.setPrevious(node);
				head_ = node;
			}
			size_++;

		}

		public void removeNode(ListNode node){
			if (node == null) {
				throw new IllegalArgumentException("Can't remove a non-existent node!");
			}

			if (head_ == null) {
				throw new IllegalArgumentException("Can't remove from an empty list.");
			}

			// node is at head
			if (node == head_) {
				// head is the only node
				if (node.getNext() == null) {
					head_ = null;
					tail_ = null;
				}
				else {
					head_ = node.getNext();
					head_.setPrevious(null);
				}
				
			}
			else if (node == tail_) {
				tail_ = node.getPrevious();
				tail_.setNext(null);
			}
			else {
				ListNode prev = node.getPrevious();
				ListNode next = node.getNext();
				prev.setNext(next);
				next.setPrevious(prev);
			}
			size_--;
		}
	}

	
	public Vertex aVertex () {
		// precondition : vertices is not empty
		if (vertices_.head_ == null) {
			throw new IllegalArgumentException("Can't get a vertex from an empty graph.");
		}
		return (Vertex) vertices_.head_.value_;
	}

	// Add vertex 
	protected void addVertex(ListNode node) {

		vertices_.addNode(node);
	}

	// remove vertex helper
	protected void removeVertexList(ListNode node) {
		vertices_.removeNode(node);
	}

	// Add edge 
	protected void addEdge(ListNode node) {
		edges_.addNode(node);
	}

	// remove edge from list
	protected void removeEdgeList(ListNode node){
		edges_.removeNode(node);
	}

	public Iterable<Vertex> vertices () {
		// creating array list to store iterable
		ArrayList<Vertex> vertices = new ArrayList<>();
		for (Object vertex : vertices_.iterator()){
			// casting each vertex Object to Vertex
			vertices.add((Vertex) vertex);
		}

		return vertices;
	}

	public Vertex opposite ( Vertex v, Edge e ) {
		// precondition: v and e are not null
		if (v == null || e == null) {
			throw new IllegalArgumentException("One of the vertices is null.");
		}

		Vertex[] endVertices = endVertices(e);
		if (endVertices[0] == v) {
			return endVertices[1];
		} 
		else if (endVertices[1] == v) {
			return endVertices[0];
		}
		else {
			throw new IllegalArgumentException("v isn't an endpoint vertex of e.");
		}
	}

	public Vertex[] endVertices ( Edge e ) {
		// precondition: e is not null
		if (e == null) throw new IllegalArgumentException("e must not be null.");

		AbstractEdge vw = (AbstractEdge) e;
		Vertex[] vertices = new Vertex[2];
		vertices[0] = vw.getV();
		vertices[1] = vw.getW();

		return vertices;
		
	}

	public Iterable<Edge> edges () {
		// creating array list to store iterable
		ArrayList<Edge> edges = new ArrayList<>();
		for(Object edge : edges_.iterator()){
			// casting each edge object to Edge, then add to the arraylist
			edges.add((Edge) edge);
		}

		return edges;
	}

	public int numEdges () {
		return edges_.getSize();
	}

	public int numVertices () {
		return vertices_.getSize();
	}

	public boolean areAdjacent ( Vertex v, Vertex w ) {
		// precondition: v and e are not null
		if (v == null || w == null) {
			throw new IllegalArgumentException("One of the vertices is null.");
		}
		// interating through the incident edges of v
			// check if w connects to v
		for(Edge edge : incidentEdges(v)) {
			if (opposite(v, edge) == w) {
				return true;
			}
		}
		return false;
	}
}
