package com.example.nutrinest.screens.registration

class RegisterPresenter : RegisterContract.Presenter {
    private var view: RegisterContract.View? = null

    override fun attachView(view: RegisterContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun handleNext(name: String, email: String, pass: String, confirmPass: String) {
        when {
            name.isEmpty() || email.isEmpty() || pass.isEmpty() -> {
                view?.showValidationError("All fields are required")
            }
            pass != confirmPass -> {
                view?.showValidationError("Passwords do not match")
            }
            pass.length < 6 -> {
                view?.showValidationError("Password must be at least 6 characters")
            }
            else -> {
                // Logic is valid, move to Step 2
                view?.navigateToHealthInfo(name, email, pass)
            }
        }
    }

    override fun handleLoginClick() {
        view?.navigateToLogin()
    }
}