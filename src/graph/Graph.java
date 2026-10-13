package graph;

import java.util.*;

public class Graph {
    private int vertices;
    private boolean directed;
    private List<Edge> edges;
    private Map<Integer, List<Edge>> adjacencyList;

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, double weight) {
        Edge edge = new Edge(u, v, weight);
        edges.add(edge);
        adjacencyList.get(u).add(edge);
        if (!directed) {
            Edge reverseEdge = new Edge(v, u, weight);
            adjacencyList.get(v).add(reverseEdge);
        }
    }

    public int getVertices() { return vertices; }
    public boolean isDirected() { return directed; }
    public List<Edge> getEdges() { return edges; }
    public List<Edge> getAdjacencyList(int u) { return adjacencyList.get(u); }
}
