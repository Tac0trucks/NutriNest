package com.example.nutrinest.screens.grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nutrinest.R
import com.example.nutrinest.data.models.GroceryItem

class GroceryFragment : Fragment(), GroceryContract.View {
    private lateinit var presenter: GroceryPresenter
    private lateinit var adapter: GroceryAdapter
    private lateinit var listView: ListView
    private lateinit var tvGroceryTitle: TextView
    private lateinit var tvGrocerySubtitle: TextView
    private lateinit var btnAddItem: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grocery, container, false)

        tvGroceryTitle = view.findViewById(R.id.tvGroceryTitle)
        tvGrocerySubtitle = view.findViewById(R.id.tvGrocerySubtitle)
        btnAddItem = view.findViewById(R.id.btnAddItem)

        tvGroceryTitle.text = "Grocery List"
        tvGrocerySubtitle.text = "Your weekly shopping list"
        btnAddItem.text = "+ Add New Item"

        listView = view.findViewById(R.id.lvGrocery)
        presenter = GroceryPresenter()
        presenter.attachView(this)

        val etSearch = view.findViewById<android.widget.EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.filterItems(s.toString())
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        // CONCEPT: Click Listener
        listView.setOnItemClickListener { _, _, position, _ ->
            presenter.toggleCheck(position)
        }

        // CONCEPT: Long Click Listener
        listView.setOnItemLongClickListener { _, _, position, _ ->
            presenter.removeItem(position)
            true
        }

        btnAddItem.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(requireContext())
            builder.setTitle("Add New Item")

            val layout = android.widget.LinearLayout(requireContext())
            layout.orientation = android.widget.LinearLayout.VERTICAL
            layout.setPadding(50, 40, 50, 10)

            val nameInput = android.widget.EditText(requireContext())
            nameInput.hint = "Item Name"
            layout.addView(nameInput)

            val qtyInput = android.widget.EditText(requireContext())
            qtyInput.hint = "Quantity (e.g. 2 lbs)"
            layout.addView(qtyInput)

            builder.setView(layout)

            builder.setPositiveButton("Add") { dialog, _ ->
                val name = nameInput.text.toString()
                val qty = qtyInput.text.toString()
                if (name.isNotBlank()) {
                    presenter.addNewItem(name, qty)
                } else {
                    Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            builder.show()
        }

        presenter.loadGroceries()
        return view
    }

    override fun displayItems(items: ArrayList<GroceryItem>) {
        adapter = GroceryAdapter(requireContext(), items)
        listView.adapter = adapter
    }

    override fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}