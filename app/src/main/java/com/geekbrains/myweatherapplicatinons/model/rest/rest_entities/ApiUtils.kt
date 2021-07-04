package com.geekbrains.myweatherapplicatinons.model.rest.rest_entities

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {
    private val baseUrlMainPart = "https://api.weather.yandex.ru/"
    private val baseUrlVersion = "v2/"
    val baseUrl = "$baseUrlMainPart$baseUrlVersion"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS) // таймаут подключения 10с
        httpClient.readTimeout(10, TimeUnit.SECONDS) // таймаут на чтение 10с
        httpClient.writeTimeout(10, TimeUnit.SECONDS) // таймут на запись 10с
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder() // к оригинальному реквесту добавляем поля
                .header("X-Yandex-API-Key", "7a436743-4c9e-415e-9edc-cc6b53f7c987")
                .method(original.method(), original.body())
                .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
}