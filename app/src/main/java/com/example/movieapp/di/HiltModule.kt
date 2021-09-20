package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.adapter.GenreAdapter
import com.example.movieapp.repository.AuthenticationRepository
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.services.MoviesService
import com.example.movieapp.services.NotificationHandler
import com.example.movieapp.services.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    fun provideAuthenticationRepository() : AuthenticationRepository = AuthenticationRepository()

    @Provides
    fun provideGetMovies(): MoviesService = RetrofitService.getAllMovies()

    @Provides
    fun getRepository(moviesRepository : MoviesService): MoviesRepository = MoviesRepository(moviesRepository)

    @Provides
    fun provideNotificationHandler(@ApplicationContext context: Context): NotificationHandler =
        NotificationHandler(context)

//    @Provides
//    fun provideAdapterContext(@ApplicationContext context: Context): GenreAdapter = GenreAdapter(context)
}

//    @Provides
//    fun provideContextDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase(context)
