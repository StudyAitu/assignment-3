package mst;
import dsu.DisjointSet;
import graph.Edge;
import graph.Graph;
import java.util.*;

public class Kruskal {
    private final double totalWeight;
    private final List<Edge> mstEdges = new ArrayList<>();
    private long operationsCount;
    public Kruskal(Graph graph) {
        if (graph.isDirected()) throw new IllegalArgumentException("MST requires an undirected graph");
        List<Edge> sorted = new ArrayList<>(graph.getEdges());
        Collections.sort(sorted);
        operationsCount += sorted.size(); // Number of edges supplied to sorting; not a comparison count.
        DisjointSet dsu = new DisjointSet(graph.getVertices());
        double weight = 0;
        for (Edge e : sorted) {
            operationsCount++;
            if (dsu.union(e.getU(), e.getV())) {
                mstEdges.add(e); weight += e.getWeight();
                if (mstEdges.size() == graph.getVertices() - 1) break;
            }
        }
        operationsCount += dsu.getOperationsCount(); totalWeight = weight;
    }
    public double getTotalWeight() { return totalWeight; }
    public List<Edge> getMstEdges() { return Collections.unmodifiableList(mstEdges); }
    public long getOperationsCount() { return operationsCount; }
    public boolean isSpanningTree(int vertices) { return vertices == 0 || mstEdges.size() == vertices - 1; }
}
