package com.example.weatherapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherCard(
    state: WeatherState,
    onUnitToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.currentWeatherData?.let { data ->
        val displayTemp = if (state.isFahrenheit) {
            (data.temperatureCelsius * 9 / 5 + 32).roundToInt()
        } else {
            data.temperatureCelsius.roundToInt()
        }
        val unitStr = if (state.isFahrenheit) "°F" else "°C"

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF2B334B),
                                Color(0xFF1F2537)
                            )
                        )
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.cityName ?: "My Location",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Today ${data.time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                            color = Color.LightGray,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF5B8FB9))
                                .clickable { onUnitToggle() }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (state.isFahrenheit) "°F" else "°C",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = data.weatherType.iconUrl,
                    contentDescription = data.weatherType.weatherDesc,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "$displayTemp$unitStr",
                    fontSize = 54.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 18.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    MetricDisplay(
                        value = "${data.pressure.roundToInt()} hPa",
                        unit = "Pressure",
                        icon = "⏱️"
                    )
                    MetricDisplay(
                        value = "${data.humidity.roundToInt()}%",
                        unit = "Humidity",
                        icon = "💧"
                    )
                    MetricDisplay(
                        value = "${data.windSpeed.roundToInt()} km/h",
                        unit = "Wind",
                        icon = "💨"
                    )
                }

                if (data.uvIndex != null || data.airQualityIndex != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        data.uvIndex?.let { uv ->
                            MetricDisplay(
                                value = "${uv.roundToInt()}",
                                unit = "UV Index",
                                icon = "☀️"
                            )
                        }
                        data.airQualityIndex?.let { aqi ->
                            MetricDisplay(
                                value = "$aqi",
                                unit = "AQI",
                                icon = "🍃"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MetricDisplay(
    value: String,
    unit: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 18.sp)
        Spacer(modifier = Modifier.size(8.dp))
        Column {
            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = unit,
                color = Color.LightGray,
                fontSize = 11.sp
            )
        }
    }
}
