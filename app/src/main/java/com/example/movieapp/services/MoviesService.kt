package com.example.movieapp.services

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.MoviesData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getMostPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Call<MoviesData>

    @GET("genre/movie/list")
    fun getAllGenres(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    )



}