package com.example.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.HomeActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.LoginFragmentBinding
import com.example.movieapp.utils.replaceView
import com.example.movieapp.utils.showMessage
import com.example.movieapp.view_model.LoginViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private lateinit var binding: LoginFragmentBinding

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private val observerUser = Observer<FirebaseUser> {
        Intent(requireContext(), HomeActivity::class.java).apply {
            startActivity(this)
        }
        requireActivity().finish()
    }

    private val observerError = Observer<String> {
        requireActivity().showMessage(requireView(), replaceErrorMessage(it))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.error.observe(viewLifecycleOwner, observerError)
        viewModel.user.observe(viewLifecycleOwner, observerUser)

        binding = LoginFragmentBinding.bind(view)

        binding.enterButton.setOnClickListener {

            val inputEmail = binding.editTextEmailUser.text.toString()
            val inputPassword = binding.editTextPassword.text.toString()

            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {

                if (inputPassword.length >= 6) {
                    viewModel.signIn(inputEmail, inputPassword)
                } else {
                    requireActivity().showMessage(view, "A senha deve ter 6 digitios ou mais.")
                }

            } else {
                requireActivity().showMessage(view, "Preencha todos os campos")
            }

        }

        binding.signUpButton.setOnClickListener {
            requireActivity().replaceView(SignUpFragment.newInstance(), R.id.container)
        }

    }

    private fun replaceErrorMessage(message: String): String {

        return when (message) {

            "We have blocked all requests from this device due to unusual activity." ->
                "O acesso a esta conta foi temporariamente desativado. Tente novamente em 10 minutos."

            "The password is invalid or the user does not have a password." ->
                "E-mail ou senha inválida"

            "The email address is badly formatted." ->
                "O endereço de e-mail informado é inválido"

            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." ->
                "Sem conexão de internet"

            "There is no user record corresponding to this identifier. The user may have been deleted." ->
                "Não foi encontrado nenhum usuário registrado com este e-mail."

            else -> {
                message
            }

        }
    }

}