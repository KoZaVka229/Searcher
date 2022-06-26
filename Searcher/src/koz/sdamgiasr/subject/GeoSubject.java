package koz.sdamgiasr.subject;

import koz.sdamgiasr.ISubject;

public class GeoSubject implements ISubject {
    @Override
    public String getUrl() {
        return"https://geo8-vpr.sdamgia.ru/problem?id=";
    }
}
