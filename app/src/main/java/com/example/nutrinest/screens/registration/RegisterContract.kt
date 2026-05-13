package com.example.nutrinest.screens.registration

interface RegisterContract {
    interface View {
        fun showValidationError(message: String)
        fun navigateToHealthInfo(name: String, email: String, pass: String)
        fun navigateToLogin()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun handleNext(name: String, email: String, pass: String, confirmPass: String)
        fun handleLoginClick()
    }
}