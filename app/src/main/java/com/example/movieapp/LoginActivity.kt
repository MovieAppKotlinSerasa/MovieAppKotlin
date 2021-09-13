package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        if (FirebaseAuth.getInstance().currentUser != null) {
            startHomeActivity()
        } else {
            replaceView(LoginFragment.newInstance(), R.id.container)
        }

    }

    override fun onResume() {
        super.onResume()
        if (FirebaseAuth.getInstance().currentUser == null) {
            replaceView(LoginFragment.newInstance(), R.id.container)
        }
    }

    private fun startHomeActivity(){
        startActivity(Intent(this, HomeActivity::class.java))
    }
}