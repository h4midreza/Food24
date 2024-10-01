package com.example.food24.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.food24.data.model.RestaurantEntity

@Composable
fun RestaurantPopup(restaurant: RestaurantEntity, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(restaurant.name) },
        text = { Text(restaurant.address) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}