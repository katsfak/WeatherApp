package com.example.weatherapp.di

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.remote.AirQualityApi
import com.example.weatherapp.data.remote.GeocodingApi
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGeocodingApi(): GeocodingApi {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(GeocodingApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GeocodingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAirQualityApi(): AirQualityApi {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(AirQualityApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AirQualityApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        geocodingApi: GeocodingApi,
        airQualityApi: AirQualityApi,
        dao: WeatherDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, geocodingApi, airQualityApi, dao)
    }
}
