package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val userEmail: String
) {
    override fun toString(): String {
        return userEmail
    }
}