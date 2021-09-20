package com.example.movieapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StreamDownloadTask
import javax.inject.Inject
import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.storage.FileDownloadTask

import com.google.android.gms.tasks.OnSuccessListener

import android.os.Environment
import android.util.Log
import java.io.File


class AuthenticationRepository @Inject constructor(

) {

    private val auth = FirebaseAuth.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    private var storageReference: StorageReference = FirebaseStorage.getInstance().getReference("Users/${uid}")

    fun signInWithEmailPassword(
        email: String,
        password: String,
        callback: (FirebaseUser?, String?) -> Unit
    ){
        val taks = auth.signInWithEmailAndPassword(email, password)
        taks.addOnSuccessListener { authResult ->

            if (authResult.user != null){
                callback(authResult.user, null)
            }

            taks.addOnFailureListener {
                callback(null, it.message)
            }

        }
    }

    fun createAccountWithEmailPassword(
        email: String,
        password: String,
        callback: (FirebaseUser?) -> Unit
    ){
        val task = auth.createUserWithEmailAndPassword(email, password)

        task.addOnSuccessListener {
            callback(it.user)
        }

    }

    fun signOut(){
        auth.signOut()
    }

    fun currentUser() : FirebaseUser? = auth.currentUser

//     fun downloadFile() {
//        val storageRef = storageReference.child("Users/$uid")
//        val islandRef = storageRef.child("$uid.jpg")
//
//        val rootPath = File(Environment.getExternalStorageDirectory(), "$uid")
//        if (!rootPath.exists()) {
//            rootPath.mkdirs()
//        }
//        val localFile = File(rootPath, "$uid.jpg")
//        islandRef.getFile(localFile)
//            .addOnSuccessListener {
//                Log.e("firebase ", ";local tem file created  created $localFile")
//                //  updateDb(timestamp,localFile.toString(),position);
//            }.addOnFailureListener(OnFailureListener { exception ->
//                Log.e(
//                    "firebase ",
//                    ";local tem file not created  created $exception"
//                )
//            })
//     }

}