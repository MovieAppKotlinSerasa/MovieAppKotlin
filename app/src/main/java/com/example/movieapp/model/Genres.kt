package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres")
    val genresList: GenresList
)

data class GenresList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
