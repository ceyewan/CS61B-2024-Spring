package wordnet;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

public class Graph {
    private final int V;
    private int E;
    private final List<Integer>[] adj;
    private final List<Integer>[] reverseadj;

    Graph(String hyponymsFileName, int V) {
        this.V = V;
        this.E = 0;
        adj = (List<Integer>[]) new List[V];
        reverseadj = (List<Integer>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
            reverseadj[v] = new ArrayList<>();
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

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void addEdge(int v, int w) {
        adj[v].add(w);
        reverseadj[w].add(v);
        E++;
    }

    private Iterable<Integer> adj(int v) {
        return adj[v];
    }

    private Iterable<Integer> reverseAdj(int v) {
        return reverseadj[v];
    }

    public List<Integer> findNext(int v) {
        List<Integer> result = new ArrayList<>();
        result.add(v);
        for (int w : adj(v)) {
            result.addAll(findNext(w));
        }
        return result;
    }

    public List<Integer> findPrev(int v) {
        List<Integer> result = new ArrayList<>(v);
        for (int w : reverseAdj(v)) {
            result.addAll(findNext(w));
        }
        return result;
    }
}
