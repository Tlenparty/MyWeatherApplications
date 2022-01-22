package com.geekbrains.myweatherapplicatinons.viewmodel

import com.geekbrains.myweatherapplicatinons.model.Weather


sealed class AppState {

    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}