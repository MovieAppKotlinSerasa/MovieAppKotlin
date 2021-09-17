package com.example.movieapp.repository

import com.example.movieapp.model.MovieTrailerResult
import com.example.movieapp.model.MovieResult
import com.example.movieapp.model.Movie
import com.example.movieapp.services.MoviesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesRepository: MoviesService
) {

    suspend fun getAllMoviesFromService(page: Int, genres: List<Int>): MovieResult? {

        return withContext(Dispatchers.Default) {
            val response = moviesRepository.getMostPopularMovies(page = page, genres = genres.joinToString())
            chargeData(response)
        }

    }

    suspend fun getFilteredMovies(page: Int, query: String): MovieResult? {

        return withContext(Dispatchers.Default) {
            val response = moviesRepository.getFilteredMoviesByName(page = page, title = query)
            chargeData(response)
        }
    }

    suspend fun getMovieById(id: Long): Movie? {

        return  withContext(Dispatchers.Default) {
            val response = moviesRepository.getMovieByID(id = id)
            chargeData(response)
        }
    }

    suspend fun getMovieTrailerById(id: Long): MovieTrailerResult? {
        return withContext(Dispatchers.Default) {
            val response = moviesRepository.getMovieTrailerByID(id)
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
}