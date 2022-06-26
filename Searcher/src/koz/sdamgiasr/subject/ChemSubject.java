package koz.sdamgiasr.subject;

import koz.sdamgiasr.ISubject;

public class ChemSubject implements ISubject {
    @Override
    public String getUrl() {
        return"https://chem8-vpr.sdamgia.ru/problem?id=";
    }
}
