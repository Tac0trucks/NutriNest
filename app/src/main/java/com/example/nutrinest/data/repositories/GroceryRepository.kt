package com.example.nutrinest.data.repositories

import com.example.nutrinest.data.models.GroceryItem

object GroceryRepository {
    var fullList = ArrayList<GroceryItem>().apply {
        add(GroceryItem(1, "Spinach", "2 bunches"))
        add(GroceryItem(2, "Chicken Breast", "2 lbs"))
    }
}
