package com.rishav.tmdbapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishav.tmdbapp.data.MovieDetail
import com.rishav.tmdbapp.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailActivityViewModel @ViewModelInject constructor(private val repository: MovieRepository): ViewModel() {
    val movieLiveData : LiveData<MovieDetail> get() = _movieLiveData
    private val _movieLiveData : MutableLiveData<MovieDetail> = MutableLiveData()
    fun getMovieDetail(id: Int) = viewModelScope.launch {
        val response = repository.getMovieDetails(id)
        response?.run {
            _movieLiveData.postValue(this)
        }
    }
}