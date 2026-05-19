package com.example.nutrinest.screens.profile

import com.example.nutrinest.data.models.Restriction

interface ProfileContract {
    interface View {
        fun displayRestrictions(list: ArrayList<Restriction>)
        fun showStatus(msg: String)
        fun showUserInfo(name: String, email: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun initData()
        fun addNewRestriction(name: String) // CONCEPT: Add Item
        fun deleteRestriction(position: Int) // CONCEPT: Remove Item
        fun toggleRestriction(position: Int) // CONCEPT: Click Listener logic
        fun saveUserData(fullName: String, email: String, restrictions: List<Restriction>)
    }
}