package com.example.nutrinest.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.nutrinest.R
import com.example.nutrinest.data.models.SettingOption
import com.example.nutrinest.databinding.FragmentSettingsBinding // renamed

class SettingsFragment : Fragment(), SettingsContract.View {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: SettingsPresenter
    private lateinit var adapter: SettingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingsPresenter()
        presenter.attachView(this)

        setupListeners()

        // Initializing the list with empty data
        adapter = SettingsAdapter(requireContext(), ArrayList())
        binding.lvNotifications.adapter = adapter

        // Load the initial data from Presenter
        presenter.loadSettings()
    }

    private fun setupListeners() {
        // --- 1. ListView Listeners (The Assignment Concept) ---
        binding.lvNotifications.setOnItemClickListener { _, _, position, _ ->
            presenter.toggleSetting(position)
        }


        // --- 3. Action Buttons ---
        binding.btnExport.setOnClickListener {
            Toast.makeText(context, "Preparing your data for export...", Toast.LENGTH_SHORT).show()
        }

        binding.btnDelete.setOnClickListener {
            // Logic to show a confirmation dialog would go here
            Toast.makeText(context, "Account deletion requires confirmation", Toast.LENGTH_LONG).show()
        }

        binding.btnLogout.setOnClickListener {
            presenter.handleLogout()
        }

        // The calorie logic has been moved to MealPlanFragment
    }

    // --- View Interface Implementation ---

    override fun displayNotificationSettings(list: ArrayList<SettingOption>) {
        adapter = SettingsAdapter(requireContext(), list)
        binding.lvNotifications.adapter = adapter

        // Use the helper to fix the ListView height inside ScrollView
        setListViewHeightBasedOnChildren(binding.lvNotifications)
    }

    override fun onSettingChanged(name: String, state: Boolean) {
        val status = if (state) "Enabled" else "Disabled"
        Toast.makeText(context, "$name: $status", Toast.LENGTH_SHORT).show()

        // Refresh the list to show the switch change
        adapter.notifyDataSetChanged()
    }

    override fun navigateToLogin() {
        val intent = android.content.Intent(requireContext(), com.example.nutrinest.screens.login.LoginActivity::class.java)
        intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    // --- HELPER FUNCTION: ListView inside ScrollView Fix ---
    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        var totalHeight = 0
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.AT_MOST)

        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += listItem.measuredHeight
        }

        val params = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count - 1))
        listView.layoutParams = params
        listView.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}