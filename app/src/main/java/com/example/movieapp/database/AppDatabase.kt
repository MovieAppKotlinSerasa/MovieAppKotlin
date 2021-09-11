package com.example.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.database.dao.FavoritesMoviesDAO
import com.example.movieapp.model.FavoritesMovies

@Database(entities = [FavoritesMovies::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun favoriteMoviesDAO(): FavoritesMoviesDAO

    companion object{
        fun getDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "movies_database"
            ).allowMainThreadQueries().build()
        }

    }


}