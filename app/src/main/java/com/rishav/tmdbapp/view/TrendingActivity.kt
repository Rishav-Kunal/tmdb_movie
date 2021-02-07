package com.rishav.tmdbapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.rishav.tmdbapp.R
import com.rishav.tmdbapp.adapter.MovieListAdapter
import com.rishav.tmdbapp.data.Movie
import com.rishav.tmdbapp.databinding.ActivityTrendingBinding
import com.rishav.tmdbapp.util.ApplicationConstants
import com.rishav.tmdbapp.util.ItemClickListener
import com.rishav.tmdbapp.viewmodel.TrendingActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrendingActivity : AppCompatActivity() {
    @Inject
    lateinit var adapter : MovieListAdapter
    private lateinit var binding : ActivityTrendingBinding
    private val viewModel : TrendingActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_trending)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        init()
        observeAll()

    }
    private fun init(){
        binding.listMovie.adapter = adapter
        adapter.clickListener = object : ItemClickListener<Movie>{
            override fun onItemClicked(item: Movie) {
                moveToDetailScreen(item.id!!)
            }
        }
    }
    private fun observeAll(){
        viewModel.movieListLiveData.observe(this, Observer {
                movieList -> adapter.updateData(movieList)
                binding.listMovie.scrollToPosition(0)
        })
        viewModel.sortSpinnerPosition.observe(this, Observer { position ->
            viewModel.sortMovies(resources.getStringArray(R.array.sort_menu)[position])
        })
    }
    private fun moveToDetailScreen(movieId : Int){
        val intent = Intent(this, MovieDetailActivity::class.java )
        intent.putExtra(ApplicationConstants.MOVIE_ID, movieId)
        startActivity(intent)
    }
}