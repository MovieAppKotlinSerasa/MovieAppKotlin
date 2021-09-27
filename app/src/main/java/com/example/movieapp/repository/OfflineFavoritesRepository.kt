package com.example.movieapp.repository

import com.example.movieapp.database.dao.FavoritesMoviesDAO
import com.example.movieapp.model.Movie
import javax.inject.Inject

class OfflineFavoritesRepository @Inject constructor(
    private val favMovies: FavoritesMoviesDAO
) {

    suspend fun fetchAllFromDatabase(movieTitle: String): List<Movie> {
        return favMovies.fetchFavoritesMoviesByName(movieTitle)
    }

    suspend fun fetchById(movieId: Long): Movie {
        return favMovies.fetchFavoriteMoviesById(movieId)
    }

    suspend fun insertNewFavList(movies: List<Movie>) {
        favMovies.insertFavoriteMovieList(movies)
    }

    suspend fun insertNewFavMovie(movie: Movie) {
        favMovies.insertFavoriteMovie(movie)
    }

    suspend fun deleteFavMovie(movieID: Long) {
        favMovies.deleteOneFavoriteMovie(movieID)
    }

    suspend fun clearFavList() {
        favMovies.deleteAllFavoritesMovies()
    }

}