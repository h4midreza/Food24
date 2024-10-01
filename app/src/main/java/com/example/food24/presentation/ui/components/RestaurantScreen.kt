package com.example.food24.presentation.ui.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.food24.data.model.RestaurantEntity
import com.example.food24.presentation.viewmodel.RestaurantViewModel
import com.google.maps.android.compose.*

@Composable
fun RestaurantScreen(viewModel: RestaurantViewModel) {
    var hasLocationPermission by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState()
    val restaurants by viewModel.restaurants.collectAsState()
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = hasLocationPermission
            )
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
    }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = hasLocationPermission
            )
        )
    }
    var selectedRestaurant by remember { mutableStateOf<RestaurantEntity?>(null) }

    val onSelectedRestaurant = { restaurant: RestaurantEntity ->
        selectedRestaurant = restaurant
    }
    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    LaunchedEffect(cameraPositionState.isMoving) {
        if (cameraPositionState.isMoving &&
            cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE
        ) {
            getRestaurants(cameraPositionState, viewModel)
        }
    }
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            getRestaurants(cameraPositionState, viewModel)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        DisplayMap(
            cameraPositionState,
            uiSettings,
            properties,
            viewModel,
            restaurants,
            onSelectedRestaurant
        )
        if (selectedRestaurant != null) {
            RestaurantPopup(
                restaurant = selectedRestaurant!!,
                onDismiss = { selectedRestaurant = null }
            )
        }
    }
}

private fun getRestaurants(
    cameraPositionState: CameraPositionState,
    viewModel: RestaurantViewModel
) {
    val lat = cameraPositionState.position.target.latitude
    val lng = cameraPositionState.position.target.longitude
    viewModel.fetchRestaurants(lat, lng)
}