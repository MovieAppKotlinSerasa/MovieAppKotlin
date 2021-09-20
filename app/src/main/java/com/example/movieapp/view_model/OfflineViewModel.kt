package com.example.movieapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.model.User
import com.example.movieapp.repository.OfflineFavoritesRepository
import com.example.movieapp.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineViewModel @Inject constructor(
    private val offFavRepository: OfflineFavoritesRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun fetchFavorites(selectedUserEmail: String) {

        viewModelScope.launch {
            _movies.value = offFavRepository.fetchAllFromDatabase(selectedUserEmail)
        }
    }

    fun fetchUsers() {

        viewModelScope.launch {
            _users.value = usersRepository.fetchUsers()
        }
    }

}