package com.example.nutrinest.screens.mealplan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.nutrinest.R
import com.example.nutrinest.data.models.MealPlan

class MealAdapter(private val context: Context, private var list: ArrayList<MealPlan>) : BaseAdapter() {

    override fun getCount(): Int = list.size
    override fun getItem(p: Int) = list[p]
    override fun getItemId(p: Int) = p.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Inflating Custom View row
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_meal_card, parent, false)
        val meal = list[position]

        // Binding complex data (Custom ListView Concept)
        val tvMealTitle = view.findViewById<TextView>(R.id.tvMealTitle)
        tvMealTitle.text = meal.title
        
        view.findViewById<TextView>(R.id.tvMealTimeType).text = meal.timeAndType
        view.findViewById<TextView>(R.id.tvMealDescription).text = meal.description
        view.findViewById<TextView>(R.id.tvMealMacros).text = "P: ${meal.protein}  C: ${meal.carbs}"
        val cbMealDone = view.findViewById<CheckBox>(R.id.cbMealDone)
        cbMealDone.setOnCheckedChangeListener(null)
        cbMealDone.isChecked = meal.isChecked

        cbMealDone.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cbMealDone.isEnabled = false
                
                // Explicitly delete from global repository BEFORE animating
                com.example.nutrinest.data.repositories.MealRepository.currentMeals.remove(meal)
                
                view.animate().alpha(0f).setDuration(300).withEndAction {
                    list.remove(meal)
                    notifyDataSetChanged()
                    view.alpha = 1.0f
                    cbMealDone.isEnabled = true
                }.start()
            }
        }

        return view
    }
}