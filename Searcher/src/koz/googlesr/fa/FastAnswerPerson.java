package koz.googlesr.fa;

import koz.googlesr.IFastAnswer;

/** Быстрый ответ - дынные о человеке **/
public class FastAnswerPerson implements IFastAnswer {
    private final String name;

    /**
     * @param name Имя человека
     */
    public FastAnswerPerson(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return name;
    }

    public String getName() {
        return name;
    }
}
