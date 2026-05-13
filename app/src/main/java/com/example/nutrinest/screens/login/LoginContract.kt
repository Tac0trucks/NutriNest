package com.example.nutrinest.screens.login

import com.example.nutrinest.data.models.User

interface LoginContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun onLoginSuccess(user: User)
        fun navigateToRegister()
        fun navigateToForgotPassword()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun handleLogin(email: String, pass: String)
        fun handleGoogleSignIn()
        fun handleSignUpClick()
    }
}