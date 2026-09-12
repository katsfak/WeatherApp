package com.example.weatherapp.domain.model

sealed class WeatherType(
    val weatherDesc: String,
    val iconEmoji: String,
    val iconUrl: String
) {
    data object ClearSky : WeatherType("Clear Sky", "☀️", "https://openweathermap.org/img/wn/01d@2x.png")
    data object MainlyClear : WeatherType("Mainly Clear", "🌤️", "https://openweathermap.org/img/wn/02d@2x.png")
    data object Overcast : WeatherType("Overcast", "☁️", "https://openweathermap.org/img/wn/04d@2x.png")
    data object Foggy : WeatherType("Foggy", "🌫️", "https://openweathermap.org/img/wn/50d@2x.png")
    data object Rainy : WeatherType("Rainy", "🌧️", "https://openweathermap.org/img/wn/10d@2x.png")
    data object Snowy : WeatherType("Snowy", "❄️", "https://openweathermap.org/img/wn/13d@2x.png")
    data object Thunderstorm : WeatherType("Thunderstorm", "⛈️", "https://openweathermap.org/img/wn/11d@2x.png")
}
