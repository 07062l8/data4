package graph.dagsp;

import util.Graph;
import metrics.Metrics;

import java.util.*;

public class DagSp {
    private final Graph g;
    private final metrics.Metrics metrics;

    public DagSp(Graph g, metrics.Metrics metrics) {
        this.g = g;
        this.metrics = metrics;
    }

    public Map<String, Double> shortestFrom(String source, List<String> topoOrder) {
        metrics.reset();
        long start = System.nanoTime();

        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        for (String v : g.nodes()) dist.put(v, Double.POSITIVE_INFINITY);
        dist.put(source, 0.0);

        boolean started = false;
        for (String u : topoOrder) {
            if (dist.get(u).isInfinite()) continue;
            for (String v : g.neighbors(u)) {
                double alt = dist.get(u) + g.weight(u, v);
                metrics.incRelaxations();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                }
            }
        }
        long end = System.nanoTime();
        metrics.setTimeNano(end - start);
        return dist;
    }

    public Map<String, Double> longestFrom(String source, List<String> topoOrder) {
        metrics.reset();
        long start = System.nanoTime();

        Map<String, Double> dist = new HashMap<>();
        for (String v : g.nodes()) dist.put(v, Double.NEGATIVE_INFINITY);
        dist.put(source, 0.0);

        for (String u : topoOrder) {
            if (dist.get(u).equals(Double.NEGATIVE_INFINITY)) continue;
            for (String v : g.neighbors(u)) {
                double alt = dist.get(u) + g.weight(u, v);
                metrics.incRelaxations();
                if (alt > dist.get(v)) {
                    dist.put(v, alt);
                }
            }
        }
        long end = System.nanoTime();
        metrics.setTimeNano(end - start);
        return dist;
    }

    public static List<String> reconstructPath(Map<String, String> parent, String target) {
        LinkedList<String> path = new LinkedList<>();
        String cur = target;
        while (cur != null) {
            path.addFirst(cur);
            cur = parent.get(cur);
        }
        return path;
    }
}
