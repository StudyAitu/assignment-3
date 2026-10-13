package shortestpath;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class Dijkstra {
    private double[] distances;
    private int[] parent;
    private long operationsCount;

    public Dijkstra(Graph graph, int source) {
        int V = graph.getVertices();
        distances = new double[V];
        parent = new int[V];
        Arrays.fill(distances, Double.MAX_VALUE);
        Arrays.fill(parent, -1);
        distances[source] = 0;
        operationsCount = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
        pq.add(new Node(source, 0));

        boolean[] visited = new boolean[V];

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            operationsCount++;
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge edge : graph.getAdjacencyList(u)) {
                operationsCount++;
                int v = edge.getV();
                double weight = edge.getWeight();

                if (weight < 0) {
                    throw new IllegalArgumentException("Dijkstra does not support negative edge weights!");
                }

                if (!visited[v] && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    parent[v] = u;
                    pq.add(new Node(v, distances[v]));
                    operationsCount++;
                }
            }
        }
    }

    public double[] getDistances() { return distances; }
    public long getOperationsCount() { return operationsCount; }

    private static class Node {
        int vertex;
        double distance;
        Node(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}