package mst;
import graph.Edge;
import graph.Graph;
import java.util.*;

public class Prim {
    private final double totalWeight;
    private final List<Edge> mstEdges = new ArrayList<>();
    private long operationsCount;
    public Prim(Graph graph) {
        if (graph.isDirected()) throw new IllegalArgumentException("MST requires an undirected graph");
        int n = graph.getVertices(); boolean[] visited = new boolean[n];
        double weight = 0;
        if (n == 0) { totalWeight = 0; return; }
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        visited[0] = true;
        for (Edge e : graph.getAdjacencyList(0)) { pq.add(e); operationsCount++; }
        while (!pq.isEmpty() && mstEdges.size() < n - 1) {
            Edge e = pq.poll(); operationsCount++;
            int v = e.getV();
            if (visited[v]) continue;
            visited[v] = true; mstEdges.add(e); weight += e.getWeight();
            for (Edge next : graph.getAdjacencyList(v)) {
                operationsCount++;
                if (!visited[next.getV()]) { pq.add(next); operationsCount++; }
            }
        }
        totalWeight = weight;
    }
    public double getTotalWeight() { return totalWeight; }
    public List<Edge> getMstEdges() { return Collections.unmodifiableList(mstEdges); }
    public long getOperationsCount() { return operationsCount; }
    public boolean isSpanningTree(int vertices) { return vertices == 0 || mstEdges.size() == vertices - 1; }
}