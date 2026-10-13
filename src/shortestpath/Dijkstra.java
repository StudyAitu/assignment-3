package shortestpath;
import graph.Edge;
import graph.Graph;
import java.util.*;

public class Dijkstra {
    private final double[] distances;
    private final int[] parent;
    private long operationsCount;
    public Dijkstra(Graph graph, int source) {
        int n = graph.getVertices();
        if (source < 0 || source >= n) throw new IllegalArgumentException("Source vertex is outside the graph");
        for (Edge e : graph.getEdges()) if (e.getWeight() < 0) throw new IllegalArgumentException("Dijkstra does not support negative edge weights");
        distances = new double[n]; parent = new int[n]; Arrays.fill(distances, Double.POSITIVE_INFINITY); Arrays.fill(parent, -1); distances[source] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a.distance)); pq.add(new Node(source, 0));
        boolean[] settled = new boolean[n];
        while (!pq.isEmpty()) {
            Node cur = pq.poll(); operationsCount++;
            int u = cur.vertex; if (settled[u]) continue; settled[u] = true;
            if (cur.distance > distances[u]) continue;
            for (Edge e : graph.getAdjacencyList(u)) {
                operationsCount++; int v = e.getV(); double candidate = distances[u] + e.getWeight();
                if (!settled[v] && candidate < distances[v]) { distances[v] = candidate; parent[v] = u; pq.add(new Node(v, candidate)); operationsCount++; }
            }
        }
    }
    public double[] getDistances() { return distances.clone(); }
    public long getOperationsCount() { return operationsCount; }
    public List<Integer> getPathTo(int target) {
        if (target < 0 || target >= distances.length) throw new IndexOutOfBoundsException("Invalid target: " + target);
        if (Double.isInfinite(distances[target])) return Collections.emptyList();
        LinkedList<Integer> path = new LinkedList<>();
        for (int v = target; v != -1; v = parent[v]) path.addFirst(v);
        return Collections.unmodifiableList(path);
    }
    private static final class Node { final int vertex; final double distance; Node(int v, double d) { vertex=v; distance=d; } }
}
