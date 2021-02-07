package com.rishav.tmdbapp.network

import com.rishav.tmdbapp.data.MovieDetail
import com.rishav.tmdbapp.data.TrendingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {
    @GET("/3/trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") mediaType: String = "movie",
        @Path("time_window") timeWindow : String = "day"
    ) : Response<TrendingMovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ) : Response<MovieDetail>
}