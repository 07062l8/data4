package graph.scc;

import util.Graph;
import metrics.Metrics;

import java.util.*;

public class TarjanSCC {
    private final Graph g;
    private final Metrics metrics;

    public TarjanSCC(Graph g, Metrics metrics) {
        this.g = g;
        this.metrics = metrics;
    }

    public List<List<String>> run() {
        metrics.reset();
        long start = System.nanoTime();
        Map<String,Integer> index = new HashMap<>();
        Map<String,Integer> lowlink = new HashMap<>();
        Deque<String> stack = new ArrayDeque<>();
        Set<String> onStack = new HashSet<>();
        List<List<String>> sccs = new ArrayList<>();
        int[] idx = {0};

        for (String v : g.nodes()) {
            if (!index.containsKey(v)) {
                strongconnect(v, idx, index, lowlink, stack, onStack, sccs);
            }
        }
        long end = System.nanoTime();
        metrics.setTimeNano(end - start);
        return sccs;
    }

    private void strongconnect(String v, int[] idx, Map<String,Integer> index, Map<String,Integer> lowlink,
                               Deque<String> stack, Set<String> onStack, List<List<String>> sccs) {
        index.put(v, idx[0]);
        lowlink.put(v, idx[0]);
        idx[0]++;
        stack.push(v);
        onStack.add(v);
        metrics.incDfsVisits();

        for (String w : g.neighbors(v)) {
            metrics.incDfsEdges();
            if (!index.containsKey(w)) {
                strongconnect(w, idx, index, lowlink, stack, onStack, sccs);
                lowlink.put(v, Math.min(lowlink.get(v), lowlink.get(w)));
            } else if (onStack.contains(w)) {
                lowlink.put(v, Math.min(lowlink.get(v), index.get(w)));
            }
        }

        if (lowlink.get(v).equals(index.get(v))) {
            List<String> comp = new ArrayList<>();
            String w;
            do {
                w = stack.pop();
                onStack.remove(w);
                comp.add(w);
            } while (!w.equals(v));
            sccs.add(comp);
        }
    }
}

