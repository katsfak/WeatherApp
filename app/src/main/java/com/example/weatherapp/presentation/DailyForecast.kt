package com.example.weatherapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.weatherDataPerDay?.let { weatherMap ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "7-Day Forecast",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            weatherMap.entries.take(7).forEach { (dayIndex, dayList) ->
                val dayData = dayList.firstOrNull() ?: return@forEach
                val dayName = if (dayIndex == 0) "Today" else dayData.time.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

                val rawMax = dayList.maxOfOrNull { it.temperatureCelsius } ?: 0.0
                val rawMin = dayList.minOfOrNull { it.temperatureCelsius } ?: 0.0

                val maxTemp = if (state.isFahrenheit) (rawMax * 9 / 5 + 32).roundToInt() else rawMax.roundToInt()
                val minTemp = if (state.isFahrenheit) (rawMin * 9 / 5 + 32).roundToInt() else rawMin.roundToInt()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1E2435))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dayName,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = dayData.weatherType.iconUrl,
                            contentDescription = dayData.weatherType.weatherDesc,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = dayData.weatherType.weatherDesc,
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Text(
                        text = "$maxTemp° / $minTemp°",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
