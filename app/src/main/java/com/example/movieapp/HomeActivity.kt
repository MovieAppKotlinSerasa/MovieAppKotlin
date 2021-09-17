package com.example.movieapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.movieapp.databinding.ActivityHomeBinding
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view.MovieFragment
import com.example.movieapp.view.SearchFragment
import com.example.movieapp.view.ui.slideshow.SlideshowFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawNavigationSetup()
        bottomNavigationSetup()
    }

    private fun bottomNavigationSetup() {
        binding.bottomNavigation.setOnItemSelectedListener {

            when(it.itemId) {

                R.id.bottom_nav_home -> replaceView(MovieFragment.newInstance(), R.id.nav_host_fragment_home_container)

//                R.id.bottom_nav_search -> startSettingsActivity()

                R.id.bottom_nav_search -> replaceView(SearchFragment.newInstance(), R.id.nav_host_fragment_home_container)

                R.id.bottom_nav_favorites -> replaceView(MovieFragment.newInstance(), R.id.nav_host_fragment_home_container)

            }
            true
        }
    }

    private fun startSettingsActivity(){
        Intent(this, SettingsActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun drawNavigationSetup() {

        setSupportActionBar(binding.appToolbar)
        supportActionBar?.title = "Movie App"

        drawerLayout = binding.drawerLayout
        navView = binding.navView

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceView(MovieFragment.newInstance(), R.id.nav_host_fragment_home_container)


        navView.setNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.drawer_nav_home -> replaceView(MovieFragment.newInstance(), R.id.nav_host_fragment_home_container)
                R.id.drawer_nav_settings -> startSettingsActivity()
                R.id.drawer_nav_slideshow -> replaceView(SlideshowFragment(), R.id.nav_host_fragment_home_container)
                R.id.drawer_nav_signout -> signOut()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {

        getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()

        AuthenticationRepository().signOut()
        finish()

    }

}