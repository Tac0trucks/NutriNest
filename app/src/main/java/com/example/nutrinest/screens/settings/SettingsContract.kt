package com.example.nutrinest.screens.settings

import com.example.nutrinest.data.models.SettingOption

interface SettingsContract {
    interface View {
        fun displayNotificationSettings(list: ArrayList<SettingOption>)
        fun onSettingChanged(name: String, state: Boolean)
        fun navigateToLogin()
    }

    interface Presenter {
        fun attachView(view: View)
        fun loadSettings()
        fun toggleSetting(position: Int) // CONCEPT: Click/Toggle logic
        fun updateCalorieTarget(value: Int)
        fun handleLogout()
    }
}