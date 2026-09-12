package com.example.weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.toWeatherInfo
import com.example.weatherapp.domain.model.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, long: Double): Result<WeatherInfo> {
        return try {
            Result.success(
                api.getWeatherData(lat, long).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}