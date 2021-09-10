package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    var user : LiveData<FirebaseUser?> = _user

    fun createNewAccount(email: String, password: String){
        authenticationRepository.createAccountWithEmailPassword(email, password){
            _user.value = it
        }
    }

}