package com.geekbrains.myweatherapplicatinons.model.repository

import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.model.WeatherLoader
import com.geekbrains.myweatherapplicatinons.model.getRussianCities
import com.geekbrains.myweatherapplicatinons.model.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(lat: Double, lng: Double): Weather {
        val dto = WeatherLoader.loadWeather(lat, lng)
        // мапинг
        return Weather(
            temperature = dto?.fact?.temp,
            feelsLike = dto?.fact?.feels_like,
            condition = dto?.fact?.condition
        )
    }

    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}