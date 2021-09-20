package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.database.AppDatabase
import com.example.movieapp.database.dao.FavoritesMoviesDAO
import com.example.movieapp.database.dao.UsersDAO
import com.example.movieapp.repository.*
import com.example.movieapp.services.MoviesService
import com.example.movieapp.services.NotificationHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): AuthenticationRepository = AuthenticationRepository(auth)

    @Provides
    fun provideFirebaseDatabase() : FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideGetMovies(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)

    @Provides
    fun provideMoviesRepository(moviesService: MoviesService): MoviesRepository =
        MoviesRepository(moviesService)

    @Provides
    fun provideFavoriteRepository(moviesRepository : MoviesRepository, database: FirebaseFirestore): FavoritesRepository = FavoritesRepository(moviesRepository, database)

    @Provides
    fun provideNotificationHandler(@ApplicationContext context: Context): NotificationHandler =
        NotificationHandler(context)

    @Provides
    fun provideContextDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    fun provideFavoritesMoviesDao(database: AppDatabase): FavoritesMoviesDAO = database.favoriteMoviesDAO()

    @Provides
    fun provideOfflineRepository(favoritesMoviesDAO: FavoritesMoviesDAO): OfflineFavoritesRepository = OfflineFavoritesRepository(favoritesMoviesDAO)

    @Provides
    fun provideUsersDao(database: AppDatabase): UsersDAO = database.usersDAO()

    @Provides
    fun provideUsersRepository(usersDAO: UsersDAO): UsersRepository = UsersRepository(usersDAO)

}


