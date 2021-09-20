package com.example.movieapp.services

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("discover/movie")
    suspend fun getMostPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("page") page: Int,
        @Query("with_genres") genre: String,
        /** to do -> val numbers = listOf(1, 2, 3, 4, 5, 6)
         * numbers.joinToString() // 1, 2, 3, 4, 5, 6
         */
    ): Response<MovieResult>

    @GET("search/movie")
    suspend fun getFilteredMoviesByName(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("query") title: String,
        @Query("page") page: Int,
    ): Response<MovieResult>

    @GET("movie/{movie_id}")
    suspend fun getMovieByID(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Response<Movie>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailerByID(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Response<MovieTrailerResult>

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Response<GenreResult>
}