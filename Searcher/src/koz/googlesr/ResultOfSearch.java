package koz.googlesr;

public class ResultOfSearch {
    private final String link;
    private final String cite;
    private final String head;
    private final String description;

    /**
     * @param link Ссылка на страницу
     * @param cite Подраздел сайта, в котором нашёлся ответа
     * @param head Заголовок сайта
     * @param description Описание содержания
     */
    public ResultOfSearch(String link, String cite, String head, String description) {
        this.link = link;
        this.cite = cite;
        this.head = head;
        this.description = description;
    }

    public String getLink() {
        return link;
    }
    public String getCite() {
        return cite;
    }
    public String getHead() {
        return head;
    }
    public String getDescription() {
        return description;
    }
}
