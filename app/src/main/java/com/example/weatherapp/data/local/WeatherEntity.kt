package com.example.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey val id: Int = 0,
    val weatherDataJson: String,
    val timestamp: Long = System.currentTimeMillis()
)
