package com.coursework.socksmarket.model;

public enum Color {

//    RED(), GREEN(), BLUE(), BLACK(), WHITE(), YELLOW();
    RED("Красный"), GREEN("Зеленый"), BLUE("Синий"), BLACK("Черный"), WHITE("Белый"), YELLOW("Желтый");

    private String translate;

    Color(String translate) {
        this.translate = translate;
    }
}
