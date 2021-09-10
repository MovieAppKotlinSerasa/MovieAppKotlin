package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.MoviesData
import com.example.movieapp.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val _movies = MutableLiveData<MoviesData>()
    val movies: LiveData<MoviesData> = _movies

    fun getMovies() {
        repository.getAllMoviesFromService(){ response, error ->
            if (response!= null) {
                _movies.value = response
            }
        }
    }
}