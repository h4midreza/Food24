package com.example.food24.presentation.ui.components

import androidx.compose.runtime.Composable
import com.example.food24.data.model.RestaurantEntity
import com.example.food24.presentation.viewmodel.RestaurantViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState

@Composable
fun DisplayMap(
    cameraPositionState: CameraPositionState,
    uiSettings: MapUiSettings,
    properties: MapProperties,
    viewModel: RestaurantViewModel,
    restaurants: List<RestaurantEntity>,
    onRestaurantSelected: (RestaurantEntity) -> Unit
) {
    GoogleMap(
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties,
        onMyLocationClick = {
            viewModel.fetchRestaurants(it.latitude, it.longitude)
        }
    ) {
        restaurants.forEach { restaurant ->
            AdvancedMarker(
                state = MarkerState(LatLng(restaurant.latitude, restaurant.longitude)),
                title = restaurant.name,
                onClick = {
                    onRestaurantSelected(restaurant)
                    true
                }
            )
        }
    }
}