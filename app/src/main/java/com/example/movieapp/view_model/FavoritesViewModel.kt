package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.repository.FavoritesRepository
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.repository.OfflineFavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val repository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val offlineRepository: OfflineFavoritesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun removeMovie(id: Long?) {
        if (id != null) {
            removeLocalFavs(id)
            viewModelScope.launch {
                favoritesRepository.removeFavorite(id.toLong()) {
                    getMovies()
                }
            }
        }
    }

    private fun removeLocalFavs(id: Long) {
        val currentUserEmail = authRepository.currentUser()?.email
        if (!currentUserEmail.isNullOrEmpty()) {
            viewModelScope.launch {
                val movie = repository.getMovieById(id)
                if (movie != null) {
                    offlineRepository.deleteFavMovie(currentUserEmail, movie.id)
                }
            }
        }
    }

    fun getMovies() {
        viewModelScope.launch {
            favoritesRepository.getAllMoviesFromFirebase { bills, error ->
                _movies.value = bills
            }
        }
    }
}