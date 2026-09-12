package com.example.weatherapp.domain.model

sealed class WeatherType {
    object ClearSky : WeatherType()
    object MainlyClear : WeatherType()
    object Overcast : WeatherType()
    object Foggy : WeatherType()
    object Rainy : WeatherType()
    object Snowy : WeatherType()
    object Thunderstorm : WeatherType()
}