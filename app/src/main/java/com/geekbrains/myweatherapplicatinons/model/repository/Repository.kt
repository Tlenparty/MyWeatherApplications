package com.geekbrains.myweatherapplicatinons.model.repository

import com.geekbrains.myweatherapplicatinons.model.Weather


// Необходим для незваисиости бизнес логики от
interface Repository {
    fun getWeatherFromServer(lat: Double, lng: Double) : Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}