package com.example.movieapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.movieapp.R

fun FragmentActivity.replaceView(fragment: Fragment, @IdRes containerId: Int = R.id.nav_host_fragment_home_container) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .commitNow()
}