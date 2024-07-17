package main;

import browser.NgordnetQueryHandler;
import wordnet.wordGraph;


public class AutograderBuddy {
    /**
     * Returns a HyponymHandler
     */
    public static NgordnetQueryHandler getHyponymsHandler(String wordFile, String countFile, String synsetFile, String hyponymFile) {
        return new HyponymsHandler(new wordGraph(hyponymFile, synsetFile));
        // throw new RuntimeException("Please fill out AutograderBuddy.java!");
    }
}
