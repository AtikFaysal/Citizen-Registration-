package com.citizen.registration.database

import com.citizen.registration.data.model.UserModel


/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/1/2021 at 11:14 AM
 */
interface PreferenceHelper
{
    fun setRememberMe(isChecked : Boolean, userName : String, password : String)

    fun setLoginStatus(isLogin : Boolean)

    fun isRemembered() : Boolean

    fun getRememberedUserName() : String

    fun getRememberedPassword() : String

    fun getLoginStatus() : Boolean

    fun setAccessToken(token : String)

    fun getAccessToken() : String

    fun setUserInfo(user : UserModel)

    fun getUserId() : String

    fun getUserFullName() : String

    fun getPhone() : String

    fun getEmail() : String?

    fun getAvatar() : String?

    fun getAddress() : String?

    fun clearPrefManager()
}