package com.geekbrains.myweatherapplicatinons.model.repository

import com.geekbrains.myweatherapplicatinons.model.Weather

interface Repository {
    fun getWeatherFromServer(lat: Double, lng: Double) : Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
    fun saveEntity(weather: Weather)
    fun getAllHistory():List<Weather>
}