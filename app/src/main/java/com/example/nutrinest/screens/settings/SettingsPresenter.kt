package com.example.nutrinest.screens.settings

import com.example.nutrinest.data.models.SettingOption

class SettingsPresenter : SettingsContract.Presenter {
    private var view: SettingsContract.View? = null
    private val notificationList = ArrayList<SettingOption>()

    override fun attachView(view: SettingsContract.View) {
        this.view = view
    }

    override fun loadSettings() {
        // Concept: ArrayList usage for ListView
        notificationList.add(SettingOption(1, "Meal Alerts", "Get notified about upcoming meals", true))
        notificationList.add(SettingOption(2, "Water Alerts", "Reminders to stay hydrated", true))
        notificationList.add(SettingOption(3, "Weekly Report", "Receive weekly progress summaries", true))
        notificationList.add(SettingOption(4, "Marketing Emails", "Receive promotional content", false))

        view?.displayNotificationSettings(notificationList)
    }

    override fun toggleSetting(position: Int) {
        val item = notificationList[position]
        item.isEnabled = !item.isEnabled
        view?.onSettingChanged(item.title, item.isEnabled)
    }

    override fun updateCalorieTarget(value: Int) {
        // Logic to update calories
    }

    override fun handleLogout() {
        com.example.nutrinest.data.repositories.UserRepository.currentUser = null
        view?.navigateToLogin()
    }
}