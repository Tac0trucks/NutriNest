package com.example.nutrinest.screens.mealplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nutrinest.R
import com.example.nutrinest.data.models.MealPlan

class MealPlanFragment : Fragment(), MealPlanContract.View {

    private lateinit var presenter: MealPlanPresenter
    private lateinit var listView: ListView
    private lateinit var tvMealPlansTitle: TextView
    private lateinit var tvMealPlansSubtitle: TextView
    private lateinit var btnGenerate: Button
    private lateinit var btnExportPlan: Button
    private lateinit var tvDailyTotalLabel: TextView
    private lateinit var tvDailyTotalCals: TextView
    private lateinit var tvProteinLabel: TextView
    private lateinit var tvProteinValue: TextView
    private lateinit var tvCarbsLabel: TextView
    private lateinit var tvCarbsValue: TextView
    private lateinit var tvFatsLabel: TextView
    private lateinit var tvFatsValue: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_plan, container, false)

        tvMealPlansTitle = view.findViewById(R.id.tvMealPlansTitle)
        tvMealPlansSubtitle = view.findViewById(R.id.tvMealPlansSubtitle)
        btnGenerate = view.findViewById(R.id.btnGenerate)
        btnExportPlan = view.findViewById(R.id.btnExportPlan)
        tvDailyTotalLabel = view.findViewById(R.id.tvDailyTotalLabel)
        tvDailyTotalCals = view.findViewById(R.id.tvDailyTotalCals)
        tvProteinLabel = view.findViewById(R.id.tvProteinLabel)
        tvProteinValue = view.findViewById(R.id.tvProteinValue)
        tvCarbsLabel = view.findViewById(R.id.tvCarbsLabel)
        tvCarbsValue = view.findViewById(R.id.tvCarbsValue)
        tvFatsLabel = view.findViewById(R.id.tvFatsLabel)
        tvFatsValue = view.findViewById(R.id.tvFatsValue)

        tvMealPlansTitle.text = "Meal Plans"
        tvMealPlansSubtitle.text = "Your weekly nutrition plan"
        btnGenerate.text = "Generate Plan"
        btnExportPlan.text = "Export Plan"
        tvDailyTotalLabel.text = "Daily Total"
        tvProteinLabel.text = "PROTEIN"
        tvCarbsLabel.text = "CARBS"
        tvFatsLabel.text = "FATS"

        listView = view.findViewById(R.id.lvMealPlan)
        presenter = MealPlanPresenter()
        presenter.attachView(this)

        // REQUIREMENT: Custom ListView Click Listener
        listView.setOnItemClickListener { _, _, position, _ ->
            presenter.toggleMealSelection(position)
        }

        // REQUIREMENT: Custom ListView Long Click Listener
        listView.setOnItemLongClickListener { _, _, position, _ ->
            presenter.deleteMeal(position)
            true
        }

        presenter.loadMeals()
        return view
    }

    override fun displayMeals(meals: ArrayList<MealPlan>) {
        listView.adapter = MealAdapter(requireContext(), meals)
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showDailyTotals(cals: Int, protein: Int, carbs: Int, fats: Int) {
        tvDailyTotalCals.text = "$cals cal"
        tvProteinValue.text = "${protein}g"
        tvCarbsValue.text = "${carbs}g"
        tvFatsValue.text = "${fats}g"
    }
}