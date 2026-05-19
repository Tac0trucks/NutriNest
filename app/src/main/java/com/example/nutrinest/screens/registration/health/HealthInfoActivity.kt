package com.example.nutrinest.screens.registration.health

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrinest.databinding.ActivityHealthInfoBinding
import com.example.nutrinest.screens.registration.goals.GoalsActivity

class HealthInfoActivity : AppCompatActivity(), HealthInfoContract.View {

    private lateinit var binding: ActivityHealthInfoBinding
    private lateinit var presenter: HealthInfoContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = HealthInfoPresenter()
        presenter.attachView(this)

        binding.btnNext.setOnClickListener {
            val restrictions = mutableListOf<String>()
            if (binding.cbVegan.isChecked) restrictions.add("Vegan")
            if (binding.cbVegetarian.isChecked) restrictions.add("Vegetarian")
            if (binding.cbGlutenFree.isChecked) restrictions.add("Gluten-free")
            if (binding.cbSpicyFree.isChecked) restrictions.add("Spicy-free")
            if (binding.cbDiabetic.isChecked) restrictions.add("Diabetic")

            presenter.handleNext(
                binding.etAge.text.toString(),
                binding.etWeight.text.toString(),
                binding.etHeight.text.toString(),
                restrictions,
                binding.etAllergies.text.toString()
            )
        }

        binding.btnBack.setOnClickListener {
            presenter.handleBack()
        }
    }

    override fun showValidationError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToGoals(age: Int, weight: Double, height: Int, restrictions: List<String>, allergies: String) {

        val nextIntent = Intent(this, com.example.nutrinest.screens.registration.goals.GoalsActivity::class.java)


        nextIntent.putExtra("EXTRA_NAME", intent.getStringExtra("EXTRA_NAME"))
        nextIntent.putExtra("EXTRA_EMAIL", intent.getStringExtra("EXTRA_EMAIL"))
        nextIntent.putExtra("EXTRA_PASS", intent.getStringExtra("EXTRA_PASS"))
        nextIntent.putStringArrayListExtra("EXTRA_RESTRICTIONS", ArrayList(restrictions))

        startActivity(nextIntent)
    }

    override fun navigateBack() {
        finish()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}