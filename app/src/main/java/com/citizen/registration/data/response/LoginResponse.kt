package com.citizen.registration.data.response

import com.citizen.registration.data.model.UserModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 12:58 PM
 */
class LoginResponse : Serializable
{
    @SerializedName("response_code") var responseCode = 0
    @SerializedName("message") lateinit var message : String
    @SerializedName("token") lateinit var accessToken : String
    @SerializedName("user") var user : UserModel ?= UserModel()
}