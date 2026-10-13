package connectivity;
import graph.Edge;
import graph.Graph;
import java.util.*;

public class TopologicalSort {
    private final List<Integer> order = new ArrayList<>();
    private final boolean hasCycle;
    private long operationsCount;
    public TopologicalSort(Graph graph) {
        if (!graph.isDirected()) throw new IllegalArgumentException("Topological sort requires a directed graph");
        int n = graph.getVertices(); int[] indegree = new int[n];
        for (int u = 0; u < n; u++) for (Edge e : graph.getAdjacencyList(u)) { indegree[e.getV()]++; operationsCount++; }
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indegree[i] == 0) q.add(i);
        while (!q.isEmpty()) {
            int u = q.remove(); order.add(u); operationsCount++;
            for (Edge e : graph.getAdjacencyList(u)) {
                operationsCount++;
                if (--indegree[e.getV()] == 0) q.add(e.getV());
            }
        }
        hasCycle = order.size() != n;
    }
    public List<Integer> getOrder() { return Collections.unmodifiableList(order); }
    public boolean hasCycle() { return hasCycle; }
    public long getOperationsCount() { return operationsCount; }
}
