package koz.sdamgiasr;

import koz.Methods;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

// впр за 8 класс
public class SDAMGIA {
    public enum CiteType { RUS, MATH, PHYS, BIO, GEO, HIST, SOC, CHEM }

    private final String URL;

    public SDAMGIA(ISubject subject) {
        URL = subject.getUrl();
    }

    public ResultOfSearch[] search(String q) throws IOException {
        Document doc = Methods.get(URL + Methods.quote(q));

        Elements questions = doc.select("div.nobreak");
        Elements prob_nums = questions.select("span.prob_nums");
        Elements pbody = questions.select("div.pbody");

        Elements nums = prob_nums.select("a");

        ResultOfSearch[] results = new ResultOfSearch[prob_nums.size()];

        for (int i = 0; i < prob_nums.size(); i++) {
            String text = pbody.get(i).text();
            int id = Integer.parseInt(nums.get(i).text());

            results[i++] = new ResultOfSearch(text, id);
        }

        return results;
    }

    public String text(ResultOfSearch selected) throws IOException{
        Document doc = Methods.get(URL + selected.getTaskId());

        Element solution = doc.selectFirst("div.solution");
        if (solution == null) return null;

        Methods.cloneAltAttrToText(solution.select("img.tex"));

        return solution.text();
    }
}
