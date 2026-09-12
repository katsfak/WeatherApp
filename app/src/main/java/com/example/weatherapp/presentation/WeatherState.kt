package com.example.weatherapp.presentation

import com.example.weatherapp.domain.model.CityLocation
import com.example.weatherapp.domain.model.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val cityName: String? = null,
    val searchQuery: String = "",
    val searchResults: List<CityLocation> = emptyList(),
    val isSearching: Boolean = false,
    val isFahrenheit: Boolean = false
)
