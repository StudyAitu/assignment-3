import connectivity.KosarajuSCC;
import connectivity.TopologicalSort;
import graph.Graph;
import graph.GraphGenerator;
import mst.Kruskal;
import mst.Prim;
import shortestpath.Dijkstra;
import java.io.*;
import java.nio.file.*;
import java.util.Locale;

public class Main {
    private static final int RUNS = 5;
    private static final int[] VERTICES = {10, 100, 1000};
    private static final int[] EDGES = {30, 500, 5000};
    private static final String[] SIZES = {"Small", "Medium", "Large"};

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        Path out = Paths.get("results", "tables", "benchmark.csv");
        Files.createDirectories(out.getParent());
        try (PrintWriter csv = new PrintWriter(Files.newBufferedWriter(out))) {
            csv.println("size,vertices,edges,algorithm,avg_time_ns,avg_operations,result,graph_type");
            System.out.println("Smart City benchmark: " + RUNS + " runs per algorithm/configuration");
            System.out.printf("%-8s %-18s %12s %14s %s%n", "Size", "Algorithm", "Avg ns", "Avg ops", "Result/check");
            for (int i=0; i<VERTICES.length; i++) {
                int v=VERTICES[i], e=EDGES[i];
                Graph undirected = GraphGenerator.generateUndirectedConnected(v,e,42);
                benchmarkMst(csv,SIZES[i],v,e,undirected);
                Graph dag = GraphGenerator.generateDirected(v,e,100+ i,true);
                benchmarkTopo(csv,SIZES[i],v,e,dag);
                Graph directed = GraphGenerator.generateDirected(v,e,200+ i,false);
                benchmarkScc(csv,SIZES[i],v,e,directed);
                benchmarkDijkstra(csv,SIZES[i],v,e,undirected);
            }
        }
        System.out.println("\nCSV written to " + out.toString());
        System.out.println("Run python3 plot_results.py to create plots from these measured CSV values.");
    }
    private static void row(PrintWriter csv,String size,int v,int e,String alg,long ns,long ops,String result,String type) {
        csv.printf("%s,%d,%d,%s,%d,%d,\"%s\",%s%n",size,v,e,alg,ns,ops,result.replace("\"","'"),type);
        System.out.printf("%-8s %-18s %12d %14d %s%n",size,alg,ns,ops,result);
    }
    private static void benchmarkMst(PrintWriter csv,String size,int v,int e,Graph g) {
        long kt=0,ko=0,pt=0,po=0; double kw=0,pw=0;
        for(int r=0;r<RUNS;r++) {
            long start=System.nanoTime(); Kruskal k=new Kruskal(g); kt+=System.nanoTime()-start; ko+=k.getOperationsCount(); kw=k.getTotalWeight();
            start=System.nanoTime(); Prim p=new Prim(g); pt+=System.nanoTime()-start; po+=p.getOperationsCount(); pw=p.getTotalWeight();
            if(!k.isSpanningTree(v)||!p.isSpanningTree(v)||Math.abs(k.getTotalWeight()-p.getTotalWeight())>1e-8)
                throw new IllegalStateException("MST correctness check failed for " + size);
        }
        row(csv,size,v,e,"Kruskal",kt/RUNS,ko/RUNS,String.format("weight=%.3f; MST verified",kw),"undirected_connected");
        row(csv,size,v,e,"Prim",pt/RUNS,po/RUNS,String.format("weight=%.3f; MST verified",pw),"undirected_connected");
    }
    private static void benchmarkTopo(PrintWriter csv,String size,int v,int e,Graph g) {
        long time=0,ops=0; TopologicalSort last=null;
        for(int r=0;r<RUNS;r++){ long s=System.nanoTime(); last=new TopologicalSort(g); time+=System.nanoTime()-s; ops+=last.getOperationsCount(); if(last.hasCycle()) throw new IllegalStateException("Generated DAG unexpectedly contains a cycle"); }
        row(csv,size,v,e,"TopologicalSort",time/RUNS,ops/RUNS,"DAG order size="+last.getOrder().size(),"directed_DAG");
    }
    private static void benchmarkScc(PrintWriter csv,String size,int v,int e,Graph g) {
        long time=0,ops=0; KosarajuSCC last=null;
        for(int r=0;r<RUNS;r++){ long s=System.nanoTime(); last=new KosarajuSCC(g); time+=System.nanoTime()-s; ops+=last.getOperationsCount(); }
        int total=last.getSccList().stream().mapToInt(java.util.List::size).sum();
        if(total!=v) throw new IllegalStateException("SCC partition does not contain every vertex exactly once");
        row(csv,size,v,e,"KosarajuSCC",time/RUNS,ops/RUNS,"components="+last.getSccList().size()+"; vertices verified","directed_general");
    }
    private static void benchmarkDijkstra(PrintWriter csv,String size,int v,int e,Graph g) {
        long time=0,ops=0; Dijkstra last=null;
        for(int r=0;r<RUNS;r++){ long s=System.nanoTime(); last=new Dijkstra(g,0); time+=System.nanoTime()-s; ops+=last.getOperationsCount(); }
        row(csv,size,v,e,"Dijkstra",time/RUNS,ops/RUNS,"source=0; reachable="+java.util.Arrays.stream(last.getDistances()).filter(Double::isFinite).count(),"undirected_nonnegative");
    }
}
