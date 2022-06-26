package koz.wikisr;

import koz.Methods;
import koz.TableParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import koz.wikisr.SectionBuilder.*;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.*;

/** Класс содержит информацию о страницы wikipedia **/
public class WikiPage {
    /** Ошибка при загрузке страницы **/
    public static class LoadPageException extends Exception {
        public LoadPageException(){
            super("Ошибка при загрузке страницы");
        }
    }

    /** Список игнорируемых имён секций **/
    public static final String[] ignore_list = {"См. также", "Примечания", "Литература", "Ссылки"};

    private String title;
    public String getTitle() {return title;}

    private int page_id;
    public int getPageId() {return page_id;}

    private String url = "";
    public String getUrl() {return url;}

    private String summary = "";
    private String content = "";
    private List<Section> sections = null;

    /**
     * @param title Имя страницы
     * @throws LoadPageException Ошибка при загрузке страницы
     */
    public WikiPage(String title) throws IOException, ParseException, LoadPageException {
        this.title = title;
        page_id = -1;
        load();
    }

    /**
     * Инициализация страницы
     *
     * @throws LoadPageException Ошибка при загрузке страницы
     */
    private void load() throws IOException, ParseException, LoadPageException {
        Map<String, String> params = new HashMap<>();
        params.put("prop", "info|pageprops");
        params.put("inprop", "url");
        params.put("ppprop", "disambiguation");
        params.put("redirects", "");
        params.put("titles", title);

        JSONObject json = Wiki.wiki_get(params);
        JSONObject query = getJson(json,"query");
        Object[] keys = getJson(query, "pages").keySet().toArray();

        int _page_id = Integer.parseInt((String)keys[0]);
        JSONObject page = getJson(getJson(query,"pages"), String.valueOf(_page_id));
        String page_str = page.toString();
        if (page_str.contains("missing") || page_str.contains("redirects")) {
            throw new LoadPageException();
        }
        else {
            page_id = _page_id;
            title = (String) page.get("title");
            url = (String) page.get("fullurl");
        }
    }

    /**
     * Получает основной текст из статьи.
     */
    public String getSummary() throws IOException, ParseException {
        if (summary.equals("")) {
            Map<String, String> params = new HashMap<>();
            params.put("prop", "extracts");
            params.put("explaintext", "");
            params.put("exintro", "");
            params.put("pageids", String.valueOf(page_id));

            summary = (String) getJson(getJson(getJson(Wiki.wiki_get(params),
                    "query"), "pages"), String.valueOf(page_id))
                    .get("extract");
            summary = no_nbsp(summary);
        }
        return summary;
    }

    /**
     * Получает всё содержимое статьи.
     */
    public String getContent() throws IOException, ParseException {
        if (content.equals("")) {
            Map<String, String> params = new HashMap<>();
            params.put("prop", "extracts|revisions");
            params.put("explaintext", "");
            params.put("rvprop", "ids");
            params.put("pageids", String.valueOf(page_id));

            content = (String) getJson(getJson(getJson(Wiki.wiki_get(params),
                    "query"), "pages"), String.valueOf(page_id))
                    .get("extract");
            content = no_nbsp(content);
        }
        return content;
    }

    /**
     * @throws NameNotFoundException Текст всей страницы, который выдала wikipedia,
     * не содержит секции, которую удалось получить от wikipedia api (чисто техническая ошибка)
     */
    public Section[] getSections() throws IOException, ParseException, NameNotFoundException {
        if (sections == null) {
            Map<String, String> params = new HashMap<>();
            params.put("action", "parse");
            params.put("prop", "sections");
            params.put("page", title);

            JSONArray sections_objects = getJsonArray(getJson(Wiki.wiki_get(params),
                    "parse"), "sections");

            sections = new ArrayList<>();
            if (content.equals("")) getContent();

            SectionBuilder sectionBuilder = new SectionBuilder(content);

            for (Object obj : sections_objects) {
                JSONObject section_json = (JSONObject) obj;

                String name = (String) section_json.get("line");
                name = formatSectionName(name);

                String number = (String) section_json.get("number");

                List<String> numbers = new ArrayList<>(Arrays.asList(number.split("\\.")));

                if (ignore_contains(name, Integer.parseInt(numbers.get(0)) - 1)) continue;

                sectionBuilder.home();
                if (number.contains(".")) {
                    if (numbers.size() > 1)
                        numbers.remove(numbers.size()-1);  // типо зачем знать свой индекс в списке ?

                    for (String num : numbers) {
                        int i = Integer.parseInt(num) - 1;
                        sectionBuilder.select(i);
                    }
                }
                sectionBuilder.add(name);
            }
            sections = sectionBuilder.build();
            sections.removeIf(s -> s.getValue().equals("") && s.getSubSections().length == 0);
        }
        return sections.toArray(new Section[0]);
    }

    /**
     *  Получает таблицы из статьи
     **/
    public TableParser[] getTables() throws IOException {
        return Methods.tables(Wiki.URL+"wiki/"+title, ".infobox, .wikitable");
    }

    private static JSONObject getJson(JSONObject from, String key) {
        return (JSONObject) from.get(key);
    }
    private static JSONArray getJsonArray(JSONObject from, String key) {
        return (JSONArray) from.get(key);
    }
    private static int ignore_number = -1;
    private static boolean ignore_contains(String target, int number) {
        for (String s : ignore_list) {
            if (target.equals(s)) {
                ignore_number = number;
                return true;
            }
        }
        return number == ignore_number;
    }
    public static String no_nbsp(String s) {
        return s.replace("\u00a0"," "); // Убираем nbsp
    }
    public static String noTags(String string) {
        return Jsoup.parse(string).text();
    }
    public static String formatSectionName(String name) {
        return noTags(no_nbsp(name)).replaceAll("\\[(\\d+)\\]", "");
    }
}
