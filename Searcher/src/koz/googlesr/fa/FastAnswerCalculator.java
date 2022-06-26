package koz.googlesr.fa;

import koz.googlesr.IFastAnswer;

/** Быстрый ответ - калькулятор **/
public class FastAnswerCalculator implements IFastAnswer {
    private final String result;

    /**
     * @param result Результат от калькулятора
     */
    public FastAnswerCalculator(String result) {
        this.result = result;
    }

    @Override
    public String getMessage() {
        return "Calc: " + result;
    }

    public String getResult() {
        return result;
    }
}
