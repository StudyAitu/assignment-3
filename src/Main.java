import connectivity.KosarajuSCC;
import connectivity.TopologicalSort;
import graph.Graph;
import graph.GraphGenerator;
import mst.Kruskal;
import mst.Prim;
import shortestpath.Dijkstra;

public class Main {
    public static void main(String[] args) {
        int[] vertices = {10, 100, 1000};
        int[] edges = {30, 500, 5000};
        String[] sizes = {"Small", "Medium", "Large"};

        System.out.println("Running Smart City Experiments (5 runs each)...");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-10s | %-15s | %-15s | %-15s\n", "Size", "Alg", "Avg Time (ns)", "Avg Ops", "Result/Weight");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < vertices.length; i++) {
            int V = vertices[i];
            int E = edges[i];

            long totalKruskalTime = 0, totalKruskalOps = 0;
            long totalPrimTime = 0, totalPrimOps = 0;
            double mstWeight = 0;

            for (int run = 0; run < 5; run++) {
                Graph ug = GraphGenerator.generateUndirectedConnected(V, E, 42 + run);

                long start = System.nanoTime();
                Kruskal k = new Kruskal(ug);
                totalKruskalTime += (System.nanoTime() - start);
                totalKruskalOps += k.getOperationsCount();
                mstWeight = k.getTotalWeight();

                start = System.nanoTime();
                Prim p = new Prim(ug);
                totalPrimTime += (System.nanoTime() - start);
                totalPrimOps += p.getOperationsCount();
            }

            System.out.printf("%-10s | %-10s | %-15d | %-15d | %.2f\n", sizes[i], "Kruskal", totalKruskalTime / 5, totalKruskalOps / 5, mstWeight);
            System.out.printf("%-10s | %-10s | %-15d | %-15d | %.2f\n", sizes[i], "Prim", totalPrimTime / 5, totalPrimOps / 5, mstWeight);
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
}