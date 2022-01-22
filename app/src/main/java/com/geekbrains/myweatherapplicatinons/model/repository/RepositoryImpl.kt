package com.geekbrains.myweatherapplicatinons.model.repository

import com.geekbrains.myweatherapplicatinons.model.*
import com.geekbrains.myweatherapplicatinons.model.database.Database
import com.geekbrains.myweatherapplicatinons.model.database.HistoryEntity
import com.geekbrains.myweatherapplicatinons.model.rest.WeatherRepo

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(lat: Double, lng: Double): Weather {
        val dto = WeatherRepo.api.getWeather(lat, lng).execute().body()
        return Weather(
            temperature = dto?.fact?.temp,
            feelsLike = dto?.fact?.feelsLike,
            condition = dto?.fact?.condition
        )
    }

    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
    override fun getAllHistory(): List<Weather> =
        convertHistoryEntityToWeather(Database.db.historyDao().all())


    override fun saveEntity(weather: Weather) {
        Database.db.historyDao().insert(convertWeatherToEntity(weather))
    }

    private fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> =
        entityList.map {
            Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.condition)
        }


    private fun convertWeatherToEntity(weather: Weather): HistoryEntity =
        HistoryEntity(
            0, weather.city.city,
            weather.temperature ?: 0,
            weather.condition ?: ""
        )
}