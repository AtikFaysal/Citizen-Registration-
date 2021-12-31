package com.citizen.registration.data.model

import com.citizen.registration.utils.constants.Constants.Companion.NETWORK_ERROR_CODE
import java.io.IOException

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/1/2021 at 2:11 PM
 */
class NoNetworkException internal constructor() : IOException()
{
    val errorCode: Int get() = NETWORK_ERROR_CODE

    val errorMessage: String get() = "Please check internet connection"
}