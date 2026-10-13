package dsu;

public class DisjointSet {
    private final int[] parent, rank;
    private long operationsCount;
    public DisjointSet(int n) {
        if (n < 0) throw new IllegalArgumentException("Size cannot be negative");
        parent = new int[n]; rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }
    public int find(int i) {
        check(i); operationsCount++;
        if (parent[i] != i) parent[i] = find(parent[i]);
        return parent[i];
    }
    public boolean union(int i, int j) {
        operationsCount++;
        int a = find(i), b = find(j);
        if (a == b) return false;
        if (rank[a] < rank[b]) parent[a] = b;
        else if (rank[a] > rank[b]) parent[b] = a;
        else { parent[b] = a; rank[a]++; }
        return true;
    }
    private void check(int i) { if (i < 0 || i >= parent.length) throw new IndexOutOfBoundsException("Invalid DSU index: " + i); }
    public long getOperationsCount() { return operationsCount; }
}
