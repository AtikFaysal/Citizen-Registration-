package com.citizen.registration.network.api

import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.data.response.LoginResponse


/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/1/2021 at 11:01 AM
 */
interface ApiHelper
{
    suspend fun login(userName : String, password : String): LoginResponse

    suspend fun getDashboardReport(): DefaultResponse

    suspend fun getHoldingType(): DefaultResponse

    suspend fun checkDuplicateIdentity(identityNo : String): DefaultResponse
}