package com.example.nutrinest.data.models

data class Restriction(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false
)
