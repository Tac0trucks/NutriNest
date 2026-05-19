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

        // Create standard options and check them if they are in the user's restrictions
        val standardRestrictions = listOf("Vegan", "Vegetarian", "Gluten-free", "Spicy-free", "Diabetic")
        val userRestrictions = user?.restrictions ?: emptyList()
        
        standardRestrictions.forEachIndexed { index, name ->
            restrictionList.add(Restriction(index + 1, name, userRestrictions.contains(name)))
        }
        
        // Add any custom restrictions the user might have added
        userRestrictions.forEach { name ->
            if (!standardRestrictions.contains(name)) {
                restrictionList.add(Restriction(restrictionList.size + 1, name, true))
            }
        }
        
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

    override fun saveUserData(fullName: String, email: String, restrictions: List<Restriction>) {
        val user = com.example.nutrinest.data.repositories.UserRepository.currentUser
        if (user != null) {
            val newFullName = fullName.trim()
            val checkedRestrictions = restrictions.filter { it.isChecked }.map { it.name }.toMutableList()
            
            // Create updated user and save to repository
            val updatedUser = user.copy(fullName = newFullName, email = email, restrictions = checkedRestrictions)
            com.example.nutrinest.data.repositories.UserRepository.currentUser = updatedUser
            
            view?.showStatus("Profile updated!")
            view?.showUserInfo(newFullName, email)
        }
    }
}