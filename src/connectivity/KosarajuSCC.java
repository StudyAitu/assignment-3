package connectivity;
import graph.Edge;
import graph.Graph;
import java.util.*;
public class KosarajuSCC {
    private final List<List<Integer>> sccList = new ArrayList<>();
    private long operationsCount;
    public KosarajuSCC(Graph graph) {
        if (!graph.isDirected()) throw new IllegalArgumentException("SCC analysis expects a directed graph");
        int n = graph.getVertices(); boolean[] seen = new boolean[n]; Deque<Integer> order = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (!seen[i]) dfs1(i, seen, order, graph);
        Graph transpose = new Graph(n, true);
        for (int u = 0; u < n; u++) for (Edge e : graph.getAdjacencyList(u)) { transpose.addEdge(e.getV(), e.getU(), e.getWeight()); operationsCount++; }
        Arrays.fill(seen, false);
        while (!order.isEmpty()) {
            int u = order.pop(); operationsCount++;
            if (!seen[u]) { List<Integer> component = new ArrayList<>(); dfs2(u, seen, component, transpose); sccList.add(component); }
        }
    }
    private void dfs1(int u, boolean[] seen, Deque<Integer> order, Graph g) {
        seen[u] = true; operationsCount++;
        for (Edge e : g.getAdjacencyList(u)) { operationsCount++; if (!seen[e.getV()]) dfs1(e.getV(), seen, order, g); }
        order.push(u);
    }
    private void dfs2(int u, boolean[] seen, List<Integer> component, Graph g) {
        seen[u] = true; component.add(u); operationsCount++;
        for (Edge e : g.getAdjacencyList(u)) { operationsCount++; if (!seen[e.getV()]) dfs2(e.getV(), seen, component, g); }
    }
    public List<List<Integer>> getSccList() {
        List<List<Integer>> copy = new ArrayList<>(); for (List<Integer> c : sccList) copy.add(Collections.unmodifiableList(c));
        return Collections.unmodifiableList(copy);
    }
    public long getOperationsCount() { return operationsCount; }
}
