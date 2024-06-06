package com.cine.brahmand.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cine.brahmand.models.simple.Movie
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

    fun fetchNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchNowPlaying()
        }
    }
}