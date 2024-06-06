package com.cine.brahmand.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cine.brahmand.models.simple.Movie
import com.cine.brahmand.models.simple.MovieDetails
import com.cine.brahmand.repository.MovieRepository
import com.cine.brahmand.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    val nowPlayingLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = movieRepository.nowPlayingLiveData
    val popularMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = movieRepository.popularMoviesLiveData
    val topRatedMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = movieRepository.topRatedMoviesLiveData
    val upcomingMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = movieRepository.upcomingMoviesLiveData
    val similarMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = movieRepository.similarMoviesLiveData
    val movieDetailsLiveData: LiveData<NetworkResult<MovieDetails>>
        get() = movieRepository.movieDetailsLiveData

    fun fetchNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchNowPlaying(1)
        }
    }

    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovies(page)
        }
    }

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getTopRatedMovies(page)
        }
    }

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getUpcomingMovies(page)
        }
    }

    fun fetchSimilarMovies(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getSimilarMovies(id)
        }
    }

    fun fetchMovieDetails(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieDetails(id)
        }
    }
}