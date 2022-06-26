package koz.googlesr.fa;

import koz.googlesr.IFastAnswer;

/** Быстрый ответ - тескт из википедии **/
public class FastAnswerWikiText implements IFastAnswer {
    private final String text;

    public FastAnswerWikiText(String text) {
        this.text = text;
    }

    @Override
    public String getMessage() {
        return text;
    }
}
