package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class MovieTrailerResult (

    @SerializedName("results")
    val results: List<MovieTrailer>
)

data class MovieTrailer(

    @SerializedName("key")
    val key : String
)
