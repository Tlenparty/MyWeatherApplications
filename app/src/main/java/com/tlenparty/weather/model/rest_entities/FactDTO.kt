package com.tlenparty.weather.model.rest_entities

data class FactDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?
)