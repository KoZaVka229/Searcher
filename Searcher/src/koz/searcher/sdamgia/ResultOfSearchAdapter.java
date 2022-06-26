package koz.searcher.sdamgia;

import koz.sdamgiasr.ResultOfSearch;
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
        return result.getText();
    }
}
