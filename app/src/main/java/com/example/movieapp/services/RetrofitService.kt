package com.example.movieapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitService {

    private val retrofitMovieInfo = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAllMovies() : MoviesService {
        return retrofitMovieInfo.create(MoviesService::class.java)
    }

    private val retrofitMovieVideo = Retrofit.Builder()
        .baseUrl("https://youtube.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    fun getMovieVideo() : MoviesService {
//        return  retrofitMovieVideo.create(VideoService::class.java)
//    }
}