package com.citizen.registration.network.api

import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.data.response.LoginResponse
import retrofit2.http.*

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 12:49 PM
 */
interface ApiServices
{
    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") userName : String ,
        @Field("password") password : String
    ): LoginResponse

    @GET("api/get-dashboard-data")
    suspend fun getDashboardReport(): DefaultResponse

    @GET("api/get-holding-name-type")
    suspend fun getHoldingType(): DefaultResponse

    @GET("api/check-nid-number")
    suspend fun checkDuplicateIdentity(
        @Query("nid") nid : String
    ): DefaultResponse
}