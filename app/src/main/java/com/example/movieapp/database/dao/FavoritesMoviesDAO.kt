package com.example.movieapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.model.FavoritesMovies

@Dao
interface FavoritesMoviesDAO {

    @Insert
    fun insertFavoriteMovie(favoritesMovies: FavoritesMovies)

    @Query("SELECT * FROM FavoritesMovies WHERE movie_title LIKE '%' || :title || '%'")
    fun fetchFilteredFavoritesMovies(title: String): List<FavoritesMovies>

    @Query("SELECT * FROM FavoritesMovies")
    fun fetchAllFavoritesMovies(): List<FavoritesMovies>

    @Delete
    fun deleteOneFavoriteMovie(favoritesMovies: FavoritesMovies)

    @Delete
    fun deleteAllFavoritesMovies(allFavoritesMovies: List<FavoritesMovies>)

}