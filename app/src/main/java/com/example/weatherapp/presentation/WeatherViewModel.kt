package com.example.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.UserPreferencesRepository
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.model.CityLocation
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            userPreferencesRepository.isFahrenheit.collect { isFahrenheit ->
                state = state.copy(isFahrenheit = isFahrenheit)
            }
        }
    }

    fun toggleTemperatureUnit() {
        viewModelScope.launch {
            userPreferencesRepository.setFahrenheit(!state.isFahrenheit)
        }
    }

    fun loadWeatherForCurrentLocation() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                loadWeatherInfo(location.latitude, location.longitude, "My Location")
            } else {
                loadWeatherInfo(51.5074, -0.1278, "London")
            }
        }
    }

    fun loadWeatherInfo(lat: Double, long: Double, cityName: String = "Location") {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
                cityName = cityName,
                searchResults = emptyList(),
                searchQuery = ""
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

    fun onSearchQueryChange(query: String) {
        state = state.copy(searchQuery = query)
        searchJob?.cancel()
        if (query.length < 2) {
            state = state.copy(searchResults = emptyList(), isSearching = false)
            return
        }
        searchJob = viewModelScope.launch {
            delay(300)
            state = state.copy(isSearching = true)
            val result = repository.searchCity(query)
            result.onSuccess { cities ->
                state = state.copy(searchResults = cities, isSearching = false)
            }.onFailure {
                state = state.copy(searchResults = emptyList(), isSearching = false)
            }
        }
    }

    fun onCitySelected(city: CityLocation) {
        loadWeatherInfo(city.latitude, city.longitude, city.name)
    }
}
