package koz.searcher.google;

import koz.googlesr.ResultOfSearch;
import koz.searcher.IResultOfSearch;

public class ResultOfSearchAdapter implements IResultOfSearch {
    private final ResultOfSearch result;
    public ResultOfSearchAdapter(ResultOfSearch r) {
        result = r;
    }

    public ResultOfSearch getResult() {
        return result;
    }

    @Override
    public String getDescription() {
        return result.getHead();
    }
}
