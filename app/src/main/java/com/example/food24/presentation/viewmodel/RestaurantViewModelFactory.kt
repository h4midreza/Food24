package com.example.food24.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.food24.domain.usecase.GetNearbyRestaurantsUseCase

class RestaurantViewModelFactory(
    private val getNearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            return RestaurantViewModel(getNearbyRestaurantsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class User(){

}


class HamidrezaFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}