package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class Movies(

    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Results>

)

data class Results(

    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String
)