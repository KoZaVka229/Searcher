package koz.googlesr;

import koz.Methods;
import koz.TableParser;
import koz.googlesr.fa.FactAnswerNotFoundException;
import koz.googlesr.fa.FactAnswerNotSupportedException;
import koz.googlesr.fa.FactAnswerUnknownException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Google {
    private static final String URL = "https://www.google.com/search?q=";

    public ResultOfSearch[] search(String q) throws IOException {
        Document doc = Methods.get(gen(q));

        List<String> hrefs = new ArrayList<>();
        List<String> subheaders = new ArrayList<>();
        List<String> heads = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        Elements tags = doc.select("a.cz3goc");
        for (Element tag : tags) {                      // Ссылки
            hrefs.add(tag.attr("href"));
        }

        tags = doc.select("span.yKd8Hd");       // Подразделы
        for (Element tag : tags) {
            subheaders.add(tag.text());
        }

        tags = doc.select("div.q8U8x");         // Заголовки
        for (Element tag : tags) {
            heads.add(tag.text());
        }

        tags = doc.select("div.VwiC3b");        // Описания содержимых
        for (Element tag : tags) {
            descriptions.add(tag.text());
        }

        int len = Math.min(hrefs.size(),
                Math.min(subheaders.size(),
                        Math.min(heads.size(), descriptions.size())));

        ResultOfSearch[] results = new ResultOfSearch[len];

        for (int i = 0; i < len; i++) {
            String link = hrefs.get(i);
            String cite = subheaders.get(i);
            String head = heads.get(i);
            String description = descriptions.get(i);

            results[i] = new ResultOfSearch(link, cite, head, description);
        }

        return results;
    }
    public String text(ResultOfSearch selected) throws IOException {
        Document doc = Methods.get(selected.getLink());
        return Methods.text(doc.clone());
    }
    public TableParser[] tables(ResultOfSearch selected) throws IOException {
        Document doc = Methods.get(selected.getLink());
        return Methods.tables(doc);
    }
    public IFastAnswer factAnswer(String q) throws IOException, FactAnswerUnknownException, FactAnswerNotFoundException, FactAnswerNotSupportedException {
        Document doc = Methods.get(gen(q));

        FactAnswerFactory f = new FactAnswerFactory();

        return f.newFactAnswer(doc);
    }

    private String gen(String s) {
        return URL + Methods.quote(s);
    }
}
