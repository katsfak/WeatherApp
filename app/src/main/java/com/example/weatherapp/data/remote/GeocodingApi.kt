package com.example.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {
    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 10
    ): GeocodingResultDto

    companion object {
        const val BASE_URL = "https://geocoding-api.open-meteo.com/"
    }
}
