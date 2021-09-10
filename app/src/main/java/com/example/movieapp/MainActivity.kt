package com.example.movieapp

import android.content.Intent

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.example.movieapp.utils.replaceView



import com.example.movieapp.view.LoginFragment
import com.example.movieapp.view.Main2Activity

import com.google.firebase.auth.FirebaseAuth



import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity2()
            finish()
        } else {
            replaceView( LoginFragment.newInstance(), R.id.container)
        }

    }

    private fun startActivity2(){
        Intent(this, Main2Activity::class.java).apply {
            startActivity(this)
        }

    }
}