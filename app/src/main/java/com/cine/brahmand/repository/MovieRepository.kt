package com.cine.brahmand.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cine.brahmand.api.MovieService
import com.cine.brahmand.models.simple.Movie
import com.cine.brahmand.models.simple.Position
import com.cine.brahmand.utils.NetworkResult
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService, private val picasso: Picasso) {

    private val _nowPlayingLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    private val _popularMoviesLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    private val _topRatedMoviesLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()

    val nowPlayingLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _nowPlayingLiveData
    val popularMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _popularMoviesLiveData
    val topRatedMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _topRatedMoviesLiveData

    suspend fun fetchNowPlaying(page: Int) {
        withContext(Dispatchers.Main) {
            _nowPlayingLiveData.value = NetworkResult.Loading()
        }
        val response = movieService.getNowPlaying(page)
        if (response.isSuccessful && response.body() != null && !response.body()?.results.isNullOrEmpty()) {
            val movieList: ArrayList<Movie> = arrayListOf()
            response.body()?.results?.subList(0, 5)?.forEach { apiMovie ->
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

    suspend fun getPopularMovies(page: Int) {
        withContext(Dispatchers.Main) {
            _popularMoviesLiveData.value = NetworkResult.Loading()
        }
        val response = movieService.getPopular(page)
        if (response.isSuccessful && response.body() != null && !response.body()?.results.isNullOrEmpty()) {
            val movieList: ArrayList<Movie> = arrayListOf()
            response.body()?.results?.forEachIndexed { index, apiMovie ->
                apiMovie?.let {
                    movieList.add(
                        Movie(
                            title = apiMovie.title,
                            overview = apiMovie.overview,
                            posterUrl = buildImageUrl(apiMovie.posterPath ?: ""),
                            backdropUrl = buildImageUrl(apiMovie.backdropPath ?: ""),
                            id = apiMovie.id,
                            position = if (index == 0) Position.START else if (index == movieList.size-1) Position.END else Position.MIDDLE
                        )
                    )
                }
            }
            withContext(Dispatchers.Main) {
                _popularMoviesLiveData.value = NetworkResult.Success(movieList)
            }
        } else if (response.errorBody() != null) {
            withContext(Dispatchers.Main) {
                _popularMoviesLiveData.value = NetworkResult.Error(response.message())
            }
        } else {
            withContext(Dispatchers.Main) {
                _popularMoviesLiveData.value = NetworkResult.Error("Something Went Wrong!!")
            }
        }
    }

    suspend fun getTopRatedMovies(page: Int) {
        withContext(Dispatchers.Main) {
            _topRatedMoviesLiveData.value = NetworkResult.Loading()
        }
        val response = movieService.getTopRated(page)
        if (response.isSuccessful && response.body() != null && !response.body()?.results.isNullOrEmpty()) {
            val movieList: ArrayList<Movie> = arrayListOf()
            response.body()?.results?.forEachIndexed { index, apiMovie ->
                apiMovie?.let {
                    movieList.add(
                        Movie(
                            title = apiMovie.title,
                            overview = apiMovie.overview,
                            posterUrl = buildImageUrl(apiMovie.posterPath ?: ""),
                            backdropUrl = buildImageUrl(apiMovie.backdropPath ?: ""),
                            id = apiMovie.id,
                            position = if (index == 0) Position.START else if (index == movieList.size-1) Position.END else Position.MIDDLE
                        )
                    )
                }
            }
            withContext(Dispatchers.Main) {
                _topRatedMoviesLiveData.value = NetworkResult.Success(movieList)
            }
        } else if (response.errorBody() != null) {
            withContext(Dispatchers.Main) {
                _topRatedMoviesLiveData.value = NetworkResult.Error(response.message())
            }
        } else {
            withContext(Dispatchers.Main) {
                _topRatedMoviesLiveData.value = NetworkResult.Error("Something Went Wrong!!")
            }
        }
    }

    private fun buildImageUrl(path: String) = "https://image.tmdb.org/t/p/w500$path"
}