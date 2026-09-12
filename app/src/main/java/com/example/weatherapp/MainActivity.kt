package com.example.weatherapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.presentation.CitySearchBar
import com.example.weatherapp.presentation.DailyForecast
import com.example.weatherapp.presentation.HourlyForecast
import com.example.weatherapp.presentation.WeatherCard
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                val viewModel: WeatherViewModel = hiltViewModel()
                val state = viewModel.state

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions()
                ) {
                    viewModel.loadWeatherForCurrentLocation()
                }

                LaunchedEffect(Unit) {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF131722))
                            .padding(innerPadding)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            CitySearchBar(
                                query = state.searchQuery,
                                onQueryChange = viewModel::onSearchQueryChange,
                                searchResults = state.searchResults,
                                onCitySelected = viewModel::onCitySelected
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(32.dp),
                                    color = Color.White
                                )
                            } else if (state.error != null) {
                                Text(
                                    text = "Error: ${state.error}",
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            } else {
                                AnimatedVisibility(
                                    visible = state.weatherInfo != null,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    Column {
                                        WeatherCard(
                                            state = state,
                                            onUnitToggle = viewModel::toggleTemperatureUnit
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        HourlyForecast(state = state)
                                        Spacer(modifier = Modifier.height(24.dp))
                                        DailyForecast(state = state)
                                        Spacer(modifier = Modifier.height(24.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
