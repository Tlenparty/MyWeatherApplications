package com.geekbrains.myweatherapplicatinons.model.rest

import com.geekbrains.myweatherapplicatinons.model.rest.rest_entities.ApiUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRepo {
    val api: WeatherAPI by lazy {
        // адаптер с url converter ...
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(WeatherAPI::class.java)
    }
}
