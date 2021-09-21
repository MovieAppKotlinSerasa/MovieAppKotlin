package com.example.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        replaceView(LoginFragment.newInstance(), R.id.container)

    }

}