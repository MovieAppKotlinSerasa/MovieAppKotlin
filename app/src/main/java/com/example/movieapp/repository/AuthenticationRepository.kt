package com.example.movieapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject


class AuthenticationRepository @Inject constructor(
    private val auth : FirebaseAuth
) {

    private val uid = auth.currentUser?.uid
    private var storageReference: StorageReference =
        FirebaseStorage.getInstance().getReference("Users/${uid}")

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