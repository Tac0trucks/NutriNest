package com.example.nutrinest.screens.registration.goals

interface GoalsContract {
    interface View {
        fun showValidationError(message: String)
        fun onRegistrationFinished()
        fun navigateBack()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun handleFinish(selectedGoalId: Int, selectedActivityId: Int)
        fun handleBack()
    }
}