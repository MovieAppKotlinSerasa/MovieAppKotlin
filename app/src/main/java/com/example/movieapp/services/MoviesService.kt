package com.example.movieapp.services

import com.example.movieapp.model.MoviesData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getMostPopularMovies(
        @Query("api_key") apiKey: String = "451c1234ee47563ca2fb15ffdce24609",
        @Query("page") page: Int
    ): Call<MoviesData>

}