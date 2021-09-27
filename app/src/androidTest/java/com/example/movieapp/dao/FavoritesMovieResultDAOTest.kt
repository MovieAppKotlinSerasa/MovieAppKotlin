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
        val movie = Movie(id = 1,  title ="Titanic", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        CoroutineScope(Dispatchers.Default).launch {
            favMoviesDao.insertFavoriteMovie(movie)

            assertThat(favMoviesDao.fetchFavoriteMoviesById(1)).isEqualTo(movie)
        }
    }

    @Test
    fun fetchAllMovies_should_return_true() {
        val movie = Movie(id = 1,  title ="Titanic", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        val movie2 = Movie(id = 2,  title ="Jurassic Park", overview = "Description", poster_path = "/url", vote_average = 8.7F, genres = null)

        CoroutineScope(Dispatchers.Default).launch {
            favMoviesDao.insertFavoriteMovie(movie)
            favMoviesDao.insertFavoriteMovie(movie2)

            assertThat(favMoviesDao.fetchFavoritesMoviesByName("")).contains(movie2)
        }
    }

    @Test
    fun deleteOneMovie_should_return_true() {

        val movie = Movie(id = 1,  title ="Titanic", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        CoroutineScope(Dispatchers.Default).launch {
            favMoviesDao.insertFavoriteMovie(movie)
            favMoviesDao.deleteOneFavoriteMovie( movieID = 1)

            assertThat(favMoviesDao.fetchFavoriteMoviesById(1)).isNotEqualTo(movie)
        }
    }

    @Test
    fun deleteAllMovies_should_return_true() {

        val movie = Movie(id = 1,  title ="Titanic", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        val movie2 = Movie(id = 2,  title ="Jurassic Park", overview = "Description", poster_path = "/url", vote_average = 7.5F, genres = null)
        CoroutineScope(Dispatchers.Default).launch {
            favMoviesDao.insertFavoriteMovie(movie)
            favMoviesDao.insertFavoriteMovie(movie2)
            favMoviesDao.deleteAllFavoritesMovies()

            assertThat(favMoviesDao.fetchFavoritesMoviesByName("")).doesNotContain(movie)
            assertThat(favMoviesDao.fetchFavoritesMoviesByName("")).doesNotContain(movie2)
        }
    }

}