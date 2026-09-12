package com.example.weatherapp.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.model.CityLocation

@Composable
fun CitySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    searchResults: List<CityLocation>,
    onCitySelected: (CityLocation) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Search city...", color = Color.Gray) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color(0xFF1E2435),
                unfocusedContainerColor = Color(0xFF1E2435),
                focusedBorderColor = Color(0xFF5B8FB9),
                unfocusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )

        AnimatedVisibility(
            visible = searchResults.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            DropdownMenu(
                expanded = searchResults.isNotEmpty(),
                onDismissRequest = { },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color(0xFF2B334B))
            ) {
                searchResults.forEach { city ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "${city.name}${if (city.country != null) ", ${city.country}" else ""}",
                                color = Color.White
                            )
                        },
                        onClick = { onCitySelected(city) }
                    )
                }
            }
        }
    }
}
