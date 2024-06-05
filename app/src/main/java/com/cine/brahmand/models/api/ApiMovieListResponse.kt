package com.cine.brahmand.models.api

import com.google.gson.annotations.SerializedName

data class ApiMovieListResponse(
    val dates: Dates?,
    val page: Int?,
    val results: List<ApiMovie?>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)

data class Dates(
    val maximum: String?,
    val minimum: String?
)