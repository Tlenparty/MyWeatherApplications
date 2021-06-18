package com.geekbrains.myweatherapplicatinons.other

// Чтобы класс всегда оставался в единственном экземпляре на протяжении работы приложения,
// достаточно заменить слово class на object
// Чтобы обратиться из Котлин Repository.getWeatherList()
// Чтобы обратиться из Java Repository.INSTANCE.getWeatherList()
object Repository {
    private val weatherList: List<WeatherOther>

    // Блок init в котором происходит инициализация при создании объекта
    // В Kotlin нет слова new
     init {
          weatherList = listOf(WeatherOther("Петрозаводск",10))
      }

    // var weatherList2 = ArrayList<Weather>()

    fun getWeatherList(): List<WeatherOther>{
        return weatherList
    }



}