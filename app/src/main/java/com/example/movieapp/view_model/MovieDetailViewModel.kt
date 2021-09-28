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
            _favMovies.value = offlineRepository.fetchAllFromDatabase("")
        }
    }

    fun addFavorite(id: Long) {
        favoritesRepository.addFavorite(id)
        addLocalFavs(id)
    }

    fun removeFavorite(id: Long) {
        removeLocalFav(id)
        viewModelScope.launch {
            favoritesRepository.removeFavorite(id) {
            }
        }
    }

    private fun removeLocalFav(id: Long) {
        viewModelScope.launch {
            val movie = repository.getMovieById(id)
            if (movie != null) {
                offlineRepository.deleteFavMovie(movie.id)
            }
        }
    }

    private fun addLocalFavs(id: Long) {
        viewModelScope.launch {
            val listLocalFav = offlineRepository.fetchAllFromDatabase("")
            var movieNotSaved = true
            listLocalFav.forEach { movie ->
                if (movie.id == id) {
                    movieNotSaved = false
                }
            }
            if (movieNotSaved) {
                val movie = repository.getMovieById(id)
                movie?.let { offlineRepository.insertNewFavMovie(movie) }
            }
        }
    }

    fun fetchLocalFav(movieId: Long) {
        viewModelScope.launch {
            _offlineMovieDetail.value = offlineRepository.fetchById(movieId)
        }
    }

}