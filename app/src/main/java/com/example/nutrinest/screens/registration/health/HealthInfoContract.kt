package com.example.nutrinest.screens.registration.health

interface HealthInfoContract {
    interface View {
        fun showValidationError(message: String)
        fun navigateToGoals(age: Int, weight: Double, height: Int, restrictions: List<String>, allergies: String)
        fun navigateBack()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun handleNext(ageStr: String, weightStr: String, heightStr: String, restrictions: List<String>, allergies: String)
        fun handleBack()
    }
}