package com.example.namegenerator.state

sealed class ResponseState<out T> {
    object Empty : ResponseState<Nothing>()
    data class Error(val error: String) : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
    data class Success<T>(val data: T) : ResponseState<T>()
}
