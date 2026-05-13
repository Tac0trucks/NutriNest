package com.example.nutrinest.screens.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nutrinest.R

class DashboardFragment : Fragment(), DashboardContract.View {

    private lateinit var presenter: DashboardPresenter
    private lateinit var adapter: MealAdapter
    private lateinit var listView: ListView
    private lateinit var tvWelcomeMessage: TextView
    private lateinit var tvDashboardTitle: TextView
    private lateinit var tvCaloriesLabel: TextView
    private lateinit var tvCaloriesValue: TextView
    private lateinit var tvMealPlanOverview: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        tvWelcomeMessage = view.findViewById(R.id.tvWelcomeMessage)
        tvDashboardTitle = view.findViewById(R.id.tvDashboardTitle)
        tvCaloriesLabel = view.findViewById(R.id.tvCaloriesLabel)
        tvCaloriesValue = view.findViewById(R.id.tvCaloriesValue)
        tvMealPlanOverview = view.findViewById(R.id.tvMealPlanOverview)
        
        tvDashboardTitle.text = "Dashboard"
        tvCaloriesLabel.text = "Today's Calories"
        tvCaloriesValue.text = "1,650 / 2,000 cal"
        tvMealPlanOverview.text = "Today's Meal Plan Overview"

        presenter = DashboardPresenter()
        presenter.attachView(this)

        listView = view.findViewById(R.id.lvMealPlan)
        adapter = MealAdapter(requireContext(), emptyList())
        listView.adapter = adapter

        // --- CONCEPT: Custom ListView Click Listener ---
        listView.setOnItemClickListener { _, _, position, _ ->
            presenter.toggleMealStatus(position)
            showToast("Meal status updated!")
        }

        // --- CONCEPT: Custom ListView Long Click Listener ---
        listView.setOnItemLongClickListener { _, _, position, _ ->
            presenter.removeMeal(position) // Triggers the Remove logic
            true // Tells Android the click is handled
        }

        // Simulating "Add Item" from a button click (as per requirement)
        val btnAddSnack = view.findViewById<android.widget.Button>(R.id.btnAddSnack)
        btnAddSnack.text = "+ Add Snack"
        btnAddSnack.setOnClickListener {
            presenter.addNewMeal("Snack", "Almonds and Apple", 150)
        }

        presenter.loadInitialMeals()

        return view
    }

    override fun showUserInfo(name: String) {
        tvWelcomeMessage.text = "Welcome back, $name!"
    }

    override fun updateMealList(meals: List<com.example.nutrinest.data.models.Meal>) {
        adapter.updateList(meals)
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateTotalCalories(calories: Int) {
        // Formatting with commas could be added, but simple string concatenation works for now
        tvCaloriesValue.text = "%,d / 2,000 cal".format(calories)
    }
}