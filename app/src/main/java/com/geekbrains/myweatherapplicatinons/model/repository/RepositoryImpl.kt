package com.geekbrains.myweatherapplicatinons.model.repository

import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.model.getRussianCities
import com.geekbrains.myweatherapplicatinons.model.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherFromSever() = Weather()
    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}