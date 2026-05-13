package com.example.nutrinest.screens.grocery

import com.example.nutrinest.data.models.GroceryItem

interface GroceryContract {
    interface View {
        fun displayItems(items: ArrayList<GroceryItem>)
        fun showMessage(msg: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun loadGroceries()
        fun addNewItem(name: String, qty: String)
        fun removeItem(position: Int)
        fun toggleCheck(position: Int)
        fun filterItems(query: String)
    }
}