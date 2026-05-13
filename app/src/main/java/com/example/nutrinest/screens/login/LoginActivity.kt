package com.example.nutrinest.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrinest.data.models.User
import com.example.nutrinest.data.repositories.UserRepository
import com.example.nutrinest.databinding.ActivityLoginBinding // Make sure this matches project package
import com.example.nutrinest.screens.registration.RegisterActivity


class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(UserRepository())
        presenter.attachView(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            presenter.handleLogin(email, pass)
        }

        binding.tvSignUp.setOnClickListener {
            presenter.handleSignUpClick()
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.INVISIBLE // Hide button text while loading
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnLogin.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoginSuccess(user: com.example.nutrinest.data.models.User) {
        val name = UserRepository.currentUser?.fullName ?: ""
        Toast.makeText(this, "Welcome back, $name!", Toast.LENGTH_SHORT).show()

        // START THE MAIN HUB (The dashboard with bottom navigation)
        val intent = Intent(this, com.example.nutrinest.screens.main.MainActivity::class.java)

        // Clear the login screen from the back button history
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToForgotPassword() {}

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}