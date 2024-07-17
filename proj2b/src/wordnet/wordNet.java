package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;

public class wordNet {
    Map<Integer, List<String>> id2words;
    Map<String, List<Integer>> word2ids;
    int size = 0;
    Graph graph;

    public wordNet(String hyponymsFileName, String synsetsFilename) {
        id2words = new HashMap<>();
        word2ids = new HashMap<>();
        In in = new In(synsetsFilename);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            String[] subSplitLine = splitLine[1].split(" ");
            int id = Integer.parseInt(splitLine[0]);
            size = Math.max(size, id);
            for (String s : subSplitLine) {
                if (!id2words.containsKey(id)) {
                    id2words.put(id, new ArrayList<>());
                }
                if (!word2ids.containsKey(s)) {
                    word2ids.put(s, new ArrayList<>());
                }
                id2words.get(id).add(s);
                word2ids.get(s).add(id);
            }
        }
        graph = new Graph(hyponymsFileName, size + 1);
    }

    public Set<String> findNext(String word) {
        Set<String> result = new TreeSet<>();
        for (int id : word2ids.get(word)) {
            result.addAll(id2words.get(id));
            for (int subid : graph.findNext(id)) {
                result.addAll(id2words.get(subid));
            }
        }
        return result;
    }

    public Set<String> findNext(List<String> words) {
        Set<String> result = findNext(words.get(0));
        for (String word : words) {
            result.retainAll(findNext(word));
        }
        return result;
    }
}
