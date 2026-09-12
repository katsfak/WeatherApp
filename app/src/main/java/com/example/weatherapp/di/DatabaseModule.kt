package com.example.weatherapp.di

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): WeatherDatabase {
        return Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "weather_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.dao
    }
}
