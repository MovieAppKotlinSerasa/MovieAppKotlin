package com.example.movieapp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.movieapp.databinding.ActivitySplashBinding
import com.example.movieapp.utils.checkInternet
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val internetConnection = checkInternet()

        setPreferenceTheme()

        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
            chooseActivityToStart(internetConnection)
            finish()
        }
    }

    private fun chooseActivityToStart(internetConnection: Boolean) {

        getSharedPreferences("app_preferences", Context.MODE_PRIVATE).apply {

            if (auth.currentUser != null && getBoolean("saved_Settings_SalvarSessao", false)) {
                if (internetConnection) {
                    startActivity(HomeActivity())
                } else {
                    startActivity(OfflineActivity())
                }
            } else {
                startActivity(LoginActivity())
            }
        }
    }

    private fun startActivity(activity: Activity) {
        startActivity(Intent(this, activity::class.java))
    }

    private fun setPreferenceTheme() {
        getSharedPreferences("app_preferences", Context.MODE_PRIVATE).apply {
            if (getBoolean("saved_Settings_ModoEscuro", false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
        }
    }

}