package koz.searcher.wiki;

import koz.searcher.IResultOfSearch;
import koz.searcher.ISearcher;
import koz.wikisr.ResultOfSearch;
import koz.wikisr.SectionBuilder;
import koz.wikisr.Wiki;
import koz.wikisr.WikiPage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class WikiAdapter implements ISearcher {
    private final Wiki wiki = new Wiki();

    @Override
    public IResultOfSearch[] search(String question) throws IOException {
        ResultOfSearch[] results = wiki.search(question);

        ResultOfSearchAdapter[] ret = new ResultOfSearchAdapter[results.length];
        for (int i = 0; i < results.length; i++) {
            ret[i] = new ResultOfSearchAdapter(results[i]);
        }

        return ret;
    }

    @Override
    public String text(IResultOfSearch selected) throws IOException {
        ResultOfSearchAdapter result = (ResultOfSearchAdapter) selected;

        ResultOfSearch sr = result.getResult();

        try {
            sr.init();
        } catch (SectionBuilder.NameNotFoundException | WikiPage.LoadPageException | ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        try {
            return sr.getPage().getSummary();
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
