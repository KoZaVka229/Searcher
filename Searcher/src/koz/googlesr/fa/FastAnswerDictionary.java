package koz.googlesr.fa;

import koz.googlesr.IFastAnswer;

/** Быстрый ответ - словарь **/
public class FastAnswerDictionary implements IFastAnswer {
    private final String target;
    private final String[] values;

    /**
     * @param target Искомое слово
     * @param values Значения слова
     */
    public FastAnswerDictionary(String target, String[] values) {
        this.target = target;
        this.values = values;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        for (String s : values) {
            builder.append(s);
            builder.append('\n');
        }
        return String.format("Dict: %s\nValues: %s", target, builder.toString());
    }

    public String getTarget() {
        return target;
    }
    public String[] getValues() {
        return values;
    }
}
