package com.example.nutrinest.screens.grocery

import com.example.nutrinest.data.models.GroceryItem

class GroceryPresenter : GroceryContract.Presenter {
    private var view: GroceryContract.View? = null
    private val fullList = ArrayList<GroceryItem>()
    private var displayedList = ArrayList<GroceryItem>()

    override fun attachView(view: GroceryContract.View) {
        this.view = view
    }

    override fun loadGroceries() {
        // Initializing with Figma data
        fullList.clear()
        fullList.add(GroceryItem(1, "Spinach", "2 bunches"))
        fullList.add(GroceryItem(2, "Chicken Breast", "2 lbs"))
        displayedList = ArrayList(fullList)
        view?.displayItems(displayedList)
    }

    override fun addNewItem(name: String, qty: String) {
        val newItem = GroceryItem(fullList.size + 1, name, qty)
        fullList.add(newItem) // CONCEPT: Add Item
        displayedList.add(newItem)
        view?.displayItems(displayedList)
    }

    override fun removeItem(position: Int) {
        val item = displayedList.removeAt(position) // CONCEPT: Remove Item
        fullList.remove(item)
        view?.displayItems(displayedList)
    }

    override fun toggleCheck(position: Int) {
        displayedList[position].isChecked = !displayedList[position].isChecked
        view?.displayItems(displayedList)
    }

    override fun filterItems(query: String) {
        val queryLower = query.lowercase()
        displayedList = if (queryLower.isEmpty()) {
            ArrayList(fullList)
        } else {
            ArrayList(fullList.filter { it.name.lowercase().contains(queryLower) })
        }
        view?.displayItems(displayedList)
    }
}