package com.example.weatherapp.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.domain.model.WeatherData
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.model.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun Int.toWeatherType(): WeatherType {
    return when (this) {
        0 -> WeatherType.ClearSky
        1, 2 -> WeatherType.MainlyClear
        3 -> WeatherType.Overcast
        45, 48 -> WeatherType.Foggy
        51, 53, 55, 61, 63, 65, 80, 81, 82 -> WeatherType.Rainy
        71, 73, 75, 85, 86 -> WeatherType.Snowy
        95, 96, 99 -> WeatherType.Thunderstorm
        else -> WeatherType.ClearSky
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.indices.map { index ->
        val timeStr = time[index]
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val pressure = pressures[index]
        val windSpeed = windSpeeds[index]
        val humidity = humidities[index]

        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(timeStr, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = weatherCode.toWeatherType()
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { entry ->
        entry.value.map { it.data }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute >= 30) now.hour + 1 else now.hour
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}