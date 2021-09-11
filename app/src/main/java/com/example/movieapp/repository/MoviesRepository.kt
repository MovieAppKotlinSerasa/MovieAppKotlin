package com.example.movieapp.repository

import com.example.movieapp.model.MoviesData
import com.example.movieapp.model.Results
import com.example.movieapp.services.MoviesService
import com.example.movieapp.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRepository: MoviesService
) {


    fun getAllMoviesFromService(callback: (MoviesData?, String?) -> Unit) {
        val call = moviesRepository.getMostPopularMovies(page = 1)

        call.enqueue(object: Callback<MoviesData>{

            override fun onResponse(call: Call<MoviesData>, response: Response<MoviesData>) {
                if(response.body() != null) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Error to connect with API response ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MoviesData>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
//    val moviesRepository = RetrofitService.getAllMovies()
}