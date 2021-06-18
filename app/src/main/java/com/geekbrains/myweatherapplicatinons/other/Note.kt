package com.geekbrains.myweatherapplicatinons.other

/**
 * Ключевое слово data перед декларацией класса даёт компилятору понять, что мы хотим,
 * чтобы он сгенерировал методы доступа к свойствам, а также методы equals() и hashCode(),
 * toString(). equals() и hashCode() будут учитывать все свойства, объявленные в главном
 * конструкторе.
 *
 */

data class Note(val title: String, val note: String, val color: Int)