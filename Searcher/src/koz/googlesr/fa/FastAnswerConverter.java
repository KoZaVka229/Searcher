package koz.googlesr.fa;

import koz.googlesr.IFastAnswer;

/** Быстрый ответ - конвертер единиц **/
public class FastAnswerConverter implements IFastAnswer {
    private final String fact_answer;
    private final String converter_type;
    private final String exact_result;
    private final String formula;

    /**
     * @param fact_answer Быстрый ответ(может быть округлён)
     * @param converter_type Тип конвертера(например, масса или объём)
     * @param exact_result Точный ответ(не округляется)
     * @param formula Формула словами
     */
    public FastAnswerConverter(String fact_answer, String converter_type, String exact_result, String formula) {
        this.fact_answer = fact_answer;
        this.converter_type = converter_type;
        this.exact_result = exact_result;
        this.formula = formula;
    }

    @Override
    public String getMessage() {
        return String.format("Тип - %s; результат - %s; %s", converter_type, exact_result, formula);
    }

    public String getFactAnswer() {
        return fact_answer;
    }
    public String getConverterType() {
        return converter_type;
    }
    public String getExactResults() {
        return exact_result;
    }
    public String getFormula() {
        return formula;
    }
}
