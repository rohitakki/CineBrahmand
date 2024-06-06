package com.cine.brahmand.utils

sealed class NetworkResult<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data: T): NetworkResult<T>(data)
    class Loading<T>(): NetworkResult<T>()
    class Error<T>(errorMessage: String?, data: T? = null): NetworkResult<T>(data, errorMessage)
}