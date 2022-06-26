package koz.searcher.google;

import koz.googlesr.Google;
import koz.googlesr.ResultOfSearch;
import koz.googlesr.fa.FactAnswerNotFoundException;
import koz.googlesr.fa.FactAnswerNotSupportedException;
import koz.googlesr.fa.FactAnswerUnknownException;
import koz.searcher.IFastAnswerer;
import koz.searcher.IResultOfSearch;
import koz.searcher.ISearcher;

import java.io.IOException;

public class GoogleAdapter implements ISearcher, IFastAnswerer {
    private final Google google = new Google();

    @Override
    public IResultOfSearch[] search(String question) throws IOException {
        ResultOfSearch[] results = google.search(question);

        ResultOfSearchAdapter[] ret = new ResultOfSearchAdapter[results.length];
        for (int i = 0; i < results.length; i++) {
            ret[i] = new ResultOfSearchAdapter(results[i]);
        }

        return ret;
    }

    @Override
    public String text(IResultOfSearch selected) throws IOException {
        ResultOfSearchAdapter result = (ResultOfSearchAdapter) selected;

        return google.text(result.getResult());
    }

    @Override
    public String fastAnswer(String question) throws IOException {
        try {
            return google.factAnswer(question).getMessage();
        } catch (FactAnswerNotFoundException | FactAnswerUnknownException | FactAnswerNotSupportedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
