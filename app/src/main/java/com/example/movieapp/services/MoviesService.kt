package com.example.movieapp.services

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {


    @GET("/3/discover/movie")
    suspend fun getMostPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("page") page: Int,
        @Query("with_genres") genre: String,
        @Query("vote_average.gte") minRate: Number = 0.1,
        @Query("vote_average.lte") maxRate: Number = 9.9,
        @Query("vote_count.gte") minVotes: Number = 100,

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

    @GET("/3/genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Response<GenreResult>
}