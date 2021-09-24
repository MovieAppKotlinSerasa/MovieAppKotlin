package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MovieResult
import com.example.movieapp.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<MovieResult>()
    val movieResult : LiveData<MovieResult> = _movies

    private val _page = MutableLiveData<Int>(1)
    val page : LiveData<Int> = _page

    fun getFilteredMovies(page: Int, query: String) {
        viewModelScope.launch {
           _movies.value = repository.getFilteredMovies(page, query)
        }
    }

    fun getFilteredMoviesByGenre(page: Int, genreId: Int, sortBy: String){
        viewModelScope.launch {
            _movies.value = repository.getAllMoviesFromService(page, genreId, sortBy)
        }
    }

    fun nextPage(){
        _page.value = _page.value!! + 1
    }

}

