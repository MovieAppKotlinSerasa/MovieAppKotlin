package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class MoviesData(

    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: Results

)

data class Results(

    @SerializedName("id")
    val id: Long,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
)