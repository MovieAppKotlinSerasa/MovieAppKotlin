package com.example.movieapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.movieapp.model.User

@Dao
interface UsersDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insertNewUser(user: User)

    @Query("SELECT * FROM User")
    suspend fun fetchAllUsers(): List<User>

    @Delete
    suspend fun deleteUser(user: User)

}