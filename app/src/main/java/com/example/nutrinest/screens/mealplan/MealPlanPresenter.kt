package com.example.nutrinest.screens.mealplan

import com.example.nutrinest.data.models.MealPlan

class MealPlanPresenter : MealPlanContract.Presenter {
    private var view: MealPlanContract.View? = null

    // REQUIREMENT: Using ArrayList as the data source
    private val meals = ArrayList<MealPlan>()

    override fun attachView(view: MealPlanContract.View) {
        this.view = view
    }

    override fun loadMeals() {
        // Initial mock data from Figma
        meals.add(MealPlan(1, "Berry Smoothie Bowl", "8:00 AM - Breakfast", "Blended acai...", 410, "12g", "62g", "14g"))
        meals.add(MealPlan(2, "Turkey & Veggie Wrap", "1:00 PM - Lunch", "Lean turkey breast...", 480, "32g", "48g", "12g"))
        view?.displayMeals(meals)
        
        view?.showDailyTotals(1670, 104, 190, 52) // Dynamic data
    }

    override fun addMeal(meal: MealPlan) {
        meals.add(meal) // Logic for "Add Item"
        view?.displayMeals(meals)
    }

    override fun deleteMeal(position: Int) {
        val removed = meals.removeAt(position) // Logic for "Remove Item"
        view?.displayMeals(meals)
        view?.showToast("Removed ${removed.title}")
    }

    override fun toggleMealSelection(position: Int) {
        meals[position].isChecked = !meals[position].isChecked
        view?.displayMeals(meals)
    }
}