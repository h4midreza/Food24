package com.example.food24.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food24.data.model.RestaurantEntity
import com.example.food24.domain.usecase.GetNearbyRestaurantsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RestaurantViewModel(private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase) :
    ViewModel() {

    private val _restaurants = MutableStateFlow<List<RestaurantEntity>>(emptyList())
    val restaurants: StateFlow<List<RestaurantEntity>> get() = _restaurants

    fun fetchRestaurants(lat: Double, lng: Double) {
        viewModelScope.launch {
            val result = getNearbyRestaurantsUseCase.execute(lat, lng)
            if (result.isSuccess) {
                _restaurants.value = result.getOrDefault(emptyList())
            } else {
                result.exceptionOrNull()?.let {
                    _restaurants.value = emptyList()
                }
            }
        }
    }
}