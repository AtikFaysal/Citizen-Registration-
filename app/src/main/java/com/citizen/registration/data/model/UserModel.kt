package com.citizen.registration.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/13/2021 at 2:38 PM
 */
class UserModel
{
    @SerializedName("id") lateinit var id : String
    @SerializedName("user_name") lateinit var name : String
    @SerializedName("phone_number") lateinit var mobile : String
    @SerializedName("email") var email : String ?= null
    @SerializedName("profile_picture") var image : String ?= null
    @SerializedName("address") var address : String ?= null
}