package mst;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class Prim {
    private double totalWeight;
    private List<Edge> mstEdges;
    private long operationsCount;

    public Prim(Graph graph) {
        mstEdges = new ArrayList<>();
        totalWeight = 0;
        operationsCount = 0;

        int V = graph.getVertices();
        boolean[] visited = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // Start from vertex 0
        visited[0] = true;
        for (Edge edge : graph.getAdjacencyList(0)) {
            pq.add(edge);
            operationsCount++;
        }

        while (!pq.isEmpty() && mstEdges.size() < V - 1) {
            Edge edge = pq.poll();
            operationsCount++; // Priority queue pop

            int u = edge.getU();
            int v = edge.getV();

            if (visited[v]) continue;

            visited[v] = true;
            mstEdges.add(edge);
            totalWeight += edge.getWeight();

            for (Edge nextEdge : graph.getAdjacencyList(v)) {
                operationsCount++;
                if (!visited[nextEdge.getV()]) {
                    pq.add(nextEdge);
                    operationsCount++; // PQ insert
                }
            }
        }
    }

    public double getTotalWeight() { return totalWeight; }
    public long getOperationsCount() { return operationsCount; }
}