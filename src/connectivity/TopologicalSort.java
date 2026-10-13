package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class TopologicalSort {
    private List<Integer> order;
    private boolean hasCycle;
    private long operationsCount;

    public TopologicalSort(Graph graph) {
        order = new ArrayList<>();
        hasCycle = false;
        operationsCount = 0;

        int V = graph.getVertices();
        int[] inDegree = new int[V];

        for (int u = 0; u < V; u++) {
            for (Edge edge : graph.getAdjacencyList(u)) {
                inDegree[edge.getV()]++;
                operationsCount++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);
            operationsCount++;

            for (Edge edge : graph.getAdjacencyList(u)) {
                operationsCount++;
                int v = edge.getV();
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        if (order.size() != V) {
            hasCycle = true;
        }
    }

    public List<Integer> getOrder() { return order; }
    public boolean hasCycle() { return hasCycle; }
    public long getOperationsCount() { return operationsCount; }
}
