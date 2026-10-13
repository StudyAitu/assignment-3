import csv
from pathlib import Path
import matplotlib.pyplot as plt

CSV = Path('results/tables/benchmark.csv')
OUT = Path('results/plots')
OUT.mkdir(parents=True, exist_ok=True)
if not CSV.exists():
    raise SystemExit('Run `javac -d out $(find src -name "*.java") && java -cp out Main` first.')
with CSV.open(newline='', encoding='utf-8') as f:
    rows = list(csv.DictReader(f))

# MST comparison is meaningful on the same undirected connected graph configurations.
mst = [r for r in rows if r['algorithm'] in ('Kruskal', 'Prim')]
for xfield, xlabel, title, filename in [
    ('vertices', 'Number of vertices (V)', 'Execution Time vs. Number of Vertices', 'time_vs_vertices.png'),
    ('edges', 'Number of edges (E)', 'Execution Time vs. Number of Edges', 'time_vs_edges.png')]:
    fig, ax = plt.subplots(figsize=(8.5, 5.2))
    for alg in ('Kruskal', 'Prim'):
        group = sorted((r for r in mst if r['algorithm'] == alg), key=lambda r: int(r[xfield]))
        ax.plot([int(r[xfield]) for r in group], [int(r['avg_time_ns']) for r in group], marker='o', linewidth=2, label=alg)
    ax.set_title(title); ax.set_xlabel(xlabel); ax.set_ylabel('Average execution time (ns)')
    ax.grid(True, linestyle='--', alpha=.4); ax.legend(); ax.set_xscale('log'); ax.set_yscale('log')
    fig.tight_layout(); fig.savefig(OUT / filename, dpi=180); plt.close(fig)

# Additional algorithm overview plot, clearly labels that graph families differ by algorithm.
fig, ax = plt.subplots(figsize=(9, 5.5))
for alg in ('Kruskal','Prim','TopologicalSort','KosarajuSCC','Dijkstra'):
    group = sorted((r for r in rows if r['algorithm'] == alg), key=lambda r: int(r['vertices']))
    if group: ax.plot([int(r['vertices']) for r in group], [int(r['avg_time_ns']) for r in group], marker='o', label=alg)
ax.set_title('Measured execution time by algorithm (graph families differ)')
ax.set_xlabel('Number of vertices (V)'); ax.set_ylabel('Average execution time (ns)')
ax.set_xscale('log'); ax.set_yscale('log'); ax.grid(True, linestyle='--', alpha=.4); ax.legend()
fig.tight_layout(); fig.savefig(OUT / 'all_algorithms_overview.png', dpi=180); plt.close(fig)
print('Plots created in results/plots/ from actual benchmark.csv measurements.')
