package com.coursework.socksmarket.model;

public enum Color {

    RED("Красный"), GREEN("Зеленый"), BLUE("Синий"), BLACK("Черный"), WHITE("Белый"), YELLOW("Желтый");

    private final String translate;

    Color(String translate) {
        this.translate = translate;
    }
}
