package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(entity: WeatherEntity)

    @Query("SELECT * FROM weatherentity WHERE id = 0")
    suspend fun getWeather(): WeatherEntity?
}
