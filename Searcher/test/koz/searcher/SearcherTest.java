package koz.searcher;

import koz.sdamgiasr.subject.MathSubject;
import koz.searcher.google.GoogleAdapter;
import koz.searcher.sdamgia.SDAMGIAAdapter;
import koz.searcher.wiki.WikiAdapter;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class SearcherTest {

    @Test
    public void googleTest() throws IOException {
        search(new GoogleAdapter(), "Ток");
    }

    @Test
    public void wikiTest() throws IOException {
        search(new WikiAdapter(), "Ток");
    }

    @Test
    public void sdamgiaTest() throws IOException {
        String s = "В школе открыты две спортивные секции: по футболу и по лёгкой атлетике. " +
                "Заниматься можно только в одной из них. Число школьников, занимающихся в секции" +
                " по футболу, относится к числу школьников, занимающихся в секции по лёгкой атлетике," +
                " как 11:8. Сколько школьников занимаются в секции по футболу, если всего в двух секциях " +
                "занимаются 57 школьников?";

        search(new SDAMGIAAdapter(new MathSubject()),s);
    }

    private void search(ISearcher searcher, String q) throws IOException {
        IResultOfSearch[] results = searcher.search(q);
        assertNotEquals(results.length, 0);
    }
}
