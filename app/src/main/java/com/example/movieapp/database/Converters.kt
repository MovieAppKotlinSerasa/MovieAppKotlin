package com.example.movieapp.database

import androidx.room.TypeConverter
import com.example.movieapp.model.Genre
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listOfGenresToJson(value: List<Genre>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListOfGenres(value: String) =
        Gson().fromJson(value, Array<Genre>::class.java).toList()
}