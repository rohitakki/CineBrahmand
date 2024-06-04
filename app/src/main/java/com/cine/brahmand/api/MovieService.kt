package com.cine.brahmand.api

import com.cine.brahmand.models.ApiMovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page: Int): Response<ApiMovieListResponse>

    @GET("movie/popular")
    suspend fun getPopular(@Query("page") page: Int): Response<ApiMovieListResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("page") page: Int): Response<ApiMovieListResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("page") page: Int): Response<ApiMovieListResponse>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY_PARAM = "api_key"
    }
}