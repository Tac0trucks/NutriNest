package com.example.nutrinest.screens.login

import com.example.nutrinest.data.models.User
import com.example.nutrinest.data.repositories.UserRepository

class LoginPresenter(private val repository: UserRepository) : LoginContract.Presenter {

    private var view: LoginContract.View? = null

    override fun attachView(view: LoginContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun handleLogin(email: String, pass: String) {
        if (email.isEmpty() || pass.isEmpty()) {
            view?.showError("Please enter email and password")
            return
        }

        view?.showLoading()

        repository.login(email, pass) { result: Result<User> -> // Added explicit type
            view?.hideLoading()
            result.onSuccess { user ->
                view?.onLoginSuccess(user)
            }.onFailure { error ->
                view?.showError(error.message ?: "Invalid email or password")
            }
        }
    }

    override fun handleSignUpClick() {
        view?.navigateToRegister()
    }

    override fun handleGoogleSignIn() {}
}