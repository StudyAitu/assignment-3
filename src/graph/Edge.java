package graph;

public final class Edge implements Comparable<Edge> {
    private final int u, v;
    private final double weight;

    public Edge(int u, int v, double weight) {
        if (!Double.isFinite(weight)) throw new IllegalArgumentException("Edge weight must be finite");
        this.u = u; this.v = v; this.weight = weight;
    }
    public int getU() { return u; }
    public int getV() { return v; }
    public double getWeight() { return weight; }
    @Override public int compareTo(Edge other) { return Double.compare(weight, other.weight); }
}