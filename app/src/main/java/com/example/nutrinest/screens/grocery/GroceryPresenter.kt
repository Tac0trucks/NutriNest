package com.example.nutrinest.screens.grocery

import com.example.nutrinest.data.models.GroceryItem
import com.example.nutrinest.data.repositories.GroceryRepository

class GroceryPresenter : GroceryContract.Presenter {
    private var view: GroceryContract.View? = null
    private var displayedList = ArrayList<GroceryItem>()

    override fun attachView(view: GroceryContract.View) {
        this.view = view
    }

    override fun loadGroceries() {
        displayedList = ArrayList(GroceryRepository.fullList)
        view?.displayItems(displayedList)
    }

    override fun addNewItem(name: String, qty: String) {
        val newItem = GroceryItem(GroceryRepository.fullList.size + 1, name, qty)
        GroceryRepository.fullList.add(newItem) // CONCEPT: Add Item
        displayedList.add(newItem)
        view?.displayItems(displayedList)
    }

    override fun removeItem(position: Int) {
        val item = displayedList.removeAt(position) // CONCEPT: Remove Item
        GroceryRepository.fullList.remove(item)
        view?.displayItems(displayedList)
    }

    override fun toggleCheck(position: Int) {
        displayedList[position].isChecked = !displayedList[position].isChecked
        view?.displayItems(displayedList)
    }

    override fun filterItems(query: String) {
        val queryLower = query.lowercase()
        displayedList = if (queryLower.isEmpty()) {
            ArrayList(GroceryRepository.fullList)
        } else {
            ArrayList(GroceryRepository.fullList.filter { it.name.lowercase().contains(queryLower) })
        }
        view?.displayItems(displayedList)
    }
}