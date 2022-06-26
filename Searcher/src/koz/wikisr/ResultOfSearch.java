package koz.wikisr;

import koz.TableParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/** Замечание: чтобы испльзовать методы getPage и getSections, нужно вызвать метод init.
 * Он проинициализирует страницу и её секции. Если этого не сделать, вышеперечисленные методы вернут null
 **/
public class ResultOfSearch {
    private final String title;
    private WikiPage page;
    private SectionBuilder.Section[] sections;

    public ResultOfSearch(String title) {
        this.title = title;
    }

    public void init() throws ParseException, IOException, WikiPage.LoadPageException, SectionBuilder.NameNotFoundException {
        page = new WikiPage(title);
        sections = page.getSections();
    }

    public boolean isInit() {
        return page != null;
    }

    public String getTitle() {
        return title;
    }
    public WikiPage getPage() {
        return page;
    }
    public SectionBuilder.Section[] getSections() {
        return sections;
    }
}
