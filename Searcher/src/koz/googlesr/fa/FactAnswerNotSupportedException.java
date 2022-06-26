package koz.googlesr.fa;

/** Быстрый ответ есть, но не поддерживается **/
public class FactAnswerNotSupportedException extends Exception {
    public FactAnswerNotSupportedException() {
        super("Быстрый ответ есть, но не поддерживается");
    }
}
