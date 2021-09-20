package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Genre
import com.example.movieapp.model.GenreResult
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResult
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.repository.FavoritesRepository
import com.example.movieapp.repository.MoviesRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository
    ) : ViewModel() {

    private val _movies = MutableLiveData<MovieResult>()
    val movieResult: LiveData<MovieResult> = _movies

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser: LiveData<FirebaseUser> = _currentUser

    private val _listOfGenres = MutableLiveData<HashMap<Genre, List<Movie>?>>()
    val listOfGenres : LiveData<HashMap<Genre, List<Movie>?>> = _listOfGenres

    fun fetchCurrentUser() {
        authRepository.currentUser().apply {
            _currentUser.value = this
        }
    }

    suspend fun getMovies(genre: Int) : List<Movie>? = withContext(Dispatchers.Main) {
        moviesRepository.getAllMoviesFromService(genre = genre, page = 1)?.results
    }

    fun getListOfGenres() {

        viewModelScope.launch {
            moviesRepository.getListOfGenres()?.let {

                val hashMapData = hashMapOf<Genre, List<Movie>?>()

                it.genres?.forEach {
                    val listOfMoviews = getMovies(it.id)
                    hashMapData[it] = listOfMoviews
                }
                _listOfGenres.value = hashMapData
            }
        }
    }
}