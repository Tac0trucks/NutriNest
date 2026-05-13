package com.example.nutrinest.screens.profile

import com.example.nutrinest.data.models.Restriction

class ProfilePresenter : ProfileContract.Presenter {
    private var view: ProfileContract.View? = null

    // REQUIREMENT: ArrayList implementation
    private val restrictionList = ArrayList<Restriction>()

    override fun attachView(view: ProfileContract.View) {
        this.view = view
    }

    override fun initData() {
        val user = com.example.nutrinest.data.repositories.UserRepository.currentUser
        val name = user?.fullName ?: "User"
        val email = user?.email ?: "user@example.com"
        view?.showUserInfo(name, email)

        // Initial items from Figma
        restrictionList.add(Restriction(1, "Vegetarian"))
        restrictionList.add(Restriction(2, "Vegan"))
        restrictionList.add(Restriction(3, "Gluten-Free", true))
        view?.displayRestrictions(restrictionList)
    }

    override fun addNewRestriction(name: String) {
        val newItem = Restriction(restrictionList.size + 1, name)
        restrictionList.add(newItem) // CONCEPT: Add Item
        view?.displayRestrictions(restrictionList)
    }

    override fun deleteRestriction(position: Int) {
        val removed = restrictionList.removeAt(position) // CONCEPT: Remove Item
        view?.displayRestrictions(restrictionList)
        view?.showStatus("Removed: ${removed.name}")
    }

    override fun toggleRestriction(position: Int) {
        restrictionList[position].isChecked = !restrictionList[position].isChecked
        view?.displayRestrictions(restrictionList)
    }
}