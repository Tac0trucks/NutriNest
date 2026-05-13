
package com.example.nutrinest.screens.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrinest.databinding.ActivityRegistrationBinding
import com.example.nutrinest.screens.registration.health.HealthInfoActivity

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = RegisterPresenter()
        presenter.attachView(this)

        binding.btnNext.setOnClickListener {
            presenter.handleNext(
                binding.etFullName.text.toString().trim(),
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString(),
                binding.etConfirmPassword.text.toString()
            )
        }

        binding.tvBackToLogin.setOnClickListener {
            presenter.handleLoginClick()
        }
    }

    override fun showValidationError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToHealthInfo(name: String, email: String, pass: String) {
        val intent = Intent(this, HealthInfoActivity::class.java)
        intent.putExtra("EXTRA_NAME", name)
        intent.putExtra("EXTRA_EMAIL", email)
        intent.putExtra("EXTRA_PASS", pass) // Passing pass forward
        startActivity(intent)
    }

    override fun navigateToLogin() {
        finish() // Goes back to LoginActivity
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}