package com.example.listycity3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    modifier: Modifier = Modifier,
    onAddCity: (City) -> Unit = {},
    onUpdateCity: (Int, City) -> Unit = { _, _ -> }
) {
    var cityName by remember { mutableStateOf("") }
    var provinceName by remember { mutableStateOf("") }

    // Track the selected row so duplicate cities can be edited separately.
    var selectedCityIndex by remember {
        mutableStateOf<Int?>(null)
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("City") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        OutlinedTextField(
            value = provinceName,
            onValueChange = { provinceName = it },
            label = { Text("Province") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Button(
            onClick = {
                if (cityName.isNotBlank() && provinceName.isNotBlank()) {
                    val newCity = City(
                        name = cityName,
                        province = provinceName
                    )

                    val index = selectedCityIndex

                    if (index == null) {
                        onAddCity(newCity)
                    } else {
                        onUpdateCity(index, newCity)
                    }

                    // Return to add mode after saving.
                    cityName = ""
                    provinceName = ""
                    selectedCityIndex = null
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = if (selectedCityIndex == null) {
                    "Add City"
                } else {
                    "Update City"
                }
            )
        }

        LazyColumn {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    onClick = {
                        selectedCityIndex = index
                        cityName = city.name
                        provinceName = city.province
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            )
        )
    }
}