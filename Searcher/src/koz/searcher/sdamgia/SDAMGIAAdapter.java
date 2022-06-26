package koz.searcher.sdamgia;

import koz.sdamgiasr.ISubject;
import koz.sdamgiasr.SDAMGIA;
import koz.sdamgiasr.ResultOfSearch;
import koz.searcher.IResultOfSearch;
import koz.searcher.ISearcher;

import java.io.IOException;

public class SDAMGIAAdapter implements ISearcher {
    private final SDAMGIA sdamgia;

    public SDAMGIAAdapter(ISubject subject) {
        sdamgia = new SDAMGIA(subject);
    }

    @Override
    public IResultOfSearch[] search(String question) throws IOException {
        ResultOfSearch[] results = sdamgia.search(question);

        ResultOfSearchAdapter[] ret = new ResultOfSearchAdapter[results.length];
        for (int i = 0; i < results.length; i++) {
            ret[i] = new ResultOfSearchAdapter(results[i]);
        }

        return ret;
    }

    @Override
    public String text(IResultOfSearch selected) throws IOException {
        ResultOfSearchAdapter result = (ResultOfSearchAdapter) selected;

        return result.getResult().getText();
    }
}
