package com.rishav.tmdbapp.module

import android.content.Context
import androidx.room.Room
import com.rishav.tmdbapp.db.MovieDatabase
import com.rishav.tmdbapp.repository.MovieRepository
import com.rishav.tmdbapp.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MyHiltModule {
    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext applicationContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            "movie_table"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao

    @Provides
    fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl) : MovieRepository = movieRepositoryImpl
}