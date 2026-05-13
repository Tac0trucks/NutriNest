package com.example.nutrinest.screens.dashboard

import com.example.nutrinest.data.models.Meal

interface DashboardContract {
    interface View {
        fun updateMealList(meals: List<Meal>)
        fun showToast(message: String)
        fun showUserInfo(name: String)
        fun updateTotalCalories(calories: Int)
    }

    interface Presenter {
        fun attachView(view: View)
        fun loadInitialMeals()
        fun addNewMeal(type: String, desc: String, cal: Int) // Add Item Concept
        fun removeMeal(position: Int)                      // Remove Item Concept
        fun toggleMealStatus(position: Int)                // Click Listener Concept
    }
}