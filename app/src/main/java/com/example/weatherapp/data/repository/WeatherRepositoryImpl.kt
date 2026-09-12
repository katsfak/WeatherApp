package com.example.weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.local.WeatherEntity
import com.example.weatherapp.data.remote.AirQualityApi
import com.example.weatherapp.data.remote.GeocodingApi
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.WeatherDto
import com.example.weatherapp.data.remote.toWeatherInfo
import com.example.weatherapp.domain.model.CityLocation
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val geocodingApi: GeocodingApi,
    private val airQualityApi: AirQualityApi,
    private val dao: WeatherDao
) : WeatherRepository {

    private val json = Json { ignoreUnknownKeys = true }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, long: Double): Result<WeatherInfo> {
        return try {
            val remoteDto = api.getWeatherData(lat, long)
            val jsonString = json.encodeToString(remoteDto)
            dao.insertWeather(WeatherEntity(weatherDataJson = jsonString))

            val weatherInfo = remoteDto.toWeatherInfo()
            val airQualityDto = try { airQualityApi.getAirQuality(lat, long) } catch (_: Exception) { null }

            val currentUv = airQualityDto?.hourly?.uvIndex?.firstOrNull { it != null }
            val currentAqi = airQualityDto?.hourly?.europeanAqi?.firstOrNull { it != null }

            val updatedCurrent = weatherInfo.currentWeatherData?.copy(
                uvIndex = currentUv,
                airQualityIndex = currentAqi
            )

            Result.success(weatherInfo.copy(currentWeatherData = updatedCurrent))
        } catch (e: Exception) {
            e.printStackTrace()
            val cachedEntity = dao.getWeather()
            if (cachedEntity != null) {
                try {
                    val cachedDto = json.decodeFromString<WeatherDto>(cachedEntity.weatherDataJson)
                    Result.success(cachedDto.toWeatherInfo())
                } catch (cacheException: Exception) {
                    Result.failure(e)
                }
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun searchCity(query: String): Result<List<CityLocation>> {
        return try {
            val response = geocodingApi.searchCity(query)
            val cities = response.results?.map { dto ->
                CityLocation(
                    name = dto.name,
                    latitude = dto.latitude,
                    longitude = dto.longitude,
                    country = dto.country
                )
            } ?: emptyList()
            Result.success(cities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
