package koz.wikisr;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

public class SectionBuilder {
    /** Текст всей страницы, который выдала wikipedia,
     * не содержит секции, которую удалось получить от wikipedia api **/
    public static class NameNotFoundException extends Exception {
        private final String name;
        public NameNotFoundException(String name) {
            super("Не удалось найти имя " + name);
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    /** Класс характерезует секцию статьи **/
    public static class Section {
        private final List<Section> subsections;
        private String name;
        private Section parent;
        private String value;

        /**
         * @param name Имя секции
         * @param value Текст секции
         * @param parent Секция уровнем выше
         * @param subsections Список секций уровнем ниже
         */
        public Section(String name, String value, Section parent, List<Section> subsections) {
            this.name = name;
            this.value = value;
            this.parent = parent;
            this.subsections = subsections;
        }
        public Section() {
            subsections = new ArrayList<>();
        }

        public String getName() { return name; }
        public String getValue() { return value; }
        public Section getParent() { return parent; }
        public Section[] getSubSections() { return subsections.toArray(new Section[0]); }
        public Section child(int index) {
            if (index >= subsections.size()) return null;
            return subsections.get(index);
        }
    }

    private final String content;
    private final List<Section> sections = new ArrayList<>();
    private Section context = null;

    /**
     * @param content Весь текст статьи, который будет использоваться для
     *                определения текста секций
     */
    public SectionBuilder(String content) {
        this.content = content;
    }

    /**
     * Добавляет подсекцию в выбранную секцию
     *
     * @param name Имя подсекции
     * @throws NameNotFoundException Текст всей страницы, который выдала wikipedia,
     * не содержит секции, которую удалось получить от wikipedia api
     */
    public void add(String name) throws NameNotFoundException {
        Section section = new Section();
        section.name = name;
        section.value = parseValue(name);
        section.parent = null;

        if (context == null) {
            sections.add(section);
        }
        else {
            section.parent = context;
            context.subsections.add(section);
        }
    }

    /**
     * Выбирает секцию для последующего добавления в неё секций
     *
     * @param i Индекс секции
     */
    public void select(int i) {
        if (i < 0) throw new IllegalArgumentException("Нельзя выбрать контекст по отрицательному индексу");

        if (context == null) {
            context = sections.get(i);
        }
        else {
            context = context.child(i);
        }
    }

    /** Выбирает секцию-родителя текущей выбранной секции **/
    public void up() {
        if (context != null) {
            context = context.parent;
        }
    }

    /** Сбрасывает выбор секции **/
    public void home() {
        context = null;
    }

    /**
     * Собирает все секции верхнего уровня в один список
     *
     * @return Список секций верхнего уровня
     */
    public List<Section> build() {
        return sections;
    }

    /**
     * Ищет текст секции во всём тексте статьи
     *
     * @param name Имя секции, для которой нужно найти текст
     * @return Текст секции
     * @throws NameNotFoundException Текст всей страницы, который выдала wikipedia,
     * не содержит секции, которую удалось получить от wikipedia api
     */
    private String parseValue(String name) throws NameNotFoundException {
        name = noTags(name);
        String section = String.format("== %s ==", name);
        int index = content.indexOf(section);
        if (index == -1) throw new NameNotFoundException(name);
        else index += section.length();

        int next_index = content.indexOf("==", index + 1);
        if (next_index == -1) next_index = content.length();

        String s = WikiPage.no_nbsp( content.substring(index, next_index) );
        return lstrip(s).strip();
    }

    /**
     * Удаляет знак "=" до первого несоответствия
     *
     * @param str Строка, где нужно удалить
     * @return Очищенная строка
     */
    private static String lstrip(String str) {
        int target_len = "=".length();

        while (str.length() > 0) {
            int i = str.indexOf("=");
            if (i == 0) str = str.substring(target_len);
            else break;
        }
        return str;
    }
    private static String noTags(String string) {
        return Jsoup.parse(string).text();
    }

    public static String sectionsTree(Section[] sections, String start) {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Section section : sections) {
            builder.append(start);
            builder.append(i);
            builder.append('.');
            builder.append(section.getName());
            builder.append('\n');

            Section[] newSections = section.getSubSections();
            if (newSections.length > 0)
                builder.append(sectionsTree(newSections, String.format("  %s%d.", start, i)));

            i++;
        }
        return builder.toString();
    }
}