package koz.googlesr.fa;

/** Быстрый ответ найден, но не содержит нужных тегов **/
public class FactAnswerUnknownException extends Exception {
    public FactAnswerUnknownException() {
        super("Быстрый ответ найден, но не содержит нужных тегов");
    }
}
