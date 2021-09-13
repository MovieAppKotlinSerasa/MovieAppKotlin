package com.example.movieapp.repository

import com.example.movieapp.model.Movies
import com.example.movieapp.services.MoviesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRepository: MoviesService
) {

    suspend fun getAllMoviesFromService(page: Int, genres: List<Int>): Movies? {

        return withContext(Dispatchers.Default) {
            val response = moviesRepository.getMostPopularMovies(page = page, genres = genres.joinToString())
            chargeData(response)
        }

    }

    private fun <T> chargeData(data: Response<T>): T? {
        return if (data.isSuccessful) {
            data.body()
        } else {
            null
        }
    }

//    val moviesRepository = RetrofitService.getAllMovies()
}