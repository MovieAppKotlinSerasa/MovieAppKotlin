package com.example.movieapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.databinding.ActivitySettingsBinding
import com.example.movieapp.view.ui.gallery.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var _binding: ActivitySettingsBinding
    private lateinit var sharedPref : SharedPreferences

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(android.R.id.home == item.itemId) {
            this.finish()
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        supportActionBar?.setTitle("Configurações")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        sharedPref.apply {
            val sessaoPref = this.getBoolean("saved_Settings_SalvarSessao", false)
            val modoEscuroPref = this.getBoolean("saved_Settings_ModoEscuro", false)
            _binding.switchSessao.setChecked(sessaoPref)
            _binding.switchModoEscuro.setChecked(modoEscuroPref)
        }

        _binding.switchModoEscuro.setOnCheckedChangeListener { _, isChecked ->
            with (sharedPref.edit()) {
                putBoolean("saved_Settings_ModoEscuro", isChecked)
                commit()
            }
        }

        _binding.switchSessao.setOnCheckedChangeListener { _, isChecked ->
            with (sharedPref.edit()) {
                putBoolean("saved_Settings_SalvarSessao", isChecked)
                commit()
            }
        }

    }

}