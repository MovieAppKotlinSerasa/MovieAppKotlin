package com.example.movieapp.repository

import com.example.movieapp.database.dao.UsersDAO
import com.example.movieapp.model.User
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersDao: UsersDAO
) {

    suspend fun insertUser(userEmail: String) {
        usersDao.insertNewUser(User(userEmail = userEmail))
    }

    suspend fun fetchUsers(): List<User> {
        return usersDao.fetchAllUsers()
    }

}