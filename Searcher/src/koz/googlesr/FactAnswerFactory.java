package koz.googlesr;

import koz.googlesr.fa.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FactAnswerFactory {

    public IFastAnswer newFactAnswer(Document doc) throws FactAnswerNotFoundException, FactAnswerUnknownException, FactAnswerNotSupportedException {
        Element rso_tag = doc.selectFirst("div#rso");
        if (rso_tag == null) {
            throw new FactAnswerNotFoundException();
        }

        Element fact_answer_tag = rso_tag.selectFirst(":first-child");
        if (fact_answer_tag == null) {
            throw new FactAnswerNotFoundException();
        }

        if (fact_answer_tag.attr("data-md").equals("") && !fact_answer_tag.nodeName().equals("block-component")) {
            Element text_tag = doc.selectFirst("div.kno-rdesc > span");
            if (text_tag == null) throw new FactAnswerNotFoundException();
            else return new FastAnswerWikiText(text_tag.text());
        }

        // Главный тег div, в котором есть вся информация от переводчика
        Element tag = doc.selectFirst("div#tw-main");
        if (tag != null) {
            String source = requireNonNull(doc.selectFirst("#tw-source-text-ta")).text();
            String translation = requireNonNull(doc.selectFirst("#tw-target-text")).text();
            List<String[]> other_translations = new ArrayList<>();

            Elements translations_tags = doc.select("span.hrcAhc");
            Elements values_tags = doc.select("div.MaH2Hf");
            int len = Math.min(translations_tags.size(), values_tags.size());
            checkUnknown(len == 0);
            for (int i = 0; i < len; i++) {
                List<String> obj = new ArrayList<>();
                obj.add(translations_tags.get(i).text());
                obj.add(values_tags.get(i).text());
                other_translations.add(obj.toArray(new String[0]));
            }

            return new FastAnswerTranslator(source, translation, other_translations.toArray(new String[0][0]));
        }

        // Тег результата от калькулятора
        tag = doc.selectFirst("span.qv3Wpe");
        if (tag != null) {
            String result = tag.text().strip();

            return new FastAnswerCalculator(result);
        }

        // Тег div-а, в котором есть таблица
        tag = doc.selectFirst("div.webanswers-webanswers_table__webanswers-table");
        if (tag != null) {
            Element head_tag = doc.selectFirst("div.iKJnec");
            String head = "NoName";
            if (head_tag != null) head = head_tag.text();

            Element table = tag.selectFirst("table");
            List<String[]> table_rows = new ArrayList<>();
            checkUnknown(table == null);
            Elements tr_tags = table.select("tr");
            checkUnknown(tr_tags.size() == 0);
            for (Element tr_tag : tr_tags) {
                Elements td_tags = tr_tag.select("td, th");
                List<String> row = new ArrayList<>();
                for (Element td_tag : td_tags) {
                    row.add(td_tag.text());
                }
                table_rows.add(row.toArray(new String[0]));
            }

            return new FastAnswerTable(head, table_rows.toArray(new String[0][0]));
        }

        // Тег span-а, в котором есть слово от словаря
        tag = doc.selectFirst("span.LWPArc");
        if (tag != null) {
            String target = tag.text();
            Elements text_divs = doc.select("ol.eQJLDd > li > div.vmod > div.thODed > div.sY7ric");

            checkUnknown(text_divs.size() == 0);

            List<String> texts = new ArrayList<>();
            for (Element text_div : text_divs) {
                String text = text_div.text();
                if (!text.equals("")) texts.add(text);
            }

            return new FastAnswerDictionary(target, texts.toArray(new String[0]));
        }

        // Тег div-а, в котором есть информация о конвертере
        tag = doc.selectFirst("div.HOslld");
        if (tag != null) {
            Element fact_answer_div = tag.selectFirst("div:first-child");
            Element converter_type_option = tag.selectFirst("select.rYVYn > option[selected=\"1\"]");
            Element exact_result_input = tag.selectFirst("input.vXQmIe");
            Element formula_div = tag.selectFirst("div.bjhkR");

            checkUnknown((fact_answer_div == null) || (converter_type_option == null) || (exact_result_input == null) || (formula_div == null));

            // Быстрый ответ
            String fact_answer = fact_answer_div.text().strip();
            fact_answer = fact_answer.replaceAll(" {2,}", " ");

            // Тип конвертера
            String converter_type = converter_type_option.text();

            // Точный ответ
            String exact_result = exact_result_input.attr("value");

            // Формула
            String formula = formula_div.text();

            return new FastAnswerConverter(fact_answer, converter_type, exact_result, formula);
        }

        // Тег div-а, в котором есть имя персоны
        tag = doc.selectFirst("a.FLP8od");
        if (tag != null) {
            String name = tag.text();

            return new FastAnswerPerson(name);
        }

        // Тег заголовка страницы, где взят ответ
        tag = doc.selectFirst("a.sXtWJb");
        if (tag != null) {
            String link = tag.attr("href");
            checkUnknown(link.equals(""));
            String head = tag.text();

            Element cite_tag = doc.selectFirst("cite.iUh30");
            checkUnknown(cite_tag == null);
            String cite = cite_tag.text();
            int i = cite.indexOf("//");
            cite = cite.substring(i + 2);

            Element answer_tag = doc.selectFirst("span.hgKElc");
            checkUnknown(answer_tag == null);
            String answer = answer_tag.text();

            return new FastAnswerCiteText(answer, head, cite, link);
        }

        throw new FactAnswerNotSupportedException();
    }

    private static <T> T requireNonNull(T obj) throws FactAnswerUnknownException {
        if (obj == null) throw new FactAnswerUnknownException();
        else return obj;
    }
    private static void checkUnknown(boolean v) throws FactAnswerUnknownException {
        if (v) throw new FactAnswerUnknownException();
    }
}
