package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MovieTrailerResult
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.FavoritesRepository
import com.example.movieapp.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository) : ViewModel() {

    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetail: LiveData<Movie> = _movieDetail

    private val _movieTrailerDetail = MutableLiveData<MovieTrailerResult>()
    val movieTrailerDetail: LiveData<MovieTrailerResult> = _movieTrailerDetail

    fun getMovieById(id: Long) {
        viewModelScope.launch {
            _movieDetail.value = repository.getMovieById(id)
            _movieTrailerDetail.value = repository.getMovieTrailerById(id)
        }
    }

    fun addFavorite(id: Long) {
        favoritesRepository.addFavorite(id)
    }

}