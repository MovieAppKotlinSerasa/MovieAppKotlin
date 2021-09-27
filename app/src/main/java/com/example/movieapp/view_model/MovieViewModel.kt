package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Genre
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.repository.FavoritesRepository
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.repository.OfflineFavoritesRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val offlineRepository: OfflineFavoritesRepository,
) : ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser: LiveData<FirebaseUser> = _currentUser

    private val _listOfGenres = MutableLiveData<HashMap<Genre, List<Movie>?>>()
    val listOfGenres: LiveData<HashMap<Genre, List<Movie>?>> = _listOfGenres

    fun fetchCurrentUser() {
        authRepository.currentUser()?.apply {
            _currentUser.value = this
        }
        fetchLocalFavs()
    }

    private suspend fun getMovies(genre: Int, sortBy: String): List<Movie>? =
        withContext(Dispatchers.Main) {
            moviesRepository.getAllMoviesFromService(
                genre = genre,
                page = 1,
                sortBy = sortBy
            )?.results
        }

    fun getListOfGenres(sortBy: String) {

        viewModelScope.launch {
            moviesRepository.getListOfGenres()?.let {

                val hashMapData = hashMapOf<Genre, List<Movie>?>()

                it.genres?.forEach { genre ->
                    val listOfMoviews = getMovies(genre.id, sortBy)
                    hashMapData[genre] = listOfMoviews
                }
                _listOfGenres.value = hashMapData

            }
        }
    }

    private fun fetchLocalFavs() {
        favoritesRepository.getAllMoviesFromFirebase { movies, error ->
            if (movies != null) {
                updateLocalFavList(movies)
            }
        }
    }

    private fun updateLocalFavList(movies: List<Movie>) {
        viewModelScope.launch {
            val listOfMovies = offlineRepository.fetchAllFromDatabase("")
            offlineRepository.clearFavList(listOfMovies)
            offlineRepository.insertNewFavList(movies)
        }
    }

}