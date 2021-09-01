package com.example.movieapp.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

object RetrofitService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAllMovies() : MoviesService {
        return retrofit.create(MoviesService::class.java)
    }

}