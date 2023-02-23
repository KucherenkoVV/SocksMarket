package com.coursework.socksmarket.model;

public enum OperationType {
    ACCEPTANCE("приемка"), WRITE_OFF("списание"), DELIVERY("выдача");

    private final String translate;

    OperationType(String translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return translate;
    }
}
