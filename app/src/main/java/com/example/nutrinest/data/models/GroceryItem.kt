package com.example.nutrinest.data.models

data class GroceryItem(
    val id: Int,
    val name: String,
    val quantity: String,
    var isChecked: Boolean = false
)