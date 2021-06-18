package com.geekbrains.myweatherapplicatinons.model
// Необходим для незваисиости бизнес логики от
interface Repository {

    fun getWeatherFromSever() : Weather

    fun getWeatherFromLocalStorage(): Weather
}