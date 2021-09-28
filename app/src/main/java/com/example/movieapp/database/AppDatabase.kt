package com.example.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.database.dao.FavoritesMoviesDAO
import com.example.movieapp.model.Genre
import com.example.movieapp.model.Movie

@Database(entities = [Movie::class, Genre::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun favoriteMoviesDAO(): FavoritesMoviesDAO

    companion object{
        fun getDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "movies_database"
            ).fallbackToDestructiveMigration().build()
        }

    }

}