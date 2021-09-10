package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.movieapp.view.MainFragment
import com.example.movieapp.view.MovieFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MovieFragment.newInstance())
            .commitNow()

    }
}