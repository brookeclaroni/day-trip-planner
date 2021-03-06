package com.example.day_trip_planner

data class Entry(
    val name: String,
    val pricePoint: String?,
    val rating: String,
    val address: String,
    val address2: String,
    val phone: String?,
    val url: String,
    val lat : Double,
    val lon : Double
)