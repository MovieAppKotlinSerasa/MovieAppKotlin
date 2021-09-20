package com.example.movieapp.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import com.example.movieapp.HomeActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.LoginFragmentBinding
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view_model.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private lateinit var binding: LoginFragmentBinding

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private val observerUser = Observer<FirebaseUser>{
        Intent(requireContext(), HomeActivity::class.java).apply {
            startActivity(this)
        }
        requireActivity().finish()
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

        binding.enterButton.setOnClickListener {

            val inputEmail = binding.editTextEmailUser.text
            val inputPassword = binding.editTextPassword.text

            if (!inputEmail.isNullOrEmpty() && !inputPassword.isNullOrEmpty()){
                viewModel.signIn(inputEmail.toString(), inputPassword.toString())
            } else {
                Snackbar.make(view, "Preencha todos os campos",
                    Snackbar.LENGTH_LONG).show()
            }

        }

        binding.signUpButton.setOnClickListener {
            requireActivity().replaceView(SignUpFragment.newInstance(), R.id.container)
        }

    }

}