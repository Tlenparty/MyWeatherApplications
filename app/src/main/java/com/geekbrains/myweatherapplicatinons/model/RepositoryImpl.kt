package com.geekbrains.myweatherapplicatinons.model

class RepositoryImpl : Repository {
    override fun getWeatherFromSever(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather = Weather()
}