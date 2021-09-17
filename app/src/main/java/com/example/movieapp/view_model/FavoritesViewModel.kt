package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Results
import com.example.movieapp.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Results>>()
    val movies: LiveData<List<Results>> = _movies

    fun getMovies() {

        viewModelScope.launch {
            favoritesRepository.getAllMoviesFromFirebase{ bills, error ->

                _movies.value = bills

            }

        }

    }
}