package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    var user : LiveData<FirebaseUser?> = _user

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    fun createNewAccount(email: String, password: String){
        authenticationRepository.createAccountWithEmailPassword(email, password){fireBaseUser, error ->
           if (fireBaseUser != null) {
               _user.value = fireBaseUser
           } else {
               _error.value = error ?: "Error diferente!"
           }

        }
    }

}