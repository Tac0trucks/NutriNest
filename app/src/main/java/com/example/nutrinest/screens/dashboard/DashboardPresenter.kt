package com.example.nutrinest.screens.dashboard
import com.example.nutrinest.data.models.Meal


class DashboardPresenter : DashboardContract.Presenter {
    private var view: DashboardContract.View? = null
    private val mealList = mutableListOf<Meal>() // In-memory list for MVP logic

    override fun attachView(view: DashboardContract.View) {
        this.view = view
    }

    private fun updateView() {
        view?.updateMealList(mealList)
        val totalCalories = mealList.sumOf { it.calories }
        view?.updateTotalCalories(totalCalories)
    }

    override fun loadInitialMeals() {
        // Load User Info
        val user = com.example.nutrinest.data.repositories.UserRepository.currentUser
        val name = user?.fullName ?: "User"
        view?.showUserInfo(name)

        // Read from global MealRepository
        val globalMeals = com.example.nutrinest.data.repositories.MealRepository.currentMeals
        mealList.clear()
        
        if (globalMeals.isNotEmpty()) {
            globalMeals.forEachIndexed { index, plan ->
                // "08:00 AM - Breakfast" -> "Breakfast"
                val type = plan.timeAndType.split(" - ").getOrNull(1) ?: "Meal"
                mealList.add(Meal(plan.id, type, plan.title, plan.calories, false))
            }
        } else {
            // Adding initial data to match MealPlan if empty
            mealList.add(Meal(1, "Breakfast", "Berry Smoothie Bowl", 410, false))
            mealList.add(Meal(2, "Lunch", "Turkey & Veggie Wrap", 480, false))
        }
        
        updateView()
    }

    override fun addNewMeal(type: String, desc: String, cal: Int) {
        val newMeal = Meal(mealList.size + 1, type, desc, cal, false)
        mealList.add(newMeal) // "Add Item" Implementation
        updateView()
        view?.showToast("Added $type to your plan")
    }

    override fun removeMeal(position: Int) {
        val removed = mealList.removeAt(position) // "Remove Item" Implementation
        updateView()
        view?.showToast("Removed ${removed.type}")
    }

    override fun toggleMealStatus(position: Int) {
        mealList[position].isCompleted = !mealList[position].isCompleted
        updateView()
    }
}