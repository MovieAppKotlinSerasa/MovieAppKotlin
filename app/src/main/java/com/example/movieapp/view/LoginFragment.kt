package com.example.movieapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.movieapp.R
import com.example.movieapp.databinding.LoginFragmentBinding
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view_model.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment(R.layout.login_fragment) {

    lateinit var binding: LoginFragmentBinding

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private val observerUser = Observer<FirebaseUser>{
        requireActivity().replaceView(MainFragment.newInstance())
    }

    private val observerError = Observer<String>{
        Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.error.observe(viewLifecycleOwner, observerError)
        viewModel.user.observe(viewLifecycleOwner, observerUser)

        binding = LoginFragmentBinding.bind(view)

        binding.buttonEntrar.setOnClickListener {

            val inputEmail = binding.editTextTextEmailUser.text
            val inputPassword = binding.editTextTextPassword.text

            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()){
                viewModel.signIn(inputEmail.toString(), inputPassword.toString())
            }

        }

        binding.buttonCadastrar.setOnClickListener {

        }

    }

}