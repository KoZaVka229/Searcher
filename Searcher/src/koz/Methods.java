package koz;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Methods {
    public static final String MobileUserAgent = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

    public static Document get(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url)
                .header("User-Agent", MobileUserAgent)
                .followRedirects(true)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .timeout(0)
                .execute();

        return response.parse();
    }
    public static String quote(String str) {
        str =   str
                .replaceAll("%", "%25")   // Процент
                .replaceAll(" ", "%20")   // Пробел
                .replaceAll("\t", "%20")  // Табуляция (заменяем на пробел)
                .replaceAll("\n", "%20")  // Переход строки (заменяем на пробел)
                .replaceAll("\r", "%20")  // Возврат каретки (заменяем на пробел)
                .replaceAll("!", "%21")   // Восклицательный знак
                .replaceAll("\"", "%22")  // Двойная кавычка
                .replaceAll("#", "%23")   // Октоторп, решетка
                .replaceAll("\\$", "%24") // Знак доллара
                .replaceAll("&", "%26")   // Амперсанд
                .replaceAll("'", "%27")   // Одиночная кавычка
                .replaceAll("\\(", "%28") // Открывающаяся скобка
                .replaceAll("\\)", "%29") // Закрывающаяся скобка
                .replaceAll("\\*", "%2a") // Звездочка
                .replaceAll("\\+", "%2b") // Знак плюс
                .replaceAll(",", "%2c")   // Запятая
                .replaceAll("-", "%2d")   // Дефис
                //.replaceAll("\\.", "%2e") // Точка
                //.replaceAll("/", "%2f")   // Слеш, косая черта
                //.replaceAll(":", "%3a")   // Двоеточие
                .replaceAll(";", "%3b")   // Точка с запятой
                .replaceAll("<", "%3c")   // Открывающаяся угловая скобка
                .replaceAll("=", "%3d")   // Знак равно
                .replaceAll(">", "%3e")   // Закрывающаяся угловая скобка
                .replaceAll("\\?", "%3f") // Вопросительный знак
                .replaceAll("@", "%40")   // At sign, по цене, собачка
                .replaceAll("\\[", "%5b") // Открывающаяся квадратная скобка
                .replaceAll("\\\\", "%5c") // Одиночный обратный слеш '\'
                .replaceAll("\\]", "%5d") // Закрывающаяся квадратная скобка
                .replaceAll("\\^", "%5e") // Циркумфлекс
                .replaceAll("_", "%5f")   // Нижнее подчеркивание
                .replaceAll("`", "%60")   // Гравис
                .replaceAll("\\{", "%7b") // Открывающаяся фигурная скобка
                .replaceAll("\\|", "%7c") // Вертикальная черта
                .replaceAll("\\}", "%7d") // Закрывающаяся фигурная скобка
                .replaceAll("~", "%7e");  // Тильда

        return str;
    }

    public static String text(String url) throws IOException {
        Document doc = Methods.get(url);
        return text(doc);
    }
    public static TableParser[] tables(String url) throws IOException {
        return tables(get(url));
    }
    public static TableParser[] tables(String url, String selector) throws IOException {
        return tables(get(url), selector);
    }

    public static String text(Element element) {
        cloneAltAttrToText(element);

        Element content = element.selectFirst("div.content");

        String text;
        if (content != null) {
            text = content.text();
        }
        else {
            String[] target_tags = {
                    "header", "nav", "footer", "form", "frame", "iframe",
                    ".popUpWindow", ".breadcrumb", ".attention-slider", ".footer", ".payment", ".sidebar", ".form",
                    ".banner", ".left-banners", ".right-banners", ".social", ".card-header"
            };

            element.select( String.join(",", target_tags) ).remove();   //Очищаем

            text = element.text();

            if (text.contains("Performance & security by Cloudflare")) {
                return "Performance & security by Cloudflare";
            }

            int i = text.indexOf("Содержание");
            if (i != -1) {
                text = text.substring(i);
            }
        }
        text = text.replaceAll("\n{3,}", "\n");
        text = text.replaceAll("\t{3,}", "  ");
        text = text.replaceAll(" {3,}", "  ");

        return text;
    }
    public static TableParser[] tables(Element element) {
        return tables(element, "");
    }
    public static TableParser[] tables(Element element, String selector) {
        cloneAltAttrToText(element);

        Elements tables = element.select("table");
        return TableParser.from(tables.select(selector));
    }

    /** Берёт атрибут alt у элемента и делает его текстом элемента **/
    public static void cloneAltAttrToText(Element element) {
        element.text(String.format(" %s{ %s } ", element.tagName(), element.attr("alt")));
        element.tagName("span");
    }
    public static void cloneAltAttrToText(Element element, String selector) {
        cloneAltAttrToText(element.select(selector));
    }
    public static void cloneAltAttrToText(Elements elements) {
        for (Element element : elements)
            cloneAltAttrToText(element);
    }
}
