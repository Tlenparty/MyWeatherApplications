package com.geekbrains.myweatherapplicatinons.viewmodel

import com.geekbrains.myweatherapplicatinons.model.Weather

sealed class AppState{
    // Три состояния экрана
    /**
      Классы Success и Error содержат данные, поэтому их получится сделать data-классами.
      Loading не содержит данных, его можно сделать object.
     */
    data class Success(val weatherData: Weather):AppState()
    data class Error(val error:Throwable):AppState()
    object Loading : AppState() // Статика синглтон
}