package com.example.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.database.dao.FavoritesMoviesDAO
import com.example.movieapp.database.dao.UsersDAO
import com.example.movieapp.model.Genre
import com.example.movieapp.model.Movie
import com.example.movieapp.model.User

@Database(entities = [Movie::class, Genre::class, User::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun favoriteMoviesDAO(): FavoritesMoviesDAO
    abstract fun usersDAO(): UsersDAO

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