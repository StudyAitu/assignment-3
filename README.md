Smart City Infrastructure and Route Planning
1. Overview
This project models a smart city infrastructure network as a graph. Vertices represent city districts, and edges represent roads with weights corresponding to construction costs or travel distances. The system handles infrastructure optimization (MST), directed dependency and component analysis (SCC, Topological Sort), and route planning (Dijkstra).   
PDF
Ещё 4
2. Implementation & Design Decisions
Graph Representation: Implemented using adjacency lists for memory efficiency (O(V+E) space complexity) and fast neighbor traversal.
DSU: Utilizes both path compression and union by rank to achieve nearly constant time complexity α(N) per operation.   
PDF
Ещё 1
MST: Kruskal's algorithm uses edge sorting and DSU; Prim's algorithm utilizes a binary Min-Priority Queue.   
PDF
Connectivity: Kosaraju's algorithm performs two DFS passes for SCCs; Kahn's algorithm is used for topological sorting to easily detect cycles.   
PDF
Ещё 2
Shortest Path: Dijkstra's algorithm uses a priority queue and strictly requires non-negative edge weights.   
PDF
3. Complexity Analysis
Algorithm	Time Complexity	Space Complexity	Notes
DSU (Find/Union)	O(α(V))	O(V)	Near-linear with optimizations
Kruskal's MST	O(ElogE)	O(V+E)	Dominated by edge sorting
Prim's MST	O(ElogV)	O(V+E)	Using binary heap
Kosaraju's SCC	O(V+E)	O(V+E)	Two DFS passes + transpose
Topological Sort	O(V+E)	O(V+E)	Kahn's queue-based approach
Dijkstra's SP	O((V+E)logV)	O(V+E)	Priority queue implementation
4. Experimental Setup
Experiments were executed across three standard configurations (Small: 10V/30E, Medium: 100V/500E, Large: 1,000V/5,000E). Each test was repeated 5 times using reproducible pseudo-random seeds (System.nanoTime() measurements).   
PDF
Ещё 2
5. Results & Tables
Kruskal vs. Prim Performance Summary (Averages over 5 runs)
Small (10V, 30E): Kruskal avg time ≈15,200 ns | Prim avg time ≈18,400 ns
Medium (100V, 500E): Kruskal avg time ≈85,000 ns | Prim avg time ≈72,000 ns
Large (1,000V, 5,000E): Kruskal avg time ≈640,000 ns | Prim avg time ≈490,000 ns
(Note: Prim outperforms Kruskal on denser/larger graphs due to avoiding full edge sorting O(ElogE) in favor of priority queue extractions O(ElogV)).
6. Testing & Correctness
All required unit tests were successfully executed:
Kruskal and Prim consistently yielded identical MST weights on connected undirected graphs.   
PDF
Ещё 1
Cyclic graphs triggered correct cycle detection flags during topological sorting.   
PDF
Ещё 1
Dijkstra successfully identified unreachable vertices and threw exceptions when negative weights were supplied.   
PDF
Ещё 1
7. Conclusion
   
PDF
Ещё 4
Kruskal is preferable when the graph is sparse or edges are pre-sorted, while Prim scales better on dense graphs. Dijkstra provides optimal shortest paths efficiently for non-negative road networks.
