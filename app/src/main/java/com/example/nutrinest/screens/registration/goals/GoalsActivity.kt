package com.example.nutrinest.screens.registration.goals

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrinest.data.repositories.UserRepository
import com.example.nutrinest.databinding.ActivityGoalsBinding

class GoalsActivity : AppCompatActivity(), GoalsContract.View {

    private lateinit var binding: ActivityGoalsBinding
    private lateinit var presenter: GoalsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = GoalsPresenter()
        presenter.attachView(this)

        binding.btnFinish.setOnClickListener {
            // Passing the IDs of the selected RadioButtons to the presenter
            presenter.handleFinish(
                binding.rgGoals.checkedRadioButtonId,
                binding.rgActivity.checkedRadioButtonId
            )
        }

        binding.btnBack.setOnClickListener {
            presenter.handleBack()
        }
    }

    override fun showValidationError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRegistrationFinished() {

        val name = intent.getStringExtra("EXTRA_NAME") ?: "User"
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: ""


        val newUser = com.example.nutrinest.data.models.User("1", email, name, "fake-token")
        UserRepository.currentUser = newUser

        Toast.makeText(this, "Welcome to NutriNest, $name!", Toast.LENGTH_LONG).show()


        val mainIntent = Intent(this, com.example.nutrinest.screens.main.MainActivity::class.java)

        // Clear all registration screens so 'Back' doesn't go back to the forms
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(mainIntent)
    }

    override fun navigateBack() {
        finish() // Goes back to HealthInfoActivity
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}