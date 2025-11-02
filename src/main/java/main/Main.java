package main;

import util.GraphLoader;
import util.GraphLoader.GraphTask;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import graph.dagsp.DagSp;
import metrics.Metrics;
import util.Graph;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        List<GraphTask> tasks = GraphLoader.loadAllFromJson(new File("data/tasks.json"));
        int idx = 1;

        for (GraphTask task : tasks) {
            System.out.println("\n====================");
            System.out.println("Graph #" + idx++);
            Graph g = task.graph;
            String source = task.source;

            Metrics metrics = new Metrics();

            // 1. SCC
            TarjanSCC sccRunner = new TarjanSCC(g, metrics);
            List<List<String>> sccs = sccRunner.run();
            System.out.println("SCC count: " + sccs.size());
            for (List<String> comp : sccs)
                System.out.println("  " + comp);

            // Build condensation graph
            Map<String,Integer> compOf = new HashMap<>();
            for (int i=0;i<sccs.size();i++)
                for (String v: sccs.get(i))
                    compOf.put(v,i);

            Map<String,List<String>> condAdj = new HashMap<>();
            for (int i=0;i<sccs.size();i++)
                condAdj.put(String.valueOf(i), new ArrayList<>());

            for (String u : g.nodes())
                for (String v : g.neighbors(u)) {
                    int cu = compOf.get(u), cv = compOf.get(v);
                    if (cu != cv && !condAdj.get(String.valueOf(cu)).contains(String.valueOf(cv)))
                        condAdj.get(String.valueOf(cu)).add(String.valueOf(cv));
                }

            // 2. Topological sort
            TopologicalSort topo = new TopologicalSort(metrics);
            List<String> compOrder = topo.kahn(condAdj);
            System.out.println("Topological order of SCCs: " + compOrder);

            // Derived order of tasks
            List<String> taskOrder = new ArrayList<>();
            for (String compId : compOrder) {
                int id = Integer.parseInt(compId);
                taskOrder.addAll(sccs.get(id));
            }

            // 3. DAG shortest and longest paths
            DagSp dagsp = new DagSp(g, metrics);
            Map<String, Double> shortest = dagsp.shortestFrom(source, taskOrder);
            Map<String, Double> longest = dagsp.longestFrom(source, taskOrder);

            System.out.println("Shortest distances from " + source + ": " + shortest);
            System.out.println("Longest distances from " + source + ": " + longest);
        }
    }
}
