package com.example.nutrinest.data.models

data class SettingOption(
    val id: Int,
    val title: String,
    val subtitle: String,
    var isEnabled: Boolean = false
)