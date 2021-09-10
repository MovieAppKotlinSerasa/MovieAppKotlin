package com.example.movieapp.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.MoviesData
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.repository.MoviesRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val movieRepository: MoviesRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _isSignedIn = MutableLiveData<Boolean>()
    val isSignedIn: LiveData<Boolean> = _isSignedIn

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser> = _user

    fun signOut() {
        repository.signOut()
        _isSignedIn.value = false
    }

    fun fetchCurrentUser() {
        repository.currentUser()?.apply {
            _user.value = this
        }
    }

    private val _movies = MutableLiveData<MoviesData>()
    val movies: LiveData<MoviesData> = _movies

    fun getMovies() {
        movieRepository.getAllMoviesFromService(){ response, error ->
            if (response!= null) {
                _movies.value = response
            }
        }
    }

}