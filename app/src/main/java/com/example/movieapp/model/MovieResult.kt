package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MovieResult(
    val page: Int,
    val results: List<Movie>
)

@Entity
data class Movie(
    @PrimaryKey
    var pkMovie: Long? = 0,
    var userEmail: String? = null,
    val id: Long,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Float,
    val genres: List<Genre>?

)

@Entity
data class Genre(
    @PrimaryKey(autoGenerate = true)
    var pkGenre: Long? = 0,
    val id: Int,
    val name: String
)

data class GenreResult(

    val genres: List<Genre>?
)

