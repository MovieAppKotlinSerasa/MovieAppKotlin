package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritesMovies(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val movie_title: String,
    val movie_image: String,
    val movie_description: String
)
