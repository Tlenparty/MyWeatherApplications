package com.geekbrains.myweatherapplicatinons

// Чтобы класс всегда оставался в единственном экземпляре на протяжении работы приложения,
// достаточно заменить слово class на object
// Чтобы обратиться из Котлин Repository.getWeatherList()
// Чтобы обратиться из Java Repository.INSTANCE.getWeatherList()
object Repository {
    private val weatherList: List<Weather>

    // Блок init в котором происходит инициализация при создании объекта
    // В Kotlin нет слова new
     init {
          weatherList = listOf(Weather("Петрозаводск",10))
      }

    // var weatherList2 = ArrayList<Weather>()

    fun getWeatherList(): List<Weather>{
        return weatherList
    }



}