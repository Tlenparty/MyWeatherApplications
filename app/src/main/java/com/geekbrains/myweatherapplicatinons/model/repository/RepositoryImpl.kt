package com.geekbrains.myweatherapplicatinons.model.repository

import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.model.getRussianCities
import com.geekbrains.myweatherapplicatinons.model.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherFromSever(): Weather {
        return Weather()
    }

    // getRussianCities() и getWorldCities() доступный так как они созданы за пределаеми класса
    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}