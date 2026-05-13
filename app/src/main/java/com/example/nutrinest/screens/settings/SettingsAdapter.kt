package com.example.nutrinest.screens.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.nutrinest.R
import com.example.nutrinest.data.models.SettingOption

class SettingsAdapter(private val context: Context, private val list: ArrayList<SettingOption>) : BaseAdapter() {

    override fun getCount() = list.size
    override fun getItem(p: Int) = list[p]
    override fun getItemId(p: Int) = p.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_setting_toggle, parent, false)
        val item = list[position]

        val tvTitle = view.findViewById<TextView>(R.id.tvSettingTitle)
        val tvSub = view.findViewById<TextView>(R.id.tvSettingSub)
        val sw = view.findViewById<SwitchCompat>(R.id.swSetting)

        tvTitle.text = item.title
        tvSub.text = item.subtitle
        sw.isChecked = item.isEnabled

        // Disable switch click so ListView handles it
        sw.isClickable = false

        return view
    }
}