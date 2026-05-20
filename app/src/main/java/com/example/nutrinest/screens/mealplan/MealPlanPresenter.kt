package com.example.nutrinest.screens.mealplan

import com.example.nutrinest.data.models.MealPlan

class MealPlanPresenter : MealPlanContract.Presenter {
    private var view: MealPlanContract.View? = null

    override fun attachView(view: MealPlanContract.View) {
        this.view = view
    }

    override fun loadMeals() {
        val meals = com.example.nutrinest.data.repositories.MealRepository.currentMeals
        if (!com.example.nutrinest.data.repositories.MealRepository.isInitialized && meals.isEmpty()) {
            com.example.nutrinest.data.repositories.MealRepository.isInitialized = true
            // Initial mock data to match Dashboard (if empty)
            meals.add(MealPlan(1, "Berry Smoothie Bowl", "08:00 AM - Breakfast", "A refreshing antioxidant-packed start.", 410, "15g", "60g", "8g"))
            meals.add(MealPlan(2, "Turkey & Veggie Wrap", "01:00 PM - Lunch", "Lean protein wrapped in whole grain.", 480, "35g", "45g", "15g"))
        }
        
        // Pass a copy so adapter modifications don't instantly affect the repository reference before animation ends
        val mealsCopy = ArrayList(meals)
        view?.displayMeals(mealsCopy)
        calculateDailyTotals(mealsCopy)
    }

    override fun addMeal(meal: MealPlan) {
        com.example.nutrinest.data.repositories.MealRepository.currentMeals.add(meal)
        loadMeals() // Reload UI
    }

    override fun deleteMeal(position: Int) {
        val meals = com.example.nutrinest.data.repositories.MealRepository.currentMeals
        if (position in 0 until meals.size) {
            val removed = meals.removeAt(position)
            loadMeals() // Reload UI
            view?.showToast("Removed: ${removed.title}")
        }
    }

    override fun toggleMealSelection(position: Int) {
        // Placeholder for checkbox toggle logic, if added later.
    }

    override fun generatePlan() {
        // Mock generation logic
        val meals = com.example.nutrinest.data.repositories.MealRepository.currentMeals
        meals.clear()
        val randomChoice = (0..1).random()
        if (randomChoice == 0) {
            meals.add(MealPlan(1, "Oatmeal Bowl", "08:00 AM - Breakfast", "Oats, berries, nuts", 350, "10g", "55g", "10g"))
            meals.add(MealPlan(2, "Chicken Salad", "01:00 PM - Lunch", "Grilled chicken, greens", 500, "40g", "20g", "25g"))
            meals.add(MealPlan(3, "Greek Yogurt", "04:00 PM - Snack", "Yogurt with honey", 150, "15g", "15g", "3g"))
            meals.add(MealPlan(4, "Salmon & Rice", "07:00 PM - Dinner", "Baked salmon, brown rice", 670, "39g", "55g", "30g"))
        } else {
            meals.add(MealPlan(1, "Scrambled Eggs", "08:00 AM - Breakfast", "Eggs, spinach, toast", 400, "25g", "30g", "20g"))
            meals.add(MealPlan(2, "Turkey Sandwich", "01:00 PM - Lunch", "Turkey breast, whole wheat", 450, "30g", "45g", "15g"))
            meals.add(MealPlan(3, "Protein Shake", "04:00 PM - Snack", "Whey protein, almond milk", 220, "24g", "10g", "4g"))
            meals.add(MealPlan(4, "Steak & Potatoes", "07:00 PM - Dinner", "Lean steak, sweet potato", 600, "25g", "60g", "25g"))
        }
        view?.displayMeals(meals)
        
        // Let's call the calculation method instead of hardcoding
        calculateDailyTotals(meals)
        view?.showToast("New Meal Plan Generated")
    }

    private fun calculateDailyTotals(list: ArrayList<MealPlan>) {
        var totalCal = 0
        var totalPro = 0
        var totalCarb = 0
        var totalFat = 0
        for (m in list) {
            totalCal += m.calories
            totalPro += m.protein.replace("g", "").toIntOrNull() ?: 0
            totalCarb += m.carbs.replace("g", "").toIntOrNull() ?: 0
            totalFat += m.fats.replace("g", "").toIntOrNull() ?: 0
        }
        view?.showDailyTotals(totalCal, totalPro, totalCarb, totalFat)
    }
}