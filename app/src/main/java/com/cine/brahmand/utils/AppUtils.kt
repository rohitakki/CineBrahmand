package com.cine.brahmand.utils

import com.cine.brahmand.models.api.Genre
import com.cine.brahmand.models.api.MovieDetailsApiResponse

fun buildImageUrl(path: String) = "https://image.tmdb.org/t/p/w500$path"

fun buildTimeYearAndRatingString(movieDetailsApiResponse: MovieDetailsApiResponse): String {
    val stringBuilder = StringBuilder()
    if (movieDetailsApiResponse.runtime != null) {
        val hours: Int = movieDetailsApiResponse.runtime / 60
        val minutes: Int = movieDetailsApiResponse.runtime % 60
        stringBuilder.append("${hours}H ${minutes}MIN    ")
    }
    if (movieDetailsApiResponse.voteAverage != null) {
        stringBuilder.append("★ ${String.format("%.1f", movieDetailsApiResponse.voteAverage)}    ")
    }
    if (!movieDetailsApiResponse.releaseDate.isNullOrBlank()) {
        stringBuilder.append(movieDetailsApiResponse.releaseDate.substring(0, 4))
    }
    return stringBuilder.toString()
}

fun generateGenreStringFromList(genres: List<Genre?>?): String {
    if (!genres.isNullOrEmpty()) {
        genres.filterNotNull()
        val stringBuilder = StringBuilder()
        genres.forEachIndexed { index, genre ->
            if (index == genres.size-1) {
                stringBuilder.append(genre?.name)
            } else {
                stringBuilder.append("${genre?.name}     ")
            }
        }
        return stringBuilder.toString()
    }
    return ""
}