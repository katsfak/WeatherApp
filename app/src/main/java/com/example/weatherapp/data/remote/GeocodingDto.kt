package com.example.weatherapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null
)

@Serializable
data class GeocodingResultDto(
    @SerialName("results")
    val results: List<CityDto>? = null
)
