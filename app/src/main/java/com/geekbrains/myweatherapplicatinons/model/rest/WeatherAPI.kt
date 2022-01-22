package com.geekbrains.myweatherapplicatinons.model.rest

import com.geekbrains.myweatherapplicatinons.model.rest.rest_entities.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface  WeatherAPI {
    @GET("informers")
    // название функции
    fun getWeather(
        //@Header("key") token : String
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ) : Call<WeatherDTO> // Call - означает запрос в котором будет возвращен CallBack
}