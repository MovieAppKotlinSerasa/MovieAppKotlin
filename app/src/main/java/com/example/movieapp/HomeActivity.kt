package com.example.movieapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.databinding.ActivityHomeBinding
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view.MovieFragment
import com.example.movieapp.view.ui.gallery.GalleryFragment
import com.example.movieapp.view.ui.slideshow.SlideshowFragment
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
                R.id.bottom_nav_search -> replaceView(GalleryFragment(), R.id.nav_host_fragment_home_container)
                R.id.bottom_nav_favorites -> replaceView(MovieFragment.newInstance(), R.id.nav_host_fragment_home_container)

            }
            true
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
                R.id.drawer_nav_gallery -> replaceView(GalleryFragment(), R.id.nav_host_fragment_home_container)
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
        AuthenticationRepository().signOut()
        finish()
    }

}