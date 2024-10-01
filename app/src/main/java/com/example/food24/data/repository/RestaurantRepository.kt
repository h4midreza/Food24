package com.example.food24.data.repository

import android.util.Log
import com.example.food24.data.model.RestaurantEntity
import com.example.food24.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class RestaurantRepository {
    private val TAG = "RestaurantRepository"

    suspend fun fetchNearbyRestaurants(lat: Double, lng: Double): Result<List<RestaurantEntity>> {
        val apiKey = Constants.FOURSQUARE_API_KEY
        val url = "https://api.foursquare.com/v3/places/search?ll=$lat,$lng&radius=1000&categories=13065&limit=10"

        return try {
            val response = withContext(Dispatchers.IO) {
                URL(url).openConnection().apply {
                    setRequestProperty("Authorization", apiKey)
                }.getInputStream().bufferedReader().readText()
            }

            val restaurants = mutableListOf<RestaurantEntity>()
            Log.d(TAG, "fetchNearbyRestaurants: $response")

            val json = JSONObject(response)
            val results = json.getJSONArray(Constants.RESULTS)
            for (i in 0 until results.length()) {
                val item = results.getJSONObject(i)
                val name = item.getString(Constants.NAME)
                val location = item.getJSONObject(Constants.LOCATION)
                val address = location.getString(Constants.ADDRESS)
                val main = item.getJSONObject(Constants.GEOCODE).getJSONObject(Constants.MAIN)
                val latitude = main.getDouble(Constants.LATITUDE)
                val longitude = main.getDouble(Constants.LONGITUDE)
                restaurants.add(RestaurantEntity(name, latitude, longitude, address))
            }

            if (restaurants.isEmpty()) {
                Result.failure(Throwable("No restaurants found"))
            } else {
                Result.success(restaurants)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching restaurants", e)
            Result.failure(Throwable("Error fetching restaurants: ${e.message}"))
        }
    }
}