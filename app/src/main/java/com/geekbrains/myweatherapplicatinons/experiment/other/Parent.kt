package com.geekbrains.myweatherapplicatinons.experiment.other

open class Parent {
    private val first:String = "first" // видно только внутри класса Parent
    protected val second: String = "second" // видно внутри Parent  и его наследников
    val third: String = "third" // public по умолчанию видно везде

    protected class Inner{ // класс виден только наследникам Parent
        private val fourth:String = "fourth" // видно только внутри класса Inner
    }
}