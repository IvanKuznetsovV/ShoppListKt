package com.ivankuznetsov.shopplistkt.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.icu.util.TimeUnit.values
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.ivankuznetsov.shopplistkt.R
import com.ivankuznetsov.shopplistkt.databinding.ActivityMainBinding
import com.ivankuznetsov.shopplistkt.fragment.BinFragment
import com.ivankuznetsov.shopplistkt.fragment.SettingsFragment
import com.ivankuznetsov.shopplistkt.fragment.ProductListFragment
import java.time.chrono.JapaneseEra.values

class MainActivity : AppCompatActivity()  {

            private lateinit var binding: ActivityMainBinding
            private lateinit var pref : SharedPreferences
            private lateinit var editor: SharedPreferences.Editor


        @SuppressLint("ResourceAsColor", "CommitPrefEdits")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            }

            val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
            toolbar.setTitleTextAppearance(this, R.style.ToolBarTheme)

            pref = getSharedPreferences("AppPref", MODE_PRIVATE)
            editor = pref.edit()

            if(pref.getInt("themeID",0) != pref.getInt("loadID", 0)) {
                createSettingsFragment()
            }
            else
            createProductListFragment()

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home  -> {
                    createProductListFragment()
                }
                R.id.bin  -> {
                    createBinFragment()
                }
                R.id.settings  -> {
                    createSettingsFragment()

                }
            }
            true
        }

     }

    @SuppressLint("CommitPrefEdits")
    override fun onApplyThemeResource(theme: Resources.Theme?, resid: Int, first: Boolean) {
        super.onApplyThemeResource(theme, resid, first)
        pref = getSharedPreferences("AppPref", MODE_PRIVATE)
        var id = pref.getInt("themeID", 0)

        when(id) {
            0 -> {
                setTheme(R.style.Theme_ShoppListKt)
            }
            1 -> {
                setTheme(R.style.Theme_ShoppListKtDark)
            }
        }

    }

    private fun createProductListFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, ProductListFragment())
            .commit()
    }

    private fun createBinFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, BinFragment())
            .commit()
    }
    private fun createSettingsFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, SettingsFragment())
            .commit()
    }
}




