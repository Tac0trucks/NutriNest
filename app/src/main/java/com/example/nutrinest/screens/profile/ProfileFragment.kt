package com.example.nutrinest.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.nutrinest.R
import com.example.nutrinest.data.models.Restriction

class ProfileFragment : Fragment(), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter
    private lateinit var listView: ListView
    private lateinit var tvProfileTitle: TextView
    private lateinit var tvProfileSubtitle: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var tvPersonalInfo: TextView
    private lateinit var tvFullNameLabel: TextView
    private lateinit var tvEmailLabel: TextView
    private lateinit var tvDietaryRestrictionsTitle: TextView
    private lateinit var btnAddCustom: Button
    private lateinit var btnCancel: Button
    private lateinit var btnSaveChanges: Button
    private lateinit var ivProfilePicture: ImageView
    private lateinit var fabCamera: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvProfileTitle = view.findViewById(R.id.tvProfileTitle)
        tvProfileSubtitle = view.findViewById(R.id.tvProfileSubtitle)
        tvUserName = view.findViewById(R.id.tvUserName)
        tvUserEmail = view.findViewById(R.id.tvUserEmail)
        tvPersonalInfo = view.findViewById(R.id.tvPersonalInfo)
        tvFullNameLabel = view.findViewById(R.id.tvFullNameLabel)
        tvEmailLabel = view.findViewById(R.id.tvEmailLabel)
        tvDietaryRestrictionsTitle = view.findViewById(R.id.tvDietaryRestrictionsTitle)
        btnAddCustom = view.findViewById(R.id.btnAddCustom)
        btnCancel = view.findViewById(R.id.btnCancel)
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges)
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture)
        fabCamera = view.findViewById(R.id.fabCamera)

        tvProfileTitle.text = "Profile"
        tvProfileSubtitle.text = "Manage your account"
        tvPersonalInfo.text = "Personal Information"
        tvFullNameLabel.text = "Full Name"
        tvEmailLabel.text = "Email Address"
        tvDietaryRestrictionsTitle.text = "Dietary Restrictions"
        btnAddCustom.text = "+ Add New Restriction"
        btnCancel.text = "Cancel"
        btnSaveChanges.text = "Save Changes"

        ivProfilePicture.setImageResource(R.drawable.ic_profile_placeholder)
        // fabCamera icon is set via src, but wait, FloatingActionButton in fragment_profile.xml had its src removed. We should set it programmatically if we want, or just leave it. I'll set it here:
        if (fabCamera is ImageView) {
            (fabCamera as ImageView).setImageResource(R.drawable.ic_camera)
        }

        listView = view.findViewById(R.id.lvRestrictions)
        presenter = ProfilePresenter()
        presenter.attachView(this)

        listView.setOnItemClickListener { _, _, position, _ ->
            presenter.toggleRestriction(position)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            presenter.deleteRestriction(position)
            true
        }

        btnAddCustom.setOnClickListener {
            presenter.addNewRestriction("Low Carb")
        }

        btnSaveChanges.setOnClickListener {
            val fullName = view.findViewById<EditText>(R.id.etFullName).text.toString()
            val email = view.findViewById<EditText>(R.id.etEmail).text.toString()
            
            // Extract the list from the adapter
            val listAdapter = listView.adapter as RestrictionAdapter
            val restrictions = ArrayList<Restriction>()
            for (i in 0 until listAdapter.count) {
                restrictions.add(listAdapter.getItem(i) as Restriction)
            }
            
            presenter.saveUserData(fullName, email, restrictions)
        }

        view.findViewById<ImageView>(R.id.ivSettings)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, com.example.nutrinest.screens.settings.SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initData()
    }

    override fun displayRestrictions(list: ArrayList<Restriction>) {
        listView.adapter = RestrictionAdapter(requireContext(), list)
        setListViewHeightBasedOnChildren(listView)
    }

    override fun showStatus(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showUserInfo(name: String, email: String) {
        tvUserName.text = name
        tvUserEmail.text = email
        // Populate fields based on User Info (using requireView() or the existing references)
        val view = requireView()
        view.findViewById<EditText>(R.id.etFullName)?.setText(name)
        view.findViewById<EditText>(R.id.etEmail)?.setText(email)
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        var totalHeight = 0
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count - 1))
        listView.layoutParams = params
        listView.requestLayout()
    }
}