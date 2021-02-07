package com.rishav.tmdbapp.repository

import com.rishav.tmdbapp.data.Movie
import com.rishav.tmdbapp.data.MovieDetail
import com.rishav.tmdbapp.data.TrendingMovieResponse
import com.rishav.tmdbapp.db.MovieDao
import com.rishav.tmdbapp.network.NetworkApi
import com.rishav.tmdbapp.util.ApplicationConstants
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieDao: MovieDao, private val api : NetworkApi) : MovieRepository {
    override suspend fun getAllTrendingMovies(): TrendingMovieResponse? {
        val response = api.getTrendingMovies()
        if (response.isSuccessful){
            return response.body()!!
        } else{
            return null
        }

    }

    override suspend fun saveAllTrendingMovies(movieList: List<Movie>) {
        movieDao.insertAll(movieList)
    }

    override suspend fun getMovieDetails(id: Int): MovieDetail? {
        val response = api.getMovieDetail(id)
        return if (response.isSuccessful){
            return response.body()
        } else{
            null
        }
    }

    override suspend fun getSortedMovies(sortParam : String): List<Movie>? {
        return when(sortParam){
            ApplicationConstants.SORT_BY_POPULARITY -> movieDao.getMoviesByPopularity()
            ApplicationConstants.SORT_BY_RATING -> movieDao.getMoviesByRating()
            ApplicationConstants.SORT_BY_NAME -> movieDao.getMovies()
            else -> null
        }
    }
}