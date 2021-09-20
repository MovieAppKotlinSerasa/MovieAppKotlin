package com.example.movieapp.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.movieapp.database.AppDatabase
import com.example.movieapp.database.dao.UsersDAO
import com.example.movieapp.model.User
import com.google.common.truth.Truth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class UsersDAOTest {
    private lateinit var usersDao: UsersDAO
    private lateinit var database: AppDatabase

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        usersDao = database.usersDAO()

    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertUser_and_fetchAllUsers_should_return_true() {
        val user = User(id = 1, userEmail = "teste@gmail.com")
        CoroutineScope(Dispatchers.Default).launch {
            usersDao.insertNewUser(user)

            Truth.assertThat(usersDao.fetchAllUsers()).contains(user)
        }
    }


}