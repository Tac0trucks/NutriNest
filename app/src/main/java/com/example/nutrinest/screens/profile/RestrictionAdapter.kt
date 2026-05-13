package com.example.nutrinest.screens.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.nutrinest.R
import com.example.nutrinest.data.models.Restriction

class RestrictionAdapter(private val context: Context, private val list: ArrayList<Restriction>) : BaseAdapter() {

    override fun getCount(): Int = list.size
    override fun getItem(position: Int): Any = list[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Inflate the custom row layout
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_restriction, parent, false)

        val item = list[position]

        val tvName = view.findViewById<TextView>(R.id.tvRestrictionName)
        val cbDone = view.findViewById<CheckBox>(R.id.cbRestriction)

        tvName.text = item.name
        cbDone.isChecked = item.isChecked

        // Important: Disable checkbox clicks so the ListView's onItemClickListener works
        cbDone.isFocusable = false
        cbDone.isClickable = false

        return view
    }
}