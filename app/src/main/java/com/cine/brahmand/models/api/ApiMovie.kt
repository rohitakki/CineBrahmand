package com.cine.brahmand.models.api

import com.google.gson.annotations.SerializedName

data class ApiMovie(
    val adult: Boolean?,
    val id: Int?,
    val overview: String?,
    val popularity: Double?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int?>?
)