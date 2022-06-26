package koz.googlesr.fa;

import koz.googlesr.IFastAnswer;

/** Быстрый ответ - текст из сайта **/
public class FastAnswerCiteText implements IFastAnswer {
    private final String answer;
    private final String head;
    private final String cite;
    private final String link;

    /**
     * @param answer Ответ на вопрос
     * @param head Заголовок страницы, где был взят ответ
     * @param cite Подраздел сайта, где был взят ответ
     * @param link Ссылка на страницу
     */
    public FastAnswerCiteText(String answer, String head, String cite, String link) {
        this.answer = answer;
        this.head = head;
        this.cite = cite;
        this.link = link;
    }

    @Override
    public String getMessage() {
        return answer;
    }

    public String getAnswer() {
        return answer;
    }
    public String getHead() {
        return head;
    }
    public String getCite() {
        return cite;
    }
    public String getLink() {
        return link;
    }
}