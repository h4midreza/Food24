package com.example.food24.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.food24.data.repository.RestaurantRepository
import com.example.food24.domain.usecase.GetNearbyRestaurantsUseCase
import com.example.food24.presentation.ui.components.RestaurantScreen
import com.example.food24.presentation.viewmodel.RestaurantViewModel
import com.example.food24.presentation.viewmodel.RestaurantViewModelFactory

class RestaurantPresentation : ComponentActivity() {
    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = RestaurantRepository()
        val useCase = GetNearbyRestaurantsUseCase(repository)
        viewModel = ViewModelProvider(
            this,
            RestaurantViewModelFactory(useCase)
        ).get(RestaurantViewModel::class.java)
        setContent {
            RestaurantScreen(viewModel)
        }
    }
}