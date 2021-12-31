package com.citizen.registration.network.api

import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.data.response.LoginResponse
import javax.inject.Inject

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/1/2021 at 11:02 AM
 */
class ApiHelperImpl @Inject constructor(private val apiServices : ApiServices) :  ApiHelper
{
    override suspend fun login(userName: String, password: String): LoginResponse = apiServices.login(userName, password)

    override suspend fun getDashboardReport(): DefaultResponse = apiServices.getDashboardReport()

    override suspend fun getHoldingType(): DefaultResponse = apiServices.getHoldingType()

    override suspend fun checkDuplicateIdentity(identityNo: String): DefaultResponse = apiServices.checkDuplicateIdentity(identityNo)
}