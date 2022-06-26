package koz.wikisr;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Wiki {
    public static final String URL = "https://ru.wikipedia.org/";
    public static final String API_URL = URL + "w/api.php";
    public static final int MAX_SEARCH_RESULTS = 7;

    /** Ищет статьи в wikipedia по имени **/
    public ResultOfSearch[] search(String q) throws IOException {
        ResultOfSearch[] empty_array = new ResultOfSearch[0];

        Map<String, String> params = new HashMap<>();
        params.put("list", "search");
        params.put("srprop", "");
        params.put("srlimit", String.valueOf(MAX_SEARCH_RESULTS));
        params.put("limit", String.valueOf(MAX_SEARCH_RESULTS));
        params.put("srsearch", q);

        JSONObject json;
        try {
            json = wiki_get(params);
        } catch (ParseException e) {
            e.printStackTrace();
            return empty_array;
        }

        if (json.toString().contains("error")) return empty_array;

        JSONArray raw_results = getJsonArray(getJson(json, "query"), "search");
        ResultOfSearch[] results = new ResultOfSearch[raw_results.size()];
        int i = 0;
        for (Object obj : raw_results) {
            JSONObject title_json = (JSONObject) obj;
            String title = (String) title_json.get("title");
            results[i++] = new ResultOfSearch(title);
        }
        return results;
    }

    /** Сделать запрос к wikipedia api **/
    public static JSONObject wiki_get(Map<String, String> params) throws IOException, ParseException {
        if (!params.containsKey("action"))
            params.put("action", "query");
        params.put("format", "json");

        Connection.Response execute = Jsoup.connect(API_URL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .followRedirects(true)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .data(params)
                .method(Connection.Method.GET)
                .timeout(0)
                .execute();

        JSONParser parser = new JSONParser();

        return (JSONObject) parser.parse(execute.body());
    }
    private static JSONObject getJson(JSONObject from, String key) {
        return (JSONObject) from.get(key);
    }
    private static JSONArray getJsonArray(JSONObject from, String key) {
        return (JSONArray) from.get(key);
    }
}
