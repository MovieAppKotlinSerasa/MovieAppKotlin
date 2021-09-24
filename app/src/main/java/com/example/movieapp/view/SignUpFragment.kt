package com.example.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.databinding.SignUpFragmentBinding
import com.example.movieapp.model.SignUpModel
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view_model.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.sign_up_fragment) {

    lateinit var binding: SignUpFragmentBinding

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel

    private val observerNewUser = Observer<FirebaseUser?> {
        Snackbar.make(requireView(), "Usuário criado com sucesso!", Snackbar.LENGTH_LONG).show()
        requireActivity().replaceView(LoginFragment.newInstance(), R.id.container)
    }

    private val observerError = Observer<String> {
        Snackbar.make(requireView(), replaceErrorMessage(it), Snackbar.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignUpFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.user.observe(viewLifecycleOwner, observerNewUser)
        viewModel.error.observe(viewLifecycleOwner, observerError)

        binding.buttonRegister.setOnClickListener {

            val inputEmail = binding.emailEditTextView.text.toString()
            val inputPassword = binding.passwordEditText.text.toString()
            val confirmInputPassword = binding.passwordConfirmEditText.text.toString()

            val signUpModel = SignUpModel(inputEmail, inputPassword, confirmInputPassword)
            signUpModel.checkUser()?.let{ error ->
                showMessage(view, error)
            } ?: viewModel.createNewAccount(inputEmail, inputPassword)
        }

        binding.buttonCancel.setOnClickListener {
            requireActivity().replaceView(LoginFragment.newInstance(), R.id.container)
        }

    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(
            view, message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun replaceErrorMessage(message: String): String {
        println(message)

        return when (message) {

            "The email address is already in use by another account." ->
                "O endereço de e-mail já está sendo usado por outra conta."

            "The email address is badly formatted." ->
                "O endereço de e-mail informado é inválido"

            else -> {
                message
            }

        }

    }
}