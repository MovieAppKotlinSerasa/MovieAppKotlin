package com.example.movieapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.movieapp.model.Movie

@Dao
interface FavoritesMoviesDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteMovieList(favoriteMovie: List<Movie>)

    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteMovie(favoriteMovie: Movie)

    @Query("SELECT * FROM Movie WHERE userEmail = :email AND title LIKE '%' || :movieTitle || '%'")
    suspend fun fetchFavoritesMoviesByEmailAndName(email: String, movieTitle: String): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    suspend fun fetchFavoriteMoviesById(movieId: Long): Movie

    @Query("DELETE FROM Movie WHERE userEmail = :email AND id = :movieID")
    suspend fun deleteOneFavoriteMovie(email: String, movieID: Long)

    @Query("DELETE FROM Movie WHERE userEmail = :email")
    suspend fun deleteAllFavoritesMovies(email: String)

}