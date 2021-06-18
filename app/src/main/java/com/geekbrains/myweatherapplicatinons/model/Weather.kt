package com.geekbrains.myweatherapplicatinons.model

// City (значение по умолчанию)
data class Weather(
/*    val city:City = City(
        "Москва",
        55.123,
        37.1231231
    ),*/
    val city: City = City("СПб"), // значение по умолчанию
    val temperature: Int = 0,
    val feelsLike: Int = 0
)
