package com.geekbrains.myweatherapplicatinons.model.repository

import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.model.WeatherLoader
import com.geekbrains.myweatherapplicatinons.model.getRussianCities
import com.geekbrains.myweatherapplicatinons.model.getWorldCities
import com.geekbrains.myweatherapplicatinons.model.rest.WeatherRepo

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(lat: Double, lng: Double): Weather {
        //val dto = WeatherLoader.loadWeather(lat, lng)
        val dto = WeatherRepo.api.getWeather(lat, lng).execute().body()
        return Weather(
            temperature = dto?.fact?.temp,
            feelsLike = dto?.fact?.feelsLike,
            condition = dto?.fact?.condition
        )
    }

    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}