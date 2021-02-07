package com.rishav.tmdbapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rishav.tmdbapp.data.Movie


@Dao
interface MovieDao {
    @Insert
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieList: List<Movie>)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Transaction
    @Query("SELECT * FROM movie_table ORDER BY title ASC")
    suspend fun getMovies(): List<Movie>?

    @Transaction
    @Query("SELECT * FROM movie_table ORDER BY voteAverage DESC")
    suspend fun getMoviesByRating(): List<Movie>?

    @Transaction
    @Query("SELECT * FROM movie_table ORDER BY popularity DESC")
    suspend fun getMoviesByPopularity(): List<Movie>?
}