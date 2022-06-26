package koz.sdamgiasr.subject;

import koz.sdamgiasr.ISubject;

public class PhysSubject implements ISubject {
    @Override
    public String getUrl() {
        return"https://phys8-vpr.sdamgia.ru/problem?id=";
    }
}
