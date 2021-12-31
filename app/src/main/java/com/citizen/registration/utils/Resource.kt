package com.citizen.registration.utils

import okhttp3.ResponseBody

sealed class Resource<out T>
{
    data class Success<out T>(val value : T) : Resource<T>()

    data class Failure(
        val isNetworkError : Boolean,
        val statusCode : Int,
        val errorMessage : String?,
        val errorBody : ResponseBody?
    ) : Resource<Nothing>()
}