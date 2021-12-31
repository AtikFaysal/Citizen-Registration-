package com.citizen.registration.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/8/2021 at 3:58 PM
 */
class DataModel
{
    @SerializedName("mobile") var mobile = ArrayList<String>()
    @SerializedName("error") lateinit var error : String
    @SerializedName("password") var password = ArrayList<String>()
    @SerializedName("hash") lateinit var hashKey : String


    @SerializedName("access_token") lateinit var accessToken : String
    @SerializedName("user") lateinit var userInfo : UserModel
}