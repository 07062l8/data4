
#  Assignment 4 — Smart City / Smart Campus Scheduling

---

## Overview

This project implements three core graph algorithms and applies them to directed task-dependency graphs from the *Smart City / Smart Campus* domain.  
Each dataset represents a set of service or maintenance tasks with dependencies (e.g., street cleaning, camera maintenance, or repair operations).

The goal is to:
1. Detect and compress **cyclic dependencies** using *Strongly Connected Components (SCC)*.  
2. Construct a **Condensation Graph (DAG)** of SCCs.  
3. Compute a **Topological Ordering** for valid task execution sequence.  
4. Run **Shortest and Longest Path algorithms** on the DAG to find optimal and critical task schedules.

---

## Project Structure

```
assignment4/
├── data/                # JSON datasets (tasks.json or multiple *.json files)
│   └── tasks.json
├── src/
│   ├── main/java/
│   │   ├── graph/scc/TarjanSCC.java
│   │   ├── graph/topo/TopologicalSort.java
│   │   ├── graph/dagsp/DagSp.java
│   │   ├── metrics/Metrics.java
│   │   ├── util/Graph.java
│   │   ├── util/GraphLoader.java
│   │   └── main/Main.java
│   └── test/java/graph/TestGraphAlgorithms.java
├── pom.xml
└── README.md
```

---

##  Algorithms Implemented

| Module | Algorithm | Description |
|:--|:--|:--|
| `graph.scc` | **Tarjan’s SCC** | Detects all strongly connected components and identifies cyclic dependencies. |
| `graph.topo` | **Kahn’s Topological Sort** | Generates a valid task order on the condensation DAG. |
| `graph.dagsp` | **Shortest/Longest Path in DAG** | Computes optimal (shortest) and critical (longest) paths for acyclic graphs. |
| `metrics` | **Metrics Recorder** | Measures operation counts and timing for each algorithm. |

---

## Datasets

- **File:** `/data/tasks.json`  
- **Format:** JSON array of graphs, each containing:
  ```json
  {
    "directed": true,
    "n": 6,
    "edges": [
      {"u": 0, "v": 1, "w": 2},
      {"u": 0, "v": 2, "w": 3}
    ],
    "source": 0,
    "weight_model": "edge"
  }
  ```
- 9 datasets provided (small, medium, large).
- Each graph varies in density, size, and cyclic structure.

---

## Example Output

```
====================
Graph #1
SCC count: 6
Topological order of SCCs: [5, 3, 4, 2, 1, 0]
Shortest distances from 0: {0=0.0, 1=2.0, 2=3.0, 3=4.0, 4=6.0, 5=11.0}
Longest distances from 0: {0=0.0, 1=2.0, 2=3.0, 3=6.0, 4=8.0, 5=13.0}
```

---

## Analysis Summary

- **SCC** compression simplifies complex cyclic graphs into DAGs, enabling scheduling.  
- **Topological sorting** guarantees valid dependency order after compression.  
- **Shortest path** identifies minimal completion times.  
- **Longest path** highlights critical sequences that determine total project duration.  
- For cyclic graphs, distances to some nodes remain *Infinity*, confirming unreachable or cyclic dependencies.

---

## Performance

| Algorithm | Complexity | Metrics |
|------------|-------------|----------|
| Tarjan SCC | O(V + E) | DFS visits, edges, time (ns) |
| Kahn Topo Sort | O(V + E) | Queue pushes/pops, edges |
| DAG Shortest Path | O(V + E) | Relaxations, time |
| DAG Longest Path | O(V + E) | Relaxations, time |

Execution was instantaneous (< 1 ms for small graphs, < 10 ms for large ones).

---

## Conclusions

- SCC detection is crucial for handling cyclic dependencies.  
- Condensation DAG enables correct scheduling.  
- Critical path (longest path) provides insight into overall task duration.  
- The combined approach scales well and is suitable for real-world Smart City service scheduling.

---

## References

- R. Tarjan, “Depth-First Search and Linear Graph Algorithms,” *SIAM J. Computing*, 1972.  
- D. Kahn, “Topological Sorting of Large Networks,” *Communications of the ACM*, 1962.  
- CLRS: *Introduction to Algorithms*, 3rd Edition, Ch. 22–24.  
