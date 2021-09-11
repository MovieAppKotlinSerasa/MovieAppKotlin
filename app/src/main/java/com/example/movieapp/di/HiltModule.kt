package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.database.AppDatabase
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.services.MoviesService
import com.example.movieapp.services.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideGetMovies(): MoviesService{
        return RetrofitService.getAllMovies()
    }

    @Provides
    fun getRepository(moviesRepository : MoviesService): MoviesRepository = MoviesRepository(moviesRepository)

//    @Provides
//    fun provideContextDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase(context)

}