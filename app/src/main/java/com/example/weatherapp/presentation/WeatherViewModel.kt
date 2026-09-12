package com.example.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo(lat: Double = 51.5074, long: Double = -0.1278) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            val result = repository.getWeatherData(lat, long)
            state = result.fold(
                onSuccess = { weatherInfo ->
                    state.copy(
                        weatherInfo = weatherInfo,
                        isLoading = false,
                        error = null
                    )
                },
                onFailure = { exception ->
                    state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = exception.message ?: "An unknown error occurred"
                    )
                }
            )
        }
    }
}