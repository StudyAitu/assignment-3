package graph;
import java.util.Random;

public final class GraphGenerator {
    private GraphGenerator() {}
    public static Graph generateUndirectedConnected(int v, int e, long seed) {
        if (v < 1) throw new IllegalArgumentException("Connected graph requires at least one vertex");
        long max = (long)v * (v - 1) / 2;
        if (e < v - 1 || e > max) throw new IllegalArgumentException("For a simple connected graph, V-1 <= E <= V(V-1)/2");
        Random r = new Random(seed); Graph g = new Graph(v, false);
        for (int i = 1; i < v; i++) g.addEdge(r.nextInt(i), i, 1 + r.nextDouble() * 99);
        int count = v - 1; // Preserve requested edge count; parallel edges may occur in this generator.
        while (count < e) { int a=r.nextInt(v), b=r.nextInt(v); if (a != b) { g.addEdge(a,b,1+r.nextDouble()*99); count++; } }
        return g;
    }
    public static Graph generateDirected(int v, int e, long seed, boolean acyclic) {
        if (v < 0) throw new IllegalArgumentException("Vertex count cannot be negative");
        long max = acyclic ? (long)v*(v-1)/2 : (long)v*(v-1);
        if (e < 0 || e > max) throw new IllegalArgumentException("Requested edge count exceeds the simple directed graph limit");
        Random r = new Random(seed); Graph g = new Graph(v, true); int count=0; int attempts=0;
        while (count < e) {
            int a=r.nextInt(v), b=r.nextInt(v); boolean allowed = a != b && (!acyclic || a < b);
            if (allowed) { g.addEdge(a,b,1+r.nextDouble()*99); count++; }
            if (++attempts > Math.max(1000, e*100)) throw new IllegalStateException("Unable to generate requested graph");
        }
        return g;
    }
}
