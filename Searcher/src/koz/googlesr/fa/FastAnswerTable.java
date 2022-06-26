package koz.googlesr.fa;

import koz.googlesr.IFastAnswer;

/** Быстрый ответ - таблица **/
public class FastAnswerTable implements IFastAnswer {
    private final String name;
    private final String[][] rows;

    /**
     * @param name Имя таблицы
     * @param rows Список строк
     */
    public FastAnswerTable(String name, String[][] rows) {
        this.name = name;
        this.rows = rows;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        int r = 1;
        for (String[] row : rows) {
            int c = 1;
            for (String col : row) {
                builder.append(' ');
                builder.append(r);
                builder.append('.');
                builder.append(c++);
                builder.append('.');
                builder.append(col);
            }
            builder.append('\n');
            r++;
        }
        return String.format("%s :\n%s", name, builder.toString());
    }

    public String getName() {
        return name;
    }
    public String[][] getRows() {
        return rows;
    }
}
