package com.rishav.tmdbapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rishav.tmdbapp.R
import com.rishav.tmdbapp.adapter.ProductionListAdapter
import com.rishav.tmdbapp.databinding.ActivityMovieDetailBinding
import com.rishav.tmdbapp.util.ApplicationConstants
import com.rishav.tmdbapp.viewmodel.MovieDetailActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private val viewModel: MovieDetailActivityViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding
    private var movieId = 0
    @Inject
    lateinit var adapter : ProductionListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_detail )
        movieId = intent.getIntExtra(ApplicationConstants.MOVIE_ID, 0)
        init()

    }
    private fun init(){
        binding.rvProduction.adapter = adapter
        binding.ratingBar.setIsIndicator(true)
        viewModel.movieLiveData.observe(this, Observer {
            val genreString = it.genres?.joinToString()
            it.rating = it.voteAverage?.div(2)
            it.genreString = genreString
            it.releaseDate?.let {
                releaseDate -> it.movieNameAndRelease = "${it.title} (${releaseDate.substring(0,4)})"
            }
            binding.movie = it
            it.productionCompanies?.let { it1 -> adapter.updateData(it1) }
        })
        viewModel.getMovieDetail(movieId)

    }
}