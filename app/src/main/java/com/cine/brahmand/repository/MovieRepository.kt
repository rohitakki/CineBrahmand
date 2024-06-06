package com.cine.brahmand.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cine.brahmand.api.MovieService
import com.cine.brahmand.models.simple.Movie
import com.cine.brahmand.models.simple.MovieDetails
import com.cine.brahmand.models.simple.Position
import com.cine.brahmand.utils.NetworkResult
import com.cine.brahmand.utils.buildImageUrl
import com.cine.brahmand.utils.buildTimeYearAndRatingString
import com.cine.brahmand.utils.generateGenreStringFromList
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService, private val picasso: Picasso) {

    private val _nowPlayingLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    private val _popularMoviesLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    private val _topRatedMoviesLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    private val _upcomingMoviesLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    private val _similarMoviesLiveData: MutableLiveData<NetworkResult<List<Movie>>> = MutableLiveData()
    private val _movieDetailsLiveData: MutableLiveData<NetworkResult<MovieDetails>> = MutableLiveData()

    val nowPlayingLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _nowPlayingLiveData
    val popularMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _popularMoviesLiveData
    val topRatedMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _topRatedMoviesLiveData
    val upcomingMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _upcomingMoviesLiveData
    val similarMoviesLiveData: LiveData<NetworkResult<List<Movie>>>
        get() = _similarMoviesLiveData
    val movieDetailsLiveData: LiveData<NetworkResult<MovieDetails>>
        get() = _movieDetailsLiveData

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

    suspend fun getUpcomingMovies(page: Int) {
        withContext(Dispatchers.Main) {
            _upcomingMoviesLiveData.value = NetworkResult.Loading()
        }
        val response = movieService.getUpcoming(page)
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
                _upcomingMoviesLiveData.value = NetworkResult.Success(movieList)
            }
        } else if (response.errorBody() != null) {
            withContext(Dispatchers.Main) {
                _upcomingMoviesLiveData.value = NetworkResult.Error(response.message())
            }
        } else {
            withContext(Dispatchers.Main) {
                _upcomingMoviesLiveData.value = NetworkResult.Error("Something Went Wrong!!")
            }
        }
    }

    suspend fun getSimilarMovies(id: Long) {
        withContext(Dispatchers.Main) {
            _similarMoviesLiveData.value = NetworkResult.Loading()
        }
        val response = movieService.getSimilarMovies(id)
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
                _similarMoviesLiveData.value = NetworkResult.Success(movieList)
            }
        } else if (response.errorBody() != null) {
            withContext(Dispatchers.Main) {
                _similarMoviesLiveData.value = NetworkResult.Error(response.message())
            }
        } else {
            withContext(Dispatchers.Main) {
                _similarMoviesLiveData.value = NetworkResult.Error("Something Went Wrong!!")
            }
        }
    }

    suspend fun getMovieDetails(id: Long) {
        withContext(Dispatchers.Main) {
            _movieDetailsLiveData.value = NetworkResult.Loading()
        }
        val response = movieService.getMovieDetails(id)
        if (response.isSuccessful && response.body() != null && response.body() != null) {
            val details = response.body()
            withContext(Dispatchers.Main) {
                _movieDetailsLiveData.value = NetworkResult.Success(
                    MovieDetails(
                        id = details?.id!!,
                        title = details.title,
                        posterUrl = buildImageUrl(details.posterPath ?: ""),
                        timeYearRatingString = buildTimeYearAndRatingString(details),
                        overview = details.overview,
                        genre = generateGenreStringFromList(details.genres),
                        homePageUrl = details.homepage
                    )
                )
            }
        } else {
            withContext(Dispatchers.Main) {
                _movieDetailsLiveData.value = NetworkResult.Error("Something Went Wrong!!")
            }
        }
    }
}