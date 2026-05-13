package com.example.nutrinest.screens.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nutrinest.R
import com.example.nutrinest.databinding.ActivityMainBinding
import com.example.nutrinest.screens.dashboard.DashboardFragment
import com.example.nutrinest.screens.grocery.GroceryFragment
import com.example.nutrinest.screens.mealplan.MealPlanFragment
import com.example.nutrinest.screens.profile.ProfileFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
        }

        binding.bottomNavigation.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var fragment: Fragment? = null
                when (item.itemId) {
                    R.id.nav_home -> fragment = DashboardFragment()
                    R.id.nav_meals -> fragment = MealPlanFragment()
                    R.id.nav_grocery -> fragment = GroceryFragment()
                    R.id.nav_profile -> fragment = ProfileFragment()
                }
                
                if (fragment != null) {
                    loadFragment(fragment)
                    return true
                }
                return false
            }
        })
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}