package graph;

import util.Graph;
import metrics.Metrics;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestGraphAlgorithms {

    @Test
    public void testSccAndTopoSimple() {
        Graph g = new Graph();
        g.addEdge("A","B",1.0);
        g.addEdge("B","C",1.0);
        g.addEdge("C","A",1.0); // cycle A-B-C
        g.addEdge("B","D",1.0);
        g.addEdge("D","E",1.0);

        Metrics m = new Metrics();
        TarjanSCC tarjan = new TarjanSCC(g, m);
        List<List<String>> sccs = tarjan.run();
        // expect one SCC of size 3 (A,B,C) and two singletons (D,E)
        boolean foundABC = false;
        for (List<String> comp : sccs) {
            if (comp.contains("A") && comp.contains("B") && comp.contains("C")) foundABC = true;
        }
        assertTrue(foundABC);

        // build condensation adjacency
        Map<String, List<String>> cond = new HashMap<>();
        for (int i=0;i<sccs.size();i++) cond.put(String.valueOf(i), new ArrayList<>());
        Map<String,Integer> compOf = new HashMap<>();
        for (int i=0;i<sccs.size();i++) for (String v: sccs.get(i)) compOf.put(v,i);
        for (String u : g.nodes()) for (String v : g.neighbors(u)) {
            int cu = compOf.get(u), cv = compOf.get(v);
            if (cu!=cv) cond.get(String.valueOf(cu)).add(String.valueOf(cv));
        }

        TopologicalSort topo = new TopologicalSort(m);
        List<String> order = topo.kahn(cond);
        assertEquals(order.size(), cond.size());
    }
}
