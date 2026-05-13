package com.example.nutrinest.screens.registration.health

class HealthInfoPresenter : HealthInfoContract.Presenter {
    private var view: HealthInfoContract.View? = null

    override fun attachView(view: HealthInfoContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun handleNext(ageStr: String, weightStr: String, heightStr: String, restrictions: List<String>, allergies: String) {
        val age = ageStr.toIntOrNull()
        val weight = weightStr.toDoubleOrNull()
        val height = heightStr.toIntOrNull()

        when {
            age == null || weight == null || height == null -> {
                view?.showValidationError("Please enter valid numbers for age, weight, and height")
            }
            age < 10 || age > 100 -> view?.showValidationError("Please enter a valid age")
            else -> {
                view?.navigateToGoals(age, weight, height, restrictions, allergies)
            }
        }
    }

    override fun handleBack() {
        view?.navigateBack()
    }
}