package com.example.weatherapp.domain.model

sealed class WeatherType(
    val weatherDesc: String,
    val iconEmoji: String
) {
    data object ClearSky : WeatherType("Clear Sky", "☀️")
    data object MainlyClear : WeatherType("Mainly Clear", "🌤️")
    data object Overcast : WeatherType("Overcast", "☁️")
    data object Foggy : WeatherType("Foggy", "🌫️")
    data object Rainy : WeatherType("Rainy", "🌧️")
    data object Snowy : WeatherType("Snowy", "❄️")
    data object Thunderstorm : WeatherType("Thunderstorm", "⛈️")
}
