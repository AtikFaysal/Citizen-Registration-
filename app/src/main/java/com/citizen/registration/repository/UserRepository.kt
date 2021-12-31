package com.citizen.registration.repository


import android.util.Log
import com.citizen.registration.interfaces.SafeApiCall
import com.citizen.registration.network.api.ApiHelper
import javax.inject.Inject

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 12:59 PM
 */
class UserRepository @Inject constructor(private val apiHelper : ApiHelper) : SafeApiCall
{
    suspend fun login(userName : String, password : String) = safeApiCall {
        apiHelper.login(userName, password)
    }

    suspend fun getDashboardReport() = safeApiCall {
        apiHelper.getDashboardReport()
    }

    suspend fun getHoldingType() = safeApiCall {
        apiHelper.getHoldingType()
    }

    suspend fun checkDuplicateIdentity(identityNo : String) = safeApiCall {
        Log.d("nid_no",identityNo)
        apiHelper.checkDuplicateIdentity(identityNo)
    }
}