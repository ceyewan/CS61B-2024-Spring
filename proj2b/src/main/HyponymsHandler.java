package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.wordNet;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    wordNet wg;

    HyponymsHandler(wordNet wg) {
        this.wg = wg;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        return wg.findNext(words).toString();
    }
}
