package com.cine.brahmand.models.simple

data class MovieDetails(
    var id: Long,
    var title: String? = null,
    var posterUrl: String? = null,
    var timeYearRatingString: String? = null,
    var overview: String? = null,
    var genre: String? = null,
    var homePageUrl: String? = null
)