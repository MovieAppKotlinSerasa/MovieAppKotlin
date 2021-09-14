package com.example.movieapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.databinding.MainFragmentBinding
import com.example.movieapp.view_model.MainViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var userEmailTextView: TextView

    val observerSignOut = Observer<Boolean> {isSignedIn ->
        if (!isSignedIn) {
            requireActivity().finish()
        }
    }

    val observerSignedUser = Observer<FirebaseUser> {user ->
        userEmailTextView.apply {
            text = user.email
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = MainFragmentBinding.bind(view)
        userEmailTextView = view.findViewById(R.id.message)

        viewModel.isSignedIn.observe(viewLifecycleOwner, observerSignOut)
        viewModel.user.observe(viewLifecycleOwner, observerSignedUser)

        binding.buttonLogOut.setOnClickListener {

            viewModel.signOut()

        }

        loadUserData()
    }

    fun loadUserData() {
        viewModel.fetchCurrentUser()
    }

}