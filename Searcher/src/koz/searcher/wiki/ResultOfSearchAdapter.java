package koz.searcher.wiki;

import koz.searcher.IResultOfSearch;
import koz.wikisr.ResultOfSearch;

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
        return result.getTitle();
    }
}
