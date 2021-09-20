package com.example.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view.OfflineFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline)
        replaceView(OfflineFragment.newInstance(), R.id.withoutInternetContainer)
    }
}