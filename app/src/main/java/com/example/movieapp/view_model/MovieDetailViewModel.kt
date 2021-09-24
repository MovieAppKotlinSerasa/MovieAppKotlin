package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MovieTrailerResult
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.repository.FavoritesRepository
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.repository.OfflineFavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val repository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val offlineRepository: OfflineFavoritesRepository
) : ViewModel() {

    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetail: LiveData<Movie> = _movieDetail

    private val _favMovies = MutableLiveData<List<Movie>>()
    val favMovies: LiveData<List<Movie>> = _favMovies

    private val _movieTrailerDetail = MutableLiveData<MovieTrailerResult>()
    val movieTrailerDetail: LiveData<MovieTrailerResult> = _movieTrailerDetail

    private val _offlineMovieDetail = MutableLiveData<Movie>()
    val offlineMovieDetail: LiveData<Movie> = _offlineMovieDetail

    fun getMovieById(id: Long) {
        viewModelScope.launch {
            _movieDetail.value = repository.getMovieById(id)
            _movieTrailerDetail.value = repository.getMovieTrailerById(id)
        }
    }

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            authRepository.currentUser()?.email?.let {
                _favMovies.value = offlineRepository.fetchAllFromDatabase(it, "")
//            favoritesRepository.getAllMoviesFromFirebase { movies, error ->
//                if(movies != null) {
//                    _favMovies.value = movies
//                }
//            }
            }
        }
    }

    fun addFavorite(id: Long) {
        favoritesRepository.addFavorite(id)
        addLocalFavs(id)
    }

    fun removeFavorite(id: Long) {
            removeLocalFavs(id)
            viewModelScope.launch {
                favoritesRepository.removeFavorite(id) {
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
                    println()
                }
            }
        }
    }

    private fun addLocalFavs(id: Long) {
        val currentUserEmail = authRepository.currentUser()?.email
        if (!currentUserEmail.isNullOrEmpty()) {
            viewModelScope.launch {
                val movie = repository.getMovieById(id)
                val listLocalFav = offlineRepository.fetchAllFromDatabase(currentUserEmail, "")
                if (movie != null) {
                    movie.userEmail = currentUserEmail
                    var movieNotSaved: Boolean = true
                    listLocalFav.forEach {
                        if (it.userEmail == currentUserEmail && it.id == movie.id) {
                            movieNotSaved = false
                        }
                    }
                    if (movieNotSaved) {
                        offlineRepository.insertNewFavMovie(movie)
                        println()
                    }
                }
            }
        }
    }

    fun fetchLocalFavs(movieId: Long) {
        viewModelScope.launch {
            _offlineMovieDetail.value = offlineRepository.fetchByEmailAndId(movieId)
        }
    }

}