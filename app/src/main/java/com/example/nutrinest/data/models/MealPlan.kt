package com.example.nutrinest.data.models
data class MealPlan(
    val id: Int,
    val title: String,
    val timeAndType: String,
    val description: String,
    val calories: Int,
    val protein: String,
    val carbs: String,
    val fats: String,
    var isChecked: Boolean = false
)