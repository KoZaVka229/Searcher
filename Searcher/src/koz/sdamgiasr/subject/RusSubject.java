package koz.sdamgiasr.subject;

import koz.sdamgiasr.ISubject;

public class RusSubject implements ISubject {
    @Override
    public String getUrl() {
        return"https://rus8-vpr.sdamgia.ru/problem?id=";
    }
}
