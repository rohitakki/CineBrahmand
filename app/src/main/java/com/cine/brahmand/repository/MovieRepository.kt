package com.cine.brahmand.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cine.brahmand.api.MovieService
import com.cine.brahmand.models.simple.Movie
import com.cine.brahmand.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService) {

    private val _nowPlayingLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    val nowPlayingLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _nowPlayingLiveData

    suspend fun fetchNowPlaying() {
        withContext(Dispatchers.Main) {
            _nowPlayingLiveData.value = NetworkResult.Loading()
        }
        val response = movieService.getNowPlaying(1)
        if (response.isSuccessful && response.body() != null && !response.body()?.results.isNullOrEmpty()) {
            val movieList: ArrayList<Movie> = arrayListOf()
            response.body()?.results?.subList(0, 5)?.forEach {apiMovie ->
                apiMovie?.let {
                    movieList.add(
                        Movie(
                            title = apiMovie.title,
                            overview = apiMovie.overview,
                            posterUrl = buildImageUrl(apiMovie.posterPath ?: ""),
                            backdropUrl = buildImageUrl(apiMovie.backdropPath ?: ""),
                            id = apiMovie.id
                        )
                    )
                }
            }
            withContext(Dispatchers.Main) {
                _nowPlayingLiveData.value = NetworkResult.Success(movieList)
            }
        } else if (response.errorBody() != null) {
            withContext(Dispatchers.Main) {
                _nowPlayingLiveData.value = NetworkResult.Error(response.message())
            }
        } else {
            withContext(Dispatchers.Main) {
                _nowPlayingLiveData.value = NetworkResult.Error("Something Went Wrong!!")
            }
        }
    }

    private fun buildImageUrl(path: String) = "https://image.tmdb.org/t/p/w500$path"
}