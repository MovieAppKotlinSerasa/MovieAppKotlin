package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser>()
    val user : LiveData<FirebaseUser> = _user

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    fun signIn(email: String, password: String){
        authenticationRepository.signInWithEmailPassword(email, password){firebaseUser, error ->
            if (firebaseUser != null){
                _user.value = firebaseUser
            } else {
                _error.value = error ?: "Error Desconhecido!"
            }
        }
    }
}