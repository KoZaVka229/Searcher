package koz.searcher;

import java.io.IOException;

public interface ISearcher {
    IResultOfSearch[] search(String question) throws IOException;
    String text(IResultOfSearch selected) throws IOException;
}
