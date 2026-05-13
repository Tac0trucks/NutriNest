
package com.example.nutrinest.screens.grocery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.nutrinest.R
import com.example.nutrinest.data.models.GroceryItem

class GroceryAdapter(private val context: Context, private var items: ArrayList<GroceryItem>) : BaseAdapter() {

    override fun getCount(): Int = items.size
    override fun getItem(p: Int) = items[p]
    override fun getItemId(p: Int) = p.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // CONCEPT: Custom ListView Inflater
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_grocery, parent, false)
        val item = items[position]

        view.findViewById<TextView>(R.id.tvItemName).text = item.name
        view.findViewById<TextView>(R.id.tvItemQty).text = item.quantity
        view.findViewById<CheckBox>(R.id.cbItem).isChecked = item.isChecked

        return view
    }
}