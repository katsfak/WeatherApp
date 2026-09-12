package com.example.weatherapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQualityHourlyDto(
    @SerialName("european_aqi")
    val europeanAqi: List<Int?>? = null,
    @SerialName("uv_index")
    val uvIndex: List<Double?>? = null
)

@Serializable
data class AirQualityDto(
    @SerialName("hourly")
    val hourly: AirQualityHourlyDto? = null
)
