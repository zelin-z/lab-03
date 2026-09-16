package com.example.listycity3

import androidx.compose.runtime.mutableStateListOf

class CityRepository {

    private val _cities = mutableStateListOf(
        City(name = "Edmonton", province = "AB"),
        City(name = "Vancouver", province = "BC"),
        City(name = "Toronto", province = "ON")
    )

    val cities: List<City>
        get() = _cities

    fun addCity(city: City) {
        _cities.add(city)
    }

    fun updateCity(index: Int, updatedCity: City) {
        if (index in _cities.indices) {
            _cities[index] = updatedCity
        }
    }
}