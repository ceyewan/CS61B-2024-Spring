package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap ngm;

    public HistoryHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (String word : words) {
            labels.add(word);
            lts.add(ngm.weightHistory(word, startYear, endYear));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        return Plotter.encodeChartAsString(chart);
    }
}
