package com.example.movieapp.services

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.Genres
import com.example.movieapp.model.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getMostPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int,
        @Query("with_genres") genres: String = "12,16",
        /** to do -> val numbers = listOf(1, 2, 3, 4, 5, 6)
         * numbers.joinToString() // 1, 2, 3, 4, 5, 6
         */
    ): Response<Movies>

    @GET("search/movie")
    suspend fun getFilteredMoviesByName(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("query") title: String,
        @Query("page") page: Int,
    ): Response<Movies>

    @GET("genre/movie/list")
    suspend fun getAllGenres(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<Genres>



}