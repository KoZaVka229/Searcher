package koz.wikisr;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class WikiSrTest {
    private final Wiki sr = new Wiki();

    @Test
    public void searchTest() throws IOException, ParseException, SectionBuilder.NameNotFoundException, WikiPage.LoadPageException {
        ResultOfSearch[] list = sr.search("Богомол");
        assertNotEquals(list.length, 0);

        ResultOfSearch selected = list[0];
        selected.init();
    }

    @Test
    public void sectionsTest() throws ParseException, IOException, WikiPage.LoadPageException, SectionBuilder.NameNotFoundException {
        WikiPage page = new WikiPage("Математика");
        page.getSections();
    }
}
