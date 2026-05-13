package com.example.nutrinest.app

import android.app.Application
import com.example.nutrinest.data.repositories.UserRepository

class NutriNestApp : Application() {

    // Global instance of Repository to be shared across all Presenters
    // This follows the "Dependency Injection" pattern
    val userRepository: UserRepository by lazy {
        UserRepository()
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase or Logging libraries here
    }
}