package com.example.weatherapp.domain.model

data class CityLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null
)
