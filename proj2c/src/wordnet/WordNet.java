package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    Map<Integer, List<String>> id2words;
    Map<String, List<Integer>> word2ids;
    int size = 0;
    Graph graph;

    public WordNet(String hyponymsFileName, String synsetsFilename) {
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

    private Set<String> getHyponym(String word) {
        Set<String> result = new TreeSet<>();
        for (int id : word2ids.get(word)) {
            result.addAll(id2words.get(id));
            for (int successorId : graph.getSuccessors(id)) {
                result.addAll(id2words.get(successorId));
            }
        }
        return result;
    }

    public Set<String> getHyponym(List<String> words) {
        Set<String> result = getHyponym(words.get(0));
        for (String word : words) {
            result.retainAll(getHyponym(word));
        }
        return result;
    }

    private Set<String> getHypernym(String word) {
        Set<String> result = new TreeSet<>();
        for (int id : word2ids.get(word)) {
            result.addAll(id2words.get(id));
            for (int predecessorId : graph.getPredecessors(id)) {
                result.addAll(id2words.get(predecessorId));
            }
        }
        return result;
    }

    public Set<String> getHypernym(List<String> words) {
        Set<String> result = getHypernym(words.get(0));
        for (String word : words) {
            result.retainAll(getHypernym(word));
        }
        return result;
    }
}
