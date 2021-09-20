package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResult
import com.example.movieapp.model.User
import com.example.movieapp.repository.*
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val offlineRepository: OfflineFavoritesRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _movies = MutableLiveData<MovieResult>()
    val movieResult: LiveData<MovieResult> = _movies

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser: LiveData<FirebaseUser> = _currentUser

    fun fetchCurrentUser() {
        authRepository.currentUser()?.apply {
            _currentUser.value = this
            insertNewLoggedUser(this.email!!)
        }
    }

    fun getMovies() {
        fetchLocalFavs()
        viewModelScope.launch {
            moviesRepository.getAllMoviesFromService(1, listOf(12, 16))?.let { movies ->
                _movies.value = movies
//                val listOfIds = movies.results.map { it.id }
//                favoritesRepository.addFavorite(listOfIds)
            }
        }
    }

    private fun fetchLocalFavs() {
        val currentUserEmail = authRepository.currentUser()?.email
        if (!currentUserEmail.isNullOrEmpty()) {
            favoritesRepository.getAllMoviesFromFirebase { movies, error ->
                if (movies != null) {
                    updateLocalFavList(currentUserEmail, movies)
                }
            }

        }
    }

    private fun updateLocalFavList(currentUserEmail: String, movies: List<Movie>) {
        movies.forEach { Movie ->
            Movie.userEmail = currentUserEmail
        }
        viewModelScope.launch {
            offlineRepository.clearFavList(currentUserEmail)
            offlineRepository.insertNewFavList(movies)
        }
    }

    private fun insertNewLoggedUser(userEmail: String) {
        viewModelScope.launch {
            val users = usersRepository.fetchUsers()
            var doesNotContainsUserEmail = true
            users.forEach {
                if(it.userEmail == userEmail) {
                    doesNotContainsUserEmail = false
                }
            }
            if(doesNotContainsUserEmail) {
                usersRepository.insertUser(userEmail)
            }
        }
    }

}