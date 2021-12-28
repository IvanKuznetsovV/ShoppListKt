package com.ivankuznetsov.shopplistkt.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.ivankuznetsov.shopplistkt.R
import com.ivankuznetsov.shopplistkt.adapter.SettingsSpinnerAdapter
import com.ivankuznetsov.shopplistkt.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    lateinit var binding : FragmentSettingsBinding
    val colors = mutableListOf<Int>(R.drawable.light_theme,R.drawable.dark_theme)
    private val MODE_PRIVATE = 0x0000
    lateinit var pref : SharedPreferences
    var themeID : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater)

        val adapter = activity?.let { SettingsSpinnerAdapter(it, R.layout.spinner_item, colors)}
        pref = activity?.let{it.getSharedPreferences("AppPref",MODE_PRIVATE)}!!
        themeID = pref.getInt("themeID", 0)

        binding.spinner.adapter = adapter
        themeID = pref.getInt("themeID", 0)
        binding.spinner.setSelection(themeID)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @SuppressLint("CommitPrefEdits")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val editor = pref.edit()
                editor.putInt("themeID", position)
                editor.putInt("loadID",themeID)
                editor.apply()

                Log.d("MyLog", pref.getInt("themeID",0).toString())

                if(themeID != pref.getInt("themeID",0)) activity?.let { it.recreate() }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) { TODO("Not yet implemented") }
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
}