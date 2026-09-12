package com.example.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityApi {
    @GET("v1/air-quality?hourly=european_aqi,uv_index")
    suspend fun getAirQuality(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): AirQualityDto

    companion object {
        const val BASE_URL = "https://air-quality-api.open-meteo.com/"
    }
}
