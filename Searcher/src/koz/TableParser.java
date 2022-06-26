package koz;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableParser {
    private final List<String> cols_list = new ArrayList<>();
    private final List<String> rows_list = new ArrayList<>();
    private final Element element;

    public TableParser(Element element) {
        this.element = element;

        Elements trs = element.select("tr");

        for (int i = 0; i < trs.size(); i++) {
            Element tr = trs.get(i);
            Elements tds = tr.select("th, td");
            for (int j = 0; j < tds.size(); j++) {
                Element td = tds.get(j);

                String colspan = td.attr("colspan");
                String rowspan = td.attr("rowspan");
                td.removeAttr("colspan");
                td.removeAttr("rowspan");
                if (!colspan.equals("")) for (int k = 0; k < Integer.parseInt(colspan)-1; k++) tr.child(j).after(td.clone());
                if (!rowspan.equals("")) for (int k = 0; k < Integer.parseInt(rowspan)-1; k++) trs.get(i+1).child(j).before(td.clone());
            }
        }

        StringBuilder builder = new StringBuilder();

        // Cols
        for (Element tr : trs) {
            Elements tds = tr.select("th, td");
            for (Element td : tds) {
                String text = td.text();
                if (text.equals(" ")) break;

                builder.append(text);
                builder.append("\n");
            }
            if (builder.length() > 0) builder.deleteCharAt(builder.length()-1);

            cols_list.add(builder.toString());
            builder.delete(0, builder.length());
        }

        // Rows
        Elements tds = trs.select("th, td");
        int tds_count = tds.size() / trs.size();

        for (int i = 0; i < tds_count; i++) {
            for (Element tr : trs) {
                Element td = tr.child(i);
                builder.append(td.text());
                builder.append("\n");
            }
            rows_list.add(builder.toString());
            builder.delete(0, builder.length());
        }
    }

    public String[] getCols() {
        return cols_list.toArray(new String[0]);
    }
    public String[] getRows() {
        return rows_list.toArray(new String[0]);
    }
    public Element getElement() {
        return element;
    }

    public static TableParser[] from(String url) throws IOException {
        return from(Methods.get(url));
    }
    public static TableParser[] from(Element element) {
        return from(element.select("table"));
    }
    public static TableParser[] from(Elements tables) {
        List<TableParser> tableParsers = new ArrayList<>();
        for (Element table : tables) {
            tableParsers.add(new TableParser(table));
        }

        return tableParsers.toArray(new TableParser[0]);
    }
}
