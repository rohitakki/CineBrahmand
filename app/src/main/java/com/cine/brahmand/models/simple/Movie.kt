package com.cine.brahmand.models.simple

data class Movie(
    var id: Long,
    var title: String? = null,
    var overview: String? = null,
    var genre: String? = null,
    var ratingString: String? = null,
    var rating: Float? = null,
    var posterUrl: String? = null,
    var backdropUrl: String? = null,
    var position: Position = Position.MIDDLE
)

enum class Position {
    START,
    END,
    MIDDLE
}
