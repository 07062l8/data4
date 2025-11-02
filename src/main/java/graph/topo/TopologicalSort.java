package graph.topo;

import util.Graph;
import metrics.Metrics;

import java.util.*;

public class TopologicalSort {
    private final Metrics metrics;

    public TopologicalSort(Metrics metrics) { this.metrics = metrics; }

    public List<String> kahn(Map<String, List<String>> graph) {
        metrics.reset();
        long start = System.nanoTime();

        Map<String, Integer> indeg = new HashMap<>();
        for (String u : graph.keySet()) indeg.put(u, 0);
        for (String u : graph.keySet()) {
            for (String v : graph.get(u)) {
                indeg.put(v, indeg.getOrDefault(v, 0) + 1);
                metrics.incKahnEdges();
            }
        }
        Deque<String> q = new ArrayDeque<>();
        for (Map.Entry<String,Integer> e : indeg.entrySet()) {
            if (e.getValue() == 0) { q.add(e.getKey()); metrics.incKahnPushes(); }
        }
        List<String> order = new ArrayList<>();
        while (!q.isEmpty()) {
            String u = q.remove();
            metrics.incKahnPops();
            order.add(u);
            for (String v : graph.getOrDefault(u, Collections.emptyList())) {
                indeg.put(v, indeg.get(v)-1);
                if (indeg.get(v)==0) { q.add(v); metrics.incKahnPushes(); }
            }
        }
        long end = System.nanoTime();
        metrics.setTimeNano(end - start);

        return order;
    }
}
