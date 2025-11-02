package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphLoader {

    public static List<GraphTask> loadAllFromJson(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(file);
        List<GraphTask> tasks = new ArrayList<>();

        for (JsonNode graphNode : root) {
            Graph g = new Graph();
            int n = graphNode.get("n").asInt();
            JsonNode edges = graphNode.get("edges");
            for (JsonNode e : edges) {
                int u = e.get("u").asInt();
                int v = e.get("v").asInt();
                double w = e.get("w").asDouble();
                g.addEdge(String.valueOf(u), String.valueOf(v), w);
            }
            int source = graphNode.get("source").asInt();
            String weightModel = graphNode.get("weight_model").asText();
            tasks.add(new GraphTask(g, String.valueOf(source), weightModel));
        }

        return tasks;
    }

    public static class GraphTask {
        public Graph graph;
        public String source;
        public String weightModel;

        public GraphTask(Graph graph, String source, String weightModel) {
            this.graph = graph;
            this.source = source;
            this.weightModel = weightModel;
        }
    }
}
