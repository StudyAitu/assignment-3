package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class KosarajuSCC {
    private List<List<Integer>> sccList;
    private long operationsCount;

    public KosarajuSCC(Graph graph) {
        sccList = new ArrayList<>();
        operationsCount = 0;
        int V = graph.getVertices();

        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        // Step 1: Fill order
        for (int i = 0; i < V; i++) {
            operationsCount++;
            if (!visited[i]) {
                dfs1(i, visited, stack, graph);
            }
        }

        // Step 2: Transpose graph
        Graph transposed = new Graph(V, true);
        for (int u = 0; u < V; u++) {
            for (Edge edge : graph.getAdjacencyList(u)) {
                transposed.addEdge(edge.getV(), edge.getU(), edge.getWeight());
                operationsCount++;
            }
        }

        // Step 3: DFS on transposed
        Arrays.fill(visited, false);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            operationsCount++;
            if (!visited[u]) {
                List<Integer> component = new ArrayList<>();
                dfs2(u, visited, component, transposed);
                sccList.add(component);
            }
        }
    }

    private void dfs1(int u, boolean[] visited, Stack<Integer> stack, Graph graph) {
        visited[u] = true;
        operationsCount++;
        for (Edge edge : graph.getAdjacencyList(u)) {
            operationsCount++;
            if (!visited[edge.getV()]) {
                dfs1(edge.getV(), visited, stack, graph);
            }
        }
        stack.push(u);
    }

    private void dfs2(int u, boolean[] visited, List<Integer> component, Graph graph) {
        visited[u] = true;
        operationsCount++;
        component.add(u);
        for (Edge edge : graph.getAdjacencyList(u)) {
            operationsCount++;
            if (!visited[edge.getV()]) {
                dfs2(edge.getV(), visited, component, graph);
            }
        }
    }

    public List<List<Integer>> getSccList() { return sccList; }
    public long getOperationsCount() { return operationsCount; }
}