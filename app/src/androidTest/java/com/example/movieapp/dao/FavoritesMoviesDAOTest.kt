package com.example.movieapp.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.movieapp.database.AppDatabase
import com.example.movieapp.database.dao.FavoritesMoviesDAO
import com.example.movieapp.model.FavoritesMovies
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class FavoritesMoviesDAOTest {

    private lateinit var favMoviesDao: FavoritesMoviesDAO
    private lateinit var database: AppDatabase

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        favMoviesDao = database.favoriteMoviesDAO()

    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertMovie_should_return_true() {
        val movie = FavoritesMovies(id = 1, movie_title = "Titanic", "UrlImage", "Description")
        favMoviesDao.insertFavoriteMovie(movie)

        assertThat(favMoviesDao.fetchFilteredFavoritesMovies("Titanic")).contains(movie)

    }

    @Test
    fun fetchAllMovies_should_return_true() {
        val movie = FavoritesMovies(id = 1, movie_title = "Titanic", "UrlImage", "Description")
        val movie2 = FavoritesMovies(id = 2, movie_title = "Jurassic Park", "UrlImage", "Description")
        favMoviesDao.insertFavoriteMovie(movie)
        favMoviesDao.insertFavoriteMovie(movie2)

        assertThat(favMoviesDao.fetchAllFavoritesMovies()).contains(movie2)

    }

    @Test
    fun deleteOneMovie_should_return_true() {

        val movie = FavoritesMovies(id = 1, movie_title = "Titanic", "UrlImage", "Description")
        favMoviesDao.insertFavoriteMovie(movie)
        favMoviesDao.deleteOneFavoriteMovie(movie)

        assertThat(favMoviesDao.fetchAllFavoritesMovies()).doesNotContain(movie)

    }

    @Test
    fun deleteAllMovies_should_return_true() {

        val movie = FavoritesMovies(id = 1, movie_title = "Titanic", "UrlImage", "Description")
        val movie2 = FavoritesMovies(id = 2, movie_title = "Jurassic Park", "UrlImage", "Description")
        favMoviesDao.insertFavoriteMovie(movie)
        favMoviesDao.insertFavoriteMovie(movie2)
        favMoviesDao.deleteAllFavoritesMovies(listOf(movie,movie2))

        assertThat(favMoviesDao.fetchAllFavoritesMovies()).doesNotContain(movie)
        assertThat(favMoviesDao.fetchAllFavoritesMovies()).doesNotContain(movie2)

    }

}