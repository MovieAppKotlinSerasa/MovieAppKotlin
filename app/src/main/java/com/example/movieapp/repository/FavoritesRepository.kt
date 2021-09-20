package com.example.movieapp.repository

import com.example.movieapp.database.AppDatabase
import com.example.movieapp.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavoritesRepository @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dataBase: FirebaseFirestore
) {

//    private val dataBase = Firebase.firestore

    fun removeFavorite(id: Long, onComplete: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            dataBase
                .collection("favoritos")
                .document(user.uid)
                .get()
                .addOnSuccessListener {

                    val listOfFilmes = it["filmes"]?.let { it as ArrayList<Any> } ?: arrayListOf()

                    if (listOfFilmes.contains(id)) {

                        listOfFilmes.remove(id)

                        dataBase
                            .collection("favoritos")
                            .document(user.uid)
                            .set(
                                hashMapOf(
                                    "filmes" to listOfFilmes
                                )
                            ).addOnSuccessListener {
                                onComplete(true)
                            }
                    }
                }
        }
    }


    fun addFavorite(id: Long) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            dataBase
                .collection("favoritos")
                .document(user.uid)
                .get()
                .addOnSuccessListener {
                    val listOfFilmes = it["filmes"]?.let { it as ArrayList<Any> } ?: arrayListOf()
                    if (!listOfFilmes.contains(id)) {

                        listOfFilmes.add(id)

                        dataBase
                            .collection("favoritos")
                            .document(user.uid)
                            .set(
                                hashMapOf(
                                    "filmes" to listOfFilmes
                                )
                            )
                    }
            }
        }
    }

    fun getAllMoviesFromFirebase(callback: (List<Movie>?, String?) -> Unit) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            dataBase
                .collection("favoritos")
                .document(user.uid)
                .get()
                .addOnSuccessListener {

                    val listOfFilmes = it["filmes"]?.let { it as ArrayList<Any> } ?: arrayListOf()

                    GlobalScope.launch(Dispatchers.Main) {

                        val listOf = arrayListOf<Movie>()

                        listOfFilmes.forEach { idMovie ->
                            val id = idMovie.toString().toLong()
                            getMovieById(id)?.let { movie ->
                                listOf.add(
                                    movie
                                )
                            }
                        }

                        callback(listOf, null)

                    }
                }
                .addOnFailureListener {
                    callback(null, it.message)
                }
        }
    }

    suspend fun getMovieById(id: Long): Movie? {
        println(id)
        return moviesRepository.getMovieById(id)
    }

}