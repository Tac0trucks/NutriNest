package com.example.nutrinest.data.repositories

import com.example.nutrinest.data.models.User

class UserRepository {
    companion object {
        // This stores the currently logged-in user for the whole app
        var currentUser: User? = null
    }

    fun login(email: String, pass: String, callback: (Result<User>) -> Unit) {
        // Mock login: try to extract a name from email or use existing
        val name = currentUser?.fullName ?: email.substringBefore("@").replaceFirstChar { it.uppercase() }
        val mockUser = User("1", email, name, "token")

        // Explicitly refer to the companion object variable
        UserRepository.currentUser = mockUser

        callback(Result.success(mockUser))
    }

    fun setCurrentUser(user: User) {
        // Explicitly refer to the companion object variable
        UserRepository.currentUser = user
    }
}