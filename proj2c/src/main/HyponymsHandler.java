package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import wordnet.WordNet;

import java.util.*;

import static browser.NgordnetQueryType.HYPONYMS;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet wnt;
    NGramMap ngm;
    int startYear;
    int endYear;
    int k;

    HyponymsHandler(WordNet wnt, NGramMap ngm) {
        this.wnt = wnt;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        this.startYear = q.startYear();
        this.endYear = q.endYear();
        this.k = q.k();
        if (q.ngordnetQueryType() == HYPONYMS) {
            return helper(wnt.getHyponym(words)).toString();
        } else {
            return helper(wnt.getHypernym(words)).toString();
        }
    }

    private Set<String> helper(Set<String> words) {
        if (k == 0) {
            return words;
        }
        Map<Double, String> count = new TreeMap<>();
        Set<String> result = new TreeSet<>();
        for (String word : words) {
            List<Double> value = ngm.countHistory(word, startYear, endYear).data();
            if (!value.isEmpty()) {
                count.put(-value.stream().reduce(0.0, Double::sum), word);
            }
        }
        for (String word : count.values()) {
            if (k > 0) {
                result.add(word);
            }
            k--;
        }
        return result;
    }
}
