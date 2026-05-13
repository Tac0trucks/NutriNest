package com.example.nutrinest.screens.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.nutrinest.R
import com.example.nutrinest.data.models.Meal

class MealAdapter(private val context: Context, private var meals: List<Meal>) : BaseAdapter() {

    override fun getCount(): Int = meals.size
    override fun getItem(position: Int): Any = meals[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_meal_plan, parent, false)
        val meal = getItem(position) as Meal

        // Find views inside the "view" object
        val tvHeader = view.findViewById<TextView>(R.id.tvMealHeader)
        val tvDesc = view.findViewById<TextView>(R.id.tvMealDesc)
        val tvCal = view.findViewById<TextView>(R.id.tvMealCalories)

        // SET text, do not assign to the variable itself
        tvHeader.text = "${meal.type} - ${if(meal.isCompleted) "Completed" else "Upcoming"}"
        tvDesc.text = meal.description
        tvCal.text = "${meal.calories} cal"

        return view
    }

    fun updateList(newList: List<Meal>) {
        this.meals = newList
        notifyDataSetChanged() // Refreshes the ListView UI
    }
}