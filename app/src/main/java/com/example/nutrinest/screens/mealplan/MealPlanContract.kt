package com.example.nutrinest.screens.mealplan


import com.example.nutrinest.data.models.MealPlan

interface MealPlanContract {
    interface View {
        fun displayMeals(meals: ArrayList<MealPlan>)
        fun showToast(message: String)
        fun showDailyTotals(cals: Int, protein: Int, carbs: Int, fats: Int)
    }

    interface Presenter {
        fun attachView(view: View)
        fun loadMeals()
        fun addMeal(meal: MealPlan)      // CONCEPT: Add Item
        fun deleteMeal(position: Int)    // CONCEPT: Remove Item
        fun toggleMealSelection(position: Int) // CONCEPT: Click Listener Logic
        fun generatePlan()
    }
}