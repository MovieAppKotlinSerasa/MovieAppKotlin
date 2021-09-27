package com.example.movieapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.movieapp.databinding.ActivityHomeBinding
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.services.NotificationHandler
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view.FavoritesFragment
import com.example.movieapp.view.MovieFragment
import com.example.movieapp.view.SearchFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var notificationHandler: NotificationHandler

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var binding: ActivityHomeBinding
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    private var storageReference: StorageReference = FirebaseStorage.getInstance().reference
    val REQUEST_CODE = 100

    // Create a reference with an initial file path and name
    private val pathReference = storageReference.child("Users/Profile/ProfilePicture/$uid")
    private val ONE_MEGABYTE: Long = 1024 * 1024

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadUserImageFromFirebase()
        drawNavigationSetup()
        bottomNavigationSetup()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            uploadProfilePic(data?.data, ::onCompleteUpload)
        }
    }

    private fun onCompleteUpload(isComplete: Boolean) {

        if (isComplete) {
            loadUserImageFromFirebase()
        }

    }

    private fun bottomNavigationSetup() {
        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.bottom_nav_home -> replaceView(
                    MovieFragment.newInstance(),
                    R.id.nav_host_fragment_home_container
                )

                R.id.bottom_nav_search -> replaceView(
                    SearchFragment(),
                    R.id.nav_host_fragment_home_container
                )

                R.id.bottom_nav_favorites -> replaceView(
                    FavoritesFragment.newInstance(),
                    R.id.nav_host_fragment_home_container
                )

            }
            true
        }
    }

    fun setSelectedItemOnBottomNav(position: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            binding.bottomNavigation.menu.getItem(position).isChecked = true
        }

    }

    private fun startSettingsActivity() {
        Intent(this, SettingsActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun drawNavigationSetup() {

        setSupportActionBar(binding.appToolbar)
        supportActionBar?.title = ""

        drawerLayout = binding.drawerLayout
        navView = binding.navView

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceView(MovieFragment.newInstance(), R.id.nav_host_fragment_home_container)

        binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.imageViewUserAvatar)
            .setOnClickListener {
                openGalleryForImage()
            }

        navView.setNavigationItemSelectedListener {

            val notificationResponse = CoroutineScope(Dispatchers.Main).async {
                showNotification()
            }
            when (it.itemId) {

                R.id.drawer_nav_home -> replaceView(
                    MovieFragment.newInstance(),
                    R.id.nav_host_fragment_home_container
                )
                R.id.drawer_nav_settings -> startSettingsActivity()
                R.id.drawer_nav_notifications -> CoroutineScope(Dispatchers.Main).launch { notificationResponse.await() }
                R.id.drawer_nav_signout -> signOut()

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {

        getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()

        AuthenticationRepository(auth).signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()

    }

    suspend fun showNotification() {
        delay(10000)
        notificationHandler.createNotification(
            "Notificação",
            "Já adicionou todos os seus filmes na sua coleção?"
        ).run {
            val notificationManager = NotificationManagerCompat.from(applicationContext)
            notificationManager.notify(1, this)
        }
    }

    private fun uploadProfilePic(data: Uri?, onComplete: (Boolean) -> Unit) {
        if (data != null) {
            FirebaseStorage.getInstance().getReference("Users/Profile/ProfilePicture/$uid")
                .putFile(data)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onComplete(true)
                    }
                }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun loadUserImageFromFirebase() {

        pathReference.getBytes(ONE_MEGABYTE)
            .addOnSuccessListener { byteArray ->

                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).let { image ->
                    binding.navView.getHeaderView(0)
                        .findViewById<ImageView>(R.id.imageViewUserAvatar).apply {
                            setImageBitmap(image)
                        }
                }

            }
            .addOnFailureListener {

            }
    }

}