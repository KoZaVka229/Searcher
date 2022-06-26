package koz.googlesr.fa;

import koz.googlesr.IFastAnswer;

/** Быстрый ответ - переводчик **/
public class FastAnswerTranslator implements IFastAnswer {
    private final String source;
    private final String translation;
    private final String[][] other_translations;

    /**
     * @param source Искомое слово
     * @param translation Перевод слова
     * @param other_translations Список вариантов перевода, где первый элемент - синоним, второй - его значения
     */
    public FastAnswerTranslator(String source, String translation, String[][] other_translations) {
        this.source = source;
        this.translation = translation;
        this.other_translations = other_translations;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        for (String[] obj : other_translations) {
            String name = obj[0];
            String values = obj[1];

            builder.append(name);
            builder.append(" == ");
            builder.append(values);
            builder.append('\n');
        }

        return String.format("%s == %s.\n%s", source, translation, builder.toString());
    }

    public String getSource() {
        return source;
    }
    public String getTranslation() {
        return translation;
    }
    public String[][] getOtherTranslations() {
        return other_translations;
    }

}
