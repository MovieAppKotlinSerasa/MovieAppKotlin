package com.example.movieapp.repository

import com.example.movieapp.model.Movie
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

const val BILLS_COLLECTION = "favoritos"


class FavoritesRepository @Inject constructor(

) {

    private val dataBase = Firebase.firestore

    fun addFavorite(movies: List<Long>) {

        dataBase.collection(BILLS_COLLECTION)
            .document("arthur@gmail.com")
            .set(
                hashMapOf(
                    "filmes" to movies
                )
            )

    }

    fun getAllMoviesFromFirebase(callback: (List<Movie>?, String?) -> Unit) {

        val docRef = dataBase.collection("favoritos").document("arthur@gmail.com")
        docRef.get()
            .addOnSuccessListener { document ->
                val listOf = arrayListOf<Movie>()

                document.data?.values?.forEach {

                    (it as? ArrayList<*>)?.forEach {

                        println(it)

                    }

                }

                callback(listOf, null)

            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

}