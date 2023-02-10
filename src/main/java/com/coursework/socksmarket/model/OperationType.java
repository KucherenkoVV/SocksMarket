package com.coursework.socksmarket.model;

public enum OperationType {
    ACCEPTANCE("приемка"), WRITE_OFF("списание"), DELIVERY("выдача");

    private String translate;

    public String getTranslate() {
        return translate;
    }

    OperationType(String translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return translate;
    }
}
