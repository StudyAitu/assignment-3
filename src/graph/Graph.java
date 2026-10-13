package graph;

import java.util.*;

public class Graph {
    private final int vertices;
    private final boolean directed;
    private final List<Edge> edges = new ArrayList<>();
    private final List<List<Edge>> adjacencyList;

    public Graph(int vertices, boolean directed) {
        if (vertices < 0) throw new IllegalArgumentException("Vertex count cannot be negative");
        this.vertices = vertices; this.directed = directed;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) adjacencyList.add(new ArrayList<>());
    }
    public void addEdge(int u, int v, double weight) {
        checkVertex(u); checkVertex(v);
        Edge edge = new Edge(u, v, weight);
        edges.add(edge); adjacencyList.get(u).add(edge);
        if (!directed) adjacencyList.get(v).add(new Edge(v, u, weight));
    }
    private void checkVertex(int v) {
        if (v < 0 || v >= vertices) throw new IndexOutOfBoundsException("Invalid vertex: " + v);
    }
    public int getVertices() { return vertices; }
    public boolean isDirected() { return directed; }
    /** Each input edge is stored once here, including for undirected graphs. */
    public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }
    public List<Edge> getAdjacencyList(int u) { checkVertex(u); return Collections.unmodifiableList(adjacencyList.get(u)); }
}
