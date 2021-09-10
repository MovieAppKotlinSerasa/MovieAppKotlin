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
import com.example.movieapp.databinding.SignUpFragmentBinding
import com.example.movieapp.utils.replaceView
import com.example.movieapp.view_model.LoginViewModel
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

    private val observerNewUser = Observer<FirebaseUser?>{
        Snackbar.make(requireView(), "Usuário criado com sucesso!", Snackbar.LENGTH_LONG).show()
        requireActivity().replaceView(LoginFragment.newInstance())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignUpFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.user.observe(viewLifecycleOwner, observerNewUser)

        binding.buttonRealizarCadastro.setOnClickListener {
            val inputEmail = binding.editTextEmailNovoCadastro.text
            val inputPassword = binding.editTextSenha.text

            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()){
                viewModel.createNewAccount(inputEmail.toString(), inputPassword.toString())
            } else {
                Snackbar.make(view, "Preencha todos os campos",
                    Snackbar.LENGTH_LONG).show()
            }

        }

        binding.buttonCancelar.setOnClickListener {
            requireActivity().replaceView(LoginFragment.newInstance())
        }

    }

}