package com.example.movieapp.repository

import com.example.movieapp.database.dao.FavoritesMoviesDAO
import com.example.movieapp.model.Movie
import javax.inject.Inject

class OfflineFavoritesRepository @Inject constructor(
    private val favMovies: FavoritesMoviesDAO
) {

    suspend fun fetchAllFromDatabase(userEmail: String, movieTitle: String): List<Movie> {
        return favMovies.fetchFavoritesMoviesByEmailAndName(userEmail, movieTitle)
    }

    suspend fun fetchByEmailAndId(movieId: Long): Movie {
        return favMovies.fetchFavoriteMoviesById(movieId)
    }

    suspend fun insertNewFavList(movies: List<Movie>) {
        favMovies.insertFavoriteMovieList(movies)
    }

    suspend fun insertNewFavMovie(movie: Movie) {
        favMovies.insertFavoriteMovie(movie)
    }

    suspend fun deleteFavMovie(userEmail: String, movieID: Long) {
        favMovies.deleteOneFavoriteMovie(userEmail, movieID)
    }

    suspend fun clearFavList(userEmail: String) {
        favMovies.deleteAllFavoritesMovies(userEmail)
    }

}