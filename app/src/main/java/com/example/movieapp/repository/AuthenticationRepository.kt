package com.example.movieapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class AuthenticationRepository @Inject constructor(
    private val auth : FirebaseAuth
) {

    fun signInWithEmailPassword(
        email: String,
        password: String,
        callback: (FirebaseUser?, String?) -> Unit
    ) {
        val taks = auth.signInWithEmailAndPassword(email, password)

        taks.addOnSuccessListener { authResult ->
            if (authResult.user != null) {
                callback(authResult.user, null)
            }
        }

        taks.addOnFailureListener {
            callback(null, it.message)
        }

    }

    fun createAccountWithEmailPassword(
        email: String,
        password: String,
        callback: (FirebaseUser?, String?) -> Unit
    ) {

        val task = auth.createUserWithEmailAndPassword(email, password)

        task.addOnSuccessListener {
            callback(it.user, null)
        }

        task.addOnFailureListener {
            callback(null, it.message)
        }

    }

    fun signOut() {
        auth.signOut()
    }

    fun currentUser(): FirebaseUser? = auth.currentUser
}