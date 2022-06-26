package koz.sdamgiasr.subject;

import koz.sdamgiasr.ISubject;

public class BioSubject implements ISubject {
    @Override
    public String getUrl() {
        return"https://bio8-vpr.sdamgia.ru/problem?id=";
    }
}
