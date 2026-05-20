package com.example.nutrinest.data.repositories

import com.example.nutrinest.data.models.User

class UserRepository {
    companion object {
        // This stores the currently logged-in user for the whole app
        var currentUser: User? = null
        
        // Simulates a database of registered users (email to password)
        val registeredAccounts = mutableMapOf(
            "test@test.com" to "password123"
        )
    }

    fun login(email: String, pass: String, callback: (Result<User>) -> Unit) {
        // Validate credentials against existing accounts
        if (registeredAccounts.containsKey(email) && registeredAccounts[email] == pass) {
            val name = currentUser?.fullName ?: email.substringBefore("@").replaceFirstChar { it.uppercase() }
            val mockUser = User("1", email, name, "token")
            UserRepository.currentUser = mockUser
            callback(Result.success(mockUser))
        } else {
            callback(Result.failure(Exception("Invalid email or password")))
        }
    }

    fun setCurrentUser(user: User) {
        // Explicitly refer to the companion object variable
        UserRepository.currentUser = user
    }
}