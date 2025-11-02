package util;

import java.util.*;

public class Graph {
    private final Map<String, List<String>> adj = new HashMap<>();
    private final Map<EdgeKey, Double> weights = new HashMap<>();

    public void addNode(String v) {
        adj.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(String from, String to, double weight) {
        addNode(from);
        addNode(to);
        adj.get(from).add(to);
        weights.put(new EdgeKey(from, to), weight);
    }

    public Set<String> nodes() {
        return adj.keySet();
    }

    public List<String> neighbors(String v) {
        return adj.getOrDefault(v, Collections.emptyList());
    }

    public double weight(String from, String to) {
        return weights.getOrDefault(new EdgeKey(from, to), Double.POSITIVE_INFINITY);
    }

    public Map<String, List<String>> adjacency() {
        return adj;
    }

    private static final class EdgeKey {
        final String u, v;
        EdgeKey(String u, String v){this.u=u; this.v=v;}
        public boolean equals(Object o){
            if(!(o instanceof EdgeKey)) return false;
            EdgeKey e=(EdgeKey)o; return u.equals(e.u) && v.equals(e.v);
        }
        public int hashCode(){return Objects.hash(u,v);}
    }
}
