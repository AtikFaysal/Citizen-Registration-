package com.citizen.registration.interfaces

import com.citizen.registration.data.model.NoNetworkException
import com.citizen.registration.utils.ErrorMessage
import com.citizen.registration.utils.Resource
import com.citizen.registration.utils.constants.Constants.Companion.INTERNAL_SERVER_ERROR
import com.citizen.registration.utils.constants.Constants.Companion.NETWORK_ERROR_CODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCall
{
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            }
            catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> Resource.Failure(false, throwable.code(), throwable.response()?.message().toString(),throwable.response()?.errorBody())
                    is NoNetworkException -> Resource.Failure(true, NETWORK_ERROR_CODE, null, null)
                    else -> Resource.Failure(false, INTERNAL_SERVER_ERROR,
                        ErrorMessage.UNKNOWN_ERROR, null)
                }
            }
        }
    }
}