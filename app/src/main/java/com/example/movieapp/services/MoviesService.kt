package com.example.movieapp.services

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.MovieTrailerResult
import com.example.movieapp.model.MovieResult
import com.example.movieapp.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("/3/movie/popular")
    suspend fun getMostPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int,
        @Query("with_genres") genres: String = "12,16",
        /** to do -> val numbers = listOf(1, 2, 3, 4, 5, 6)
         * numbers.joinToString() // 1, 2, 3, 4, 5, 6
         */
    ): Response<MovieResult>

    @GET("/3/search/movie")
    suspend fun getFilteredMoviesByName(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("query") title: String,
        @Query("page") page: Int,
    ): Response<MovieResult>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieByID(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Response<Movie>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getMovieTrailerByID(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Response<MovieTrailerResult>
}