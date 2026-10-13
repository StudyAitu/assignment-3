package mst;

import dsu.DisjointSet;
import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {
    private double totalWeight;
    private List<Edge> mstEdges;
    private long operationsCount;

    public Kruskal(Graph graph) {
        mstEdges = new ArrayList<>();
        totalWeight = 0;
        operationsCount = 0;

        List<Edge> allEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(allEdges);
        operationsCount += (long)(allEdges.size() * Math.log(allEdges.size())); // Sorting ops estimate

        DisjointSet dsu = new DisjointSet(graph.getVertices());

        for (Edge edge : allEdges) {
            operationsCount++; // Edge examination
            if (dsu.union(edge.getU(), edge.getV())) {
                mstEdges.add(edge);
                totalWeight += edge.getWeight();
                if (mstEdges.size() == graph.getVertices() - 1) break;
            }
        }
        operationsCount += dsu.getOperationsCount();
    }

    public double getTotalWeight() { return totalWeight; }
    public List<Edge> getMstEdges() { return mstEdges; }
    public long getOperationsCount() { return operationsCount; }
}