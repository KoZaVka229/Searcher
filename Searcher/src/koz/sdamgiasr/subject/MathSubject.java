package koz.sdamgiasr.subject;

import koz.sdamgiasr.ISubject;

public class MathSubject implements ISubject {
    @Override
    public String getUrl() {
        return"https://math8-vpr.sdamgia.ru/problem?id=";
    }
}
