package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class MovieResult(

    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>

)

data class Movie(

    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("genres")
    val genreList: List<Genre>?
)

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)