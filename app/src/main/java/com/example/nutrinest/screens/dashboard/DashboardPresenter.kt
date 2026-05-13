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

        // Adding initial data from Figma
        mealList.add(Meal(1, "Breakfast", "Greek yogurt bowl", 420, true))
        mealList.add(Meal(2, "Lunch", "Grilled chicken salad", 580, true))
        mealList.add(Meal(3, "Dinner", "Salmon with vegetables", 650, false))
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