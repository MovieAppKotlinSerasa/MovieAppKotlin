package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movies
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.repository.MoviesRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val moviesRepository: MoviesRepository
    ) : ViewModel() {

    private val _movies = MutableLiveData<Movies>()
    val movies: LiveData<Movies> = _movies

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser: LiveData<FirebaseUser> = _currentUser

    fun fetchCurrentUser() {
        authRepository.currentUser().apply {
            _currentUser.value = this
        }
    }

    fun getMovies() {

        viewModelScope.launch {
            moviesRepository.getAllMoviesFromService(1, listOf(12,16))?.let { movies ->
                _movies.value = movies
            }
        }
    }
}