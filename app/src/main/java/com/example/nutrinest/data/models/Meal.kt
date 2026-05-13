package com.example.nutrinest.data.models

data class Meal(
    val id: Int,
    val type: String,
    val description: String,
    val calories: Int,
    var isCompleted: Boolean
)