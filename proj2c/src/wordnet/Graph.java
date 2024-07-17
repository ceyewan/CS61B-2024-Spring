package wordnet;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

public class Graph {
    private final List<Integer>[] adj;
    private final List<Integer>[] prevAdj;

    Graph(String hyponymsFileName, int V) {
        adj = (List<Integer>[]) new List[V];
        prevAdj = (List<Integer>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
            prevAdj[v] = new ArrayList<>();
        }
        In in = new In(hyponymsFileName);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int v = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i++) {
                addEdge(v, Integer.parseInt(splitLine[i]));
            }
        }
    }

    private void addEdge(int v, int w) {
        adj[v].add(w);
        prevAdj[w].add(v);
    }

    private Iterable<Integer> adj(int v) {
        return adj[v];
    }

    private Iterable<Integer> prevAdj(int v) {
        return prevAdj[v];
    }

    public List<Integer> getSuccessors(int v) {
        List<Integer> result = new ArrayList<>();
        result.add(v);
        for (int w : adj(v)) {
            result.addAll(getSuccessors(w));
        }
        return result;
    }

    public List<Integer> getPredecessors(int v) {
        List<Integer> result = new ArrayList<>();
        result.add(v);
        for (int w : prevAdj(v)) {
            result.addAll(getPredecessors(w));
        }
        return result;
    }
}
