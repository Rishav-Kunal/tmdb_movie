package com.rishav.tmdbapp.viewmodel


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishav.tmdbapp.data.Movie
import com.rishav.tmdbapp.repository.MovieRepository
import kotlinx.coroutines.launch

const val VISIBLE = 0x00000000
const val INVISIBLE = 0x00000004
const val GONE = 0x00000008
class TrendingActivityViewModel @ViewModelInject constructor(private val repository: MovieRepository) : ViewModel() {
    val movieListLiveData : LiveData<List<Movie>> get() = _movieListLiveData
    private val _movieListLiveData : MutableLiveData<List<Movie>> = MutableLiveData()

    val sortSpinnerPosition : MutableLiveData<Int>  = MutableLiveData()
    private var isDataDownloaded = false
    var isProgressBarVisible : MutableLiveData<Boolean> = MutableLiveData(true)
    var isListVisible : MutableLiveData<Boolean> = MutableLiveData(false)
    init {
        getAllTrendingMovies()
    }
    private fun getAllTrendingMovies() = viewModelScope.launch {
        isProgressBarVisible.postValue(true)
        isListVisible.postValue(false)
        repository.getAllTrendingMovies()?.movies?.let {
            _movieListLiveData.postValue(it)
            repository.saveAllTrendingMovies(it)
            isDataDownloaded = true
            isProgressBarVisible.postValue(false)
            isListVisible.postValue(true)
        }
    }

    fun sortMovies(param: String) = viewModelScope.launch {
        if (isDataDownloaded) {
            isProgressBarVisible.postValue(true)
            isListVisible.postValue(false)
            var movies = repository.getSortedMovies(param)
            if (!movies.isNullOrEmpty()){
                _movieListLiveData.postValue(movies)
                isProgressBarVisible.postValue(false)
                isListVisible.postValue(true)
            }
        }
    }
}