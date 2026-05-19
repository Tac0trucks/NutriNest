package com.example.nutrinest.data.models

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val token: String,
    var calorieGoal: Int = 2000,
    var restrictions: MutableList<String> = mutableListOf()
)