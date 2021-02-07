package com.rishav.tmdbapp.data


import com.google.gson.annotations.SerializedName

data class TrendingMovieResponse(
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var movies: List<Movie>? = null,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
)