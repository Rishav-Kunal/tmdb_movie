package com.rishav.tmdbapp.repository

import com.rishav.tmdbapp.data.Movie
import com.rishav.tmdbapp.data.MovieDetail
import com.rishav.tmdbapp.data.TrendingMovieResponse

interface MovieRepository {
    suspend fun getAllTrendingMovies() : TrendingMovieResponse?
    suspend fun saveAllTrendingMovies(movieList: List<Movie>)
    suspend fun getMovieDetails(id : Int) : MovieDetail?
    suspend fun getSortedMovies(sortParam : String) : List<Movie>?
}