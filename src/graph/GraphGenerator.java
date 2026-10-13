package graph;

import java.util.Random;

public class GraphGenerator {
    public static Graph generateUndirectedConnected(int V, int E, long seed) {
        Random rand = new Random(seed);
        Graph graph = new Graph(V, false);

        // Ensure connectivity using a spanning tree first
        for (int i = 1; i < V; i++) {
            int parent = rand.nextInt(i);
            double weight = 1 + rand.nextDouble() * 99;
            graph.addEdge(parent, i, weight);
        }

        // Add remaining random edges
        int addedEdges = V - 1;
        while (addedEdges < E) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);
            if (u != v) {
                double weight = 1 + rand.nextDouble() * 99;
                graph.addEdge(u, v, weight);
                addedEdges++;
            }
        }
        return graph;
    }

    public static Graph generateDirected(int V, int E, long seed, boolean acyclic) {
        Random rand = new Random(seed);
        Graph graph = new Graph(V, true);

        int addedEdges = 0;
        while (addedEdges < E) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);
            if (u != v && (!acyclic || u < v)) {
                double weight = 1 + rand.nextDouble() * 99;
                graph.addEdge(u, v, weight);
                addedEdges++;
            }
        }
        return graph;
    }
}