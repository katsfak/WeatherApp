package com.example.weatherapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val IS_FAHRENHEIT = booleanPreferencesKey("is_fahrenheit")
    }

    val isFahrenheit: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.IS_FAHRENHEIT] ?: false
    }

    suspend fun setFahrenheit(isFahrenheit: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_FAHRENHEIT] = isFahrenheit
        }
    }
}
