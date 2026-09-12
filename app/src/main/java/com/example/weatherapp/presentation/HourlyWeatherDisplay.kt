package com.example.weatherapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.weatherapp.domain.model.WeatherData
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    isFahrenheit: Boolean = false,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedTime = weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm"))
    val tempVal = if (isFahrenheit) {
        (weatherData.temperatureCelsius * 9 / 5 + 32).roundToInt()
    } else {
        weatherData.temperatureCelsius.roundToInt()
    }
    val unitStr = if (isFahrenheit) "°F" else "°C"

    Column(
        modifier = modifier
            .width(80.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF232B3E))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            color = Color.LightGray,
            fontSize = 12.sp
        )
        AsyncImage(
            model = weatherData.weatherType.iconUrl,
            contentDescription = weatherData.weatherType.weatherDesc,
            modifier = Modifier
                .size(36.dp)
                .padding(vertical = 4.dp)
        )
        Text(
            text = "$tempVal$unitStr",
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
