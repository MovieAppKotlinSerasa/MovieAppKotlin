package com.example.movieapp.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.movieapp.database.AppDatabase
import com.example.movieapp.database.dao.FavoritesMoviesDAO
import com.example.movieapp.model.Movie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class FavoritesMovieResultDAOTest {

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
    fun insertMovie_and_fetchOneMovie_should_return_true() {
        val movie = Movie(pkMovie = 1, userEmail = "teste@gmail.com", id = 1,  title ="Titanic", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        CoroutineScope(Dispatchers.Default).launch {
            favMoviesDao.insertFavoriteMovie(movie)

            assertThat(favMoviesDao.fetchFavoriteMoviesById(1)).isEqualTo(movie)
        }
    }

    @Test
    fun fetchAllMovies_should_return_true() {
        val movie = Movie(pkMovie = 1, userEmail = "teste@gmail.com", id = 1,  title ="Titanic", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        val movie2 = Movie(pkMovie = 2, userEmail = "teste@gmail.com", id = 2,  title ="Jurassic Park", overview = "Description", poster_path = "/url", vote_average = 8.7F, genres = null)

        CoroutineScope(Dispatchers.Default).launch {
            favMoviesDao.insertFavoriteMovie(movie)
            favMoviesDao.insertFavoriteMovie(movie2)

            assertThat(favMoviesDao.fetchFavoritesMoviesByEmail("teste@gmail.com")).contains(movie2)
        }
    }

    @Test
    fun deleteOneMovie_should_return_true() {

        val movie = Movie(pkMovie = 1, userEmail = "teste@gmail.com", id = 1,  title ="Titanic", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        CoroutineScope(Dispatchers.Default).launch {
            favMoviesDao.insertFavoriteMovie(movie)
            favMoviesDao.deleteOneFavoriteMovie(email = "teste@gmail.com", movieID = 1)

            assertThat(favMoviesDao.fetchFavoritesMoviesByEmail("teste@gmail.com")).doesNotContain(
                movie
            )
        }
    }

    @Test
    fun deleteAllMovies_should_return_true() {

        val movie = Movie(pkMovie = 1, userEmail = "teste@gmail.com", id = 1,  title ="Titanic", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        val movie2 = Movie(pkMovie = 2, userEmail = "teste@gmail.com", id = 2,  title ="Jurassic Park", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        CoroutineScope(Dispatchers.Default).launch {
            favMoviesDao.insertFavoriteMovie(movie)
            favMoviesDao.insertFavoriteMovie(movie2)
            favMoviesDao.deleteAllFavoritesMovies("teste@gmail.com")

            assertThat(favMoviesDao.fetchFavoritesMoviesByEmail("teste@gmail.com")).doesNotContain(movie)
            assertThat(favMoviesDao.fetchFavoritesMoviesByEmail("teste@gmail.com")).doesNotContain(movie2)
        }
    }

}