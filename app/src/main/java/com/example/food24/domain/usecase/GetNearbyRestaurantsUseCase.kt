package com.example.food24.domain.usecase

import com.example.food24.data.model.RestaurantEntity
import com.example.food24.data.repository.RestaurantRepository

class GetNearbyRestaurantsUseCase(private val repository: RestaurantRepository) {

    suspend fun execute(lat: Double, lng: Double): Result<List<RestaurantEntity>> {
        return repository.fetchNearbyRestaurants(lat, lng)
    }
}